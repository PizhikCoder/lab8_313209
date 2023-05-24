package server.connection;

import server.core.Invoker;
import server.core.printers.SocketPrinter;
import server.commands.Command;
import server.connection.interfaces.IServerConnection;
import server.database.interfaces.IDatabaseHandlerDAO;
import shared.connection.interfaces.IRequest;
import shared.connection.interfaces.IServerBackRequest;
import shared.connection.requests.*;
import server.core.managers.CommandsManager;
import server.core.managers.ModelsManager;
import shared.core.models.MusicBand;
import shared.interfaces.IDataLoader;
import shared.interfaces.IDataSaver;
import shared.interfaces.INoAuthCommand;

import java.util.concurrent.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The class contains logic for creating and managing a connection.
 */
public class ConnectionHandler implements IServerConnection {

    /**
     * Read buffer size in bytes.
     */
    private final int BUFFER_CAPACITY = 4096;

    private final int BASE_ID_VALUE = -1;

    private final int HEADER_BYTES_COUNT = 8;

    private static final Logger logger = Logger.getLogger(ConnectionHandler.class.getName());

    private Selector selector;

    private volatile ForkJoinPool requestExecutionPool;

    private ExecutorService sendersPool;

    private final InetSocketAddress address;

    /**
     * Stores information about which channel handles each of the threads.
     */
    private volatile Map<Long, SocketChannel> channels;

    private final Invoker invoker;


    /**
     * Creates a connection object, and also creates an Invoker object
     *
     * @param host
     * @param port
     * @param saver
     * @param loader
     * @param modelsManager
     */
    public ConnectionHandler(String host, int port, IDataSaver saver, IDataLoader loader, ModelsManager modelsManager, IDatabaseHandlerDAO databaseHandler) {
        address = new InetSocketAddress(host, port);
        channels = new ConcurrentHashMap<>();
        CommandsManager commandsManager = new CommandsManager();
        this.invoker = new Invoker(new SocketPrinter(this), saver, loader, modelsManager, commandsManager, databaseHandler);
        commandsManager.initializeCommands(invoker);
        invoker.setConnection(this);
        requestExecutionPool = new ForkJoinPool();
        sendersPool = Executors.newCachedThreadPool();
    }

    /**
     * Waiting for the creation of a connection or request from clients.
     */
    public void waitConnection() {
        try {
            logger.log(Level.INFO, "Waiting connection...");
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(address);
            logger.log(Level.INFO, "Server started on port: {0}", address.getPort());
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keys.iterator();
                checkSelectedKeys(keyIterator);
            }

        } catch (AlreadyBoundException exception) {
            logger.log(Level.WARNING, "Connection already bound. ", exception);
        } catch (UnsupportedAddressTypeException exception) {
            logger.log(Level.WARNING, "Unsupported address. ", exception);
        } catch (IOException exception) {
            logger.log(Level.WARNING, "Connection error: ", exception);
        }
    }

    /**
     * Checks Selected Keys for readability and connectivity.
     *
     * @param keyIterator selected keys for check
     */
    private void checkSelectedKeys(Iterator<SelectionKey> keyIterator) {
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            if (key.isValid()) {
                if (key.isAcceptable()) {
                    createConnection(key);
                    continue;
                }
                if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    /**
     * Reads data from the transmitted channel.
     *
     * @param key
     */
    public void read(SelectionKey key) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_CAPACITY);
            SocketChannel channel = (SocketChannel) key.channel();
            int readResult = channel.read(buffer);
            if (readResult == -1) {
                closeChannel(channel);
                return;
            }
            if (readResult == 0 || !checkStreamHeader(buffer.array())) {//If channel buffer hasn't got new data.
                return;
            }
            readThread(Arrays.copyOf(buffer.array(), buffer.array().length), key);
        } catch (IOException exception) {
            logger.log(Level.SEVERE, "Can not read from channel.");
            closeChannel((SocketChannel) key.channel());
        }
    }

    /**
     * Contains logic for parallel reading from a channel
     *
     * @param buffer
     * @param key    SelectionKey for reading
     */
    private void readThread(byte[] buffer, SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        Thread readThread = new Thread(() -> {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                IRequest request = (IRequest) objectInputStream.readObject();
                logger.log(Level.INFO, "New request received from: {0}", channel);
                executeRequestThread(key, request);
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Can not parse request.", ex);
            } catch (ClassNotFoundException exception) {
                logger.log(Level.WARNING, "Unknown class in request.");
            }
        });
        readThread.setDaemon(true);
        readThread.start();
    }

    private void executeRequestThread(SelectionKey key, IRequest request) {
        requestExecutionPool.submit(() -> {
            channels.putIfAbsent(Thread.currentThread().getId(), (SocketChannel) key.channel());
            executeRequest(request, invoker, (SocketChannel) key.channel());
            channels.remove(Thread.currentThread().getId());
        });
    }

    /**
     * Checks that the stream header indicates that deserialization is possible
     *
     * @param buffer stream buffer
     * @return
     */
    private boolean checkStreamHeader(byte[] buffer) {
        int zeroCounter = 0;
        for (int i = 0; i < HEADER_BYTES_COUNT; i++) {
            if (buffer[i] == 0) zeroCounter++;
        }
        if (zeroCounter == HEADER_BYTES_COUNT) {
            return false;
        }
        return true;
    }

    /**
     * Creates a new SocketChannel.
     *
     * @param key Key with ServerSocketChannel.
     */
    private void createConnection(SelectionKey key) {
        try {
            SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
            if (channel == null) return;
            logger.log(Level.INFO, "Connection received: {0}", channel);
            channel.configureBlocking(false);

            channel.register(selector, SelectionKey.OP_READ + SelectionKey.OP_WRITE);
        } catch (IOException exception) {
            logger.log(Level.SEVERE, "Exception while connection creating.", exception);
        }
    }

    /**
     * Determines the type of request and executes it.
     *
     * @param request
     * @param invoker
     */
    private void executeRequest(IRequest request, Invoker invoker, SocketChannel channel) {
        CommandsManager commandsManager = invoker.getCommandsManager();
        if (request instanceof CommandRequest) {
            handleCommandRequest(request, commandsManager);
            return;
        }
        if (request instanceof ValidationRequest) {
            handleValidationRequest(request, commandsManager);
            return;
        }
        if (request instanceof AuthorizationRequest) {
            handleAuthorizationRequest(request, channel);
            return;
        }
        if (request instanceof RegistrationRequest) {
            handleRegistrationRequest(request, channel);
        }

    }

    /**
     * Processes the registration request
     *
     * @param request
     */
    private void handleRegistrationRequest(IRequest request, SocketChannel channel) {
        RegistrationRequest registrationRequest = (RegistrationRequest) request;
        boolean isExist = invoker.getDatabaseHandler().clientExist(registrationRequest.getLogin());
        if (isExist) {
            invoker.getPrinter().print("Client already registered!");
            send(new AuthorizationRequest(null, null, BASE_ID_VALUE, false));
            return;
        }
        invoker.getDatabaseHandler().addClient(registrationRequest.getLogin(), registrationRequest.getPassword());
        int id = invoker.getDatabaseHandler().getClientID(registrationRequest.getLogin(), registrationRequest.getPassword());
        send(new AuthorizationRequest(null, null, id, true));
        send(new CollectionRequest(new ArrayList<>(invoker.getModelsManager().getModels())));
    }

    /**
     * Processes the authorization request
     *
     * @param request
     */
    private void handleAuthorizationRequest(IRequest request, SocketChannel channel) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) request;
        int id = invoker.getDatabaseHandler().getClientID(authorizationRequest.getLogin(), authorizationRequest.getPassword());
        boolean isAuthorized = id != -1;
        try {
            send(new AuthorizationRequest(null, null, id, isAuthorized)).get();
            if (isAuthorized) {
                send(new CollectionRequest(new ArrayList<>(invoker.getModelsManager().getModels())));
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.log(Level.WARNING, "Can not send authorization request.");
        }
    }

    /**
     * Processes the command request
     *
     * @param request
     * @param commandsManager executor
     */
    private void handleCommandRequest(IRequest request, CommandsManager commandsManager) {
        logger.log(Level.INFO, "New command request: {0}", request);
        CommandRequest commandRequest = (CommandRequest) request;
        if (commandsManager.getCommand(commandRequest.getCommand(), invoker.getPrinter()) instanceof INoAuthCommand) {
            Command command = commandsManager.getCommand(commandRequest.getCommand(), invoker.getPrinter());
            invoker.invokeCommand(command, commandRequest.getData(), -1);
            return;
        }
        if (invoker.getDatabaseHandler().getClientID(commandRequest.getLogin(), commandRequest.getPassword()) != -1) {
            Command command = commandsManager.getCommand(commandRequest.getCommand(), invoker.getPrinter());
            invoker.invokeCommand(command, commandRequest.getData(), invoker.getDatabaseHandler().getClientID(commandRequest.getLogin(), commandRequest.getPassword()));
        } else {
            logger.log(Level.INFO, "Unknown client!");
            invoker.getPrinter().print("Client is not authorized!");
            send(new CommandRequest(null, true));
        }
    }

    /**
     * Processes the validation request
     *
     * @param request
     * @param commandsManager executor
     */
    private void handleValidationRequest(IRequest request, CommandsManager commandsManager) {
        logger.log(Level.INFO, "New validation request: {0}", request);
        ValidationRequest validationRequest = (ValidationRequest) request;
        Command command = commandsManager.getCommand(validationRequest.getCommand(), invoker.getPrinter());
        if (command != null) {
            command.validate(validationRequest.getData());
        }
    }

    /**
     * Writes data to the stream of the specified SelectionKey.
     *
     * @param key
     * @param data
     * @throws IOException
     */
    private void write(SelectionKey key, IRequest data) throws IOException {
        byte[] buffer;
        SocketChannel channel = (SocketChannel) key.channel();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(data);
        buffer = new byte[byteArrayOutputStream.size()];
        channel.write(ByteBuffer.wrap(buffer).put(byteArrayOutputStream.toByteArray()).flip());
    }

    /**
     * Sends the transmitted object.
     * As a channel for sending a response, it takes out the channel from Max connections that is currently processing the thread.
     *
     * @param data
     * @return Future of senders pool or null if current thread haven't got SocketChannel.
     */
    @Override
    public synchronized Future send(IRequest data) {
        if (!channels.containsKey(Thread.currentThread().getId())) {
            logger.log(Level.INFO, "Can not send data!");
            return null;
        }
        SocketChannel channel = channels.get(Thread.currentThread().getId());
        return sendersPool.submit(() ->
        {
            boolean sent = false;
            try {
                while (!sent) {
                    selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = keys.iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.channel().equals(channel) && channelCheck((SocketChannel) key.channel())) {
                            logger.log(Level.INFO, String.format("Sending request: %s. Thread: %s", data, Thread.currentThread().getId()));
                            write(key, data);
                            sent = true;
                        }
                    }
                }
            } catch (IOException exception) {
                logger.log(Level.WARNING, "Data sending exception: ", exception);
            }
        });

    }

    /**
     * Checks the validity of the channel.
     *
     * @param channel
     * @return
     */
    private boolean channelCheck(SocketChannel channel) {
        try {
            write(channel.keyFor(selector), new PingRequest());
            return true;
        } catch (IOException exception) {
            closeChannel(channel);
            return false;
        }
    }

    /**
     * Closes unreachable channel.
     *
     * @param channel
     */
    private void closeChannel(SocketChannel channel) {
        logger.log(Level.WARNING, "Can not access to client. ");
        try {
            logger.log(Level.INFO, "Closing channel: {0}", channel);
            channel.close();
            logger.log(Level.INFO, "Channel closed", channel);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Can not close channel with client", channel);
        }
    }

    /**
     * Sends changes to all clients.
     */
    @Override
    public void sendChangesToAll(IServerBackRequest iServerBackRequest) {
        sendersPool.submit(() -> {
            try {
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isWritable() && channelCheck((SocketChannel) key.channel())) {
                        write(key, iServerBackRequest);
                    }
                }
            } catch (IOException exception) {
                logger.log(Level.WARNING, "EXCEPTION: ", exception);
            }
        });
    }


    public Invoker getInvoker() {
        return invoker;
    }
}

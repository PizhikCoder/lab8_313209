package server.database;

import org.postgresql.util.PSQLException;
import server.database.interfaces.IDatabase;

import java.sql.*;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.Driver;

/**
 * Contains logic for connection to PostgreSQL data base.
 */
public class PostgresDataBase implements IDatabase {

    private final Logger logger = Logger.getLogger(PostgresDataBase.class.getName());

    private final int DATABASE_WAITING_TIME = 3;

    private final int SEMAPHORE_PLACES_COUNT = 2;

    private final String hostName;

    private final String pass;

    private final String url;

    private Connection connection;

    private final Semaphore semaphore;

    public PostgresDataBase(String hostName, String pass, String url) {
        this.hostName = hostName;
        this.pass = pass;
        this.url = url;
        this.semaphore = new Semaphore(SEMAPHORE_PLACES_COUNT);
    }

    /**
     * Creates connection with database.
     *
     * @return
     */
    @Override
    public boolean connect() {
        java.sql.Driver driver = new Driver();

        try {
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(url, hostName, pass);
            if (connection == null) {
                throw new SQLException();
            }
            return true;
        } catch (SQLException exception) {
            logger.log(Level.WARNING, "Can not connect to database!", exception);
            return false;
        }
    }

    /**
     * Sends query to database.
     *
     * @param preparedStatement
     * @return Query result or null if has no result.
     */
    @Override
    public ResultSet sendQuery(PreparedStatement preparedStatement) {
        if (!isConnected()) {
            logger.log(Level.SEVERE, "Connection to the database is lost!");
            System.exit(1);
        }
        try {
            semaphore.acquire();
            if(preparedStatement.execute()){
                return preparedStatement.getResultSet();
            }
            return null;
        } catch (PSQLException exception) {
            logger.log(Level.INFO, "Query without results.");
            return null;
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Can not send query to database!", e);
            return null;
        } catch (InterruptedException e) {
            return null;
        }
        finally {
            semaphore.release();
        }
    }

    /**
     * Checks is data base connected.
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        try {
            return !connection.isClosed() && connection.isValid(DATABASE_WAITING_TIME);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database access error!", e);
            return false;
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}

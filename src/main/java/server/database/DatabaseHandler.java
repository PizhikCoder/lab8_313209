package server.database;

import org.apache.commons.codec.digest.DigestUtils;
import server.core.managers.ModelsManager;
import server.database.interfaces.IDatabase;
import server.database.interfaces.IDatabaseHandlerDAO;
import shared.commands.enums.DataField;
import shared.core.models.*;
import shared.interfaces.IDataLoader;
import shared.interfaces.IDataSaver;

import java.sql.*;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains logic for interacting with the database
 */
public class DatabaseHandler implements IDataLoader, IDataSaver, IDatabaseHandlerDAO {

    private final IDatabase dbConnection;

    private final ModelsManager modelsManager;

    private final Logger logger = Logger.getLogger(DatabaseHandler.class.getName());

    public DatabaseHandler(IDatabase dbConnection, ModelsManager modelsManager) {
        this.dbConnection = dbConnection;
        this.modelsManager = modelsManager;
        initRequests();
    }

    private void initRequests() {
        for (RequestsEnum requestsEnum : RequestsEnum.values()) {
            try {
                requestsEnum.setStatement(dbConnection.getConnection());
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Can not create statement", e);
            }
        }
    }

    /**
     * Loads models from database to models collection.
     *
     * @return
     */
    @Override
    public MusicBand[] load() {
        List<MusicBand> models = new ArrayList<>();
        try {
            ResultSet resultSet = dbConnection.sendQuery(RequestsEnum.LOAD_MODELS.createStatement());
            while (resultSet.next()) {
                models.add(createModel(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return models.stream().toArray(MusicBand[]::new);
    }

    /**
     * Creates model by ResulSet from database.
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private MusicBand createModel(ResultSet resultSet) throws SQLException {
        Map<DataField, Object> data = new HashMap<>();
        data.put(DataField.OWNER_ID, resultSet.getInt("owner_id"));
        data.put(DataField.NAME, resultSet.getString("musicband_name"));
        data.put(DataField.COORDINATES, createCoordinates(resultSet));
        data.put(DataField.NUMBER_OF_PARTICIPANTS, Integer.valueOf(resultSet.getInt("numberofparticipants")));
        data.put(DataField.GENRE, resultSet.getString("genre").equals("NULL") ? null : MusicGenre.valueOf(resultSet.getString("genre")));
        data.put(DataField.FRONTMAN, createPerson(resultSet));
        return modelsManager.createModel(data, resultSet.getLong("id"), resultSet.getTimestamp("creationdate").toLocalDateTime().atZone(ZoneId.systemDefault()));
    }

    /**
     * Creates coordinates be ResultSet from database.
     *
     * @param data
     * @return
     * @throws SQLException
     */
    private Coordinates createCoordinates(ResultSet data) throws SQLException {
        return new Coordinates(data.getInt("coordinates_x"), data.getDouble("coordinates_y"));
    }

    /**
     * Creates person by ResultSet from database.
     *
     * @param data
     * @return
     * @throws SQLException
     */
    private Person createPerson(ResultSet data) throws SQLException {
        Float height = data.getFloat("height");
        if (data.wasNull()) {
            height = null;
        }
        return new Person(data.getString("person_name"), height, Country.valueOf(data.getString("country_name")), createLocation(data));
    }

    /**
     * Creates location by ResultSet from database.
     *
     * @param data
     * @return
     * @throws SQLException
     */
    private Location createLocation(ResultSet data) throws SQLException {
        return new Location(data.getInt("location_x"), data.getFloat("location_y"), data.getFloat("location_x"));
    }

    /**
     * Saves models to database.
     *
     * @param data models to save.
     * @return
     */
    @Override
    public boolean save(MusicBand[] data) {
        Arrays.stream(data).forEach(this::sendModel);
        return true;
    }

    /**
     * Sends new model to database.
     *
     * @param model
     * @return
     */
    public int sendModel(MusicBand model) {
        try {
            logger.log(Level.INFO, "Sending new coordinates to database.");
            int coordinates_id = sendCoordinates(model.getCoordinates().getX(), model.getCoordinates().getY());
            logger.log(Level.INFO, "Sending new nationality to database.");
            int nationality_id = sendCountry(model.getFrontMan().getNationality());
            logger.log(Level.INFO, "Sending new location to database.");
            int location_id = sendLocation(model.getFrontMan().getLocation().getX(), model.getFrontMan().getLocation().getY(), model.getFrontMan().getLocation().getZ());
            logger.log(Level.INFO, "Sending new genre to database.");
            int genre_id = sendGenre(model.getGenre());
            logger.log(Level.INFO, "Sending new front man to database.");
            int frontman_id = sendPerson(model.getFrontMan().getName(), model.getFrontMan().getHeight(), nationality_id, location_id);

            ResultSet result = dbConnection.sendQuery(RequestsEnum.SEND_MODEL.createStatement(model.getOwnerId(), model.getName(), coordinates_id, Timestamp.valueOf(model.getCreationDate().toLocalDateTime()), model.getNumberOfParticipants(), genre_id, frontman_id));
            if (result.next()) {
                return result.getInt("id");
            }
            return -1;
        } catch (SQLException exception) {
            logger.log(Level.WARNING, String.format("Can not save model with id: %s", model.getId()), exception);
            return -1;
        }
    }

    /**
     * Removes model from database.
     *
     * @param model_id
     * @return
     */
    public boolean removeModel(long model_id) {
        try {
            ResultSet ids = dbConnection.sendQuery(RequestsEnum.GET_IDS.createStatement(model_id));
            if (ids.next()) {
                int coordinates_id = ids.getInt("coordinates_id");
                int genre_id = ids.getInt("genre_id");
                int location_id = ids.getInt("location_id");
                int nationality_id = ids.getInt("nationality_id");
                int frontman_id = ids.getInt("frontman_id");

                dbConnection.sendQuery(RequestsEnum.DELETE_FROM_MUSICBAND.createStatement(model_id));
                dbConnection.sendQuery(RequestsEnum.DELETE_FROM_PERSON.createStatement(frontman_id));
                dbConnection.sendQuery(RequestsEnum.DELETE_FROM_COORDINATES.createStatement(coordinates_id));
                dbConnection.sendQuery(RequestsEnum.DELETE_FROM_MUSICGENRE.createStatement(genre_id));
                dbConnection.sendQuery(RequestsEnum.DELETE_FROM_LOCATION.createStatement(location_id));
                dbConnection.sendQuery(RequestsEnum.DELETE_FROM_COUNTRY.createStatement(nationality_id));
                return true;
            }
            return false;
        } catch (SQLException exception) {
            logger.log(Level.WARNING, "Can not remove model!");
            return false;
        }
    }

    /**
     * Sends new model to database with custom id.
     *
     * @param model
     * @param modelId
     * @return
     */
    public int sendModel(MusicBand model, long modelId) {
        try {
            logger.log(Level.INFO, "Sending new coordinates to database.");
            int coordinates_id = sendCoordinates(model.getCoordinates().getX(), model.getCoordinates().getY());
            logger.log(Level.INFO, "Sending new nationality to database.");
            int nationality_id = sendCountry(model.getFrontMan().getNationality());
            logger.log(Level.INFO, "Sending new location to database.");
            int location_id = sendLocation(model.getFrontMan().getLocation().getX(), model.getFrontMan().getLocation().getY(), model.getFrontMan().getLocation().getZ());
            logger.log(Level.INFO, "Sending new genre to database.");
            int genre_id = sendGenre(model.getGenre());
            logger.log(Level.INFO, "Sending new front man to database.");
            int frontman_id = sendPerson(model.getFrontMan().getName(), model.getFrontMan().getHeight(), nationality_id, location_id);

            ResultSet result = dbConnection.sendQuery(RequestsEnum.SEND_MODEL.createStatement(modelId, model.getOwnerId(), model.getName(), coordinates_id, Timestamp.valueOf(model.getCreationDate().toLocalDateTime()), model.getNumberOfParticipants(), genre_id, frontman_id));
            if (result.next()) {
                return result.getInt("id");
            }
            return -1;
        } catch (SQLException exception) {
            logger.log(Level.WARNING, String.format("Can not save model with id: %s", model.getId()), exception);
            return -1;
        }
    }

    /**
     * Updates model in database.
     *
     * @param data
     * @param modelId
     * @return
     */
    public boolean updateModel(Map<DataField, Object> data, long modelId) {
        try {
            ResultSet ids = dbConnection.sendQuery(RequestsEnum.GET_IDS.createStatement(modelId));
            if (ids.next()) {
                int coordinates_id = ids.getInt("coordinates_id");
                int genre_id = ids.getInt("genre_id");
                int frontman_id = ids.getInt("frontman_id");
                int location_id = ids.getInt("location_id");
                int nationality_id = ids.getInt("nationality_id");

                updateLocation(data, location_id);
                updateNationality(data, nationality_id);
                updatePerson(data, frontman_id);
                updateGenre(data, genre_id);
                updateCoordinates(data, coordinates_id);

                dbConnection.sendQuery(RequestsEnum.UPDATE_MODEL.createStatement(data.get(DataField.NAME), data.get(DataField.NUMBER_OF_PARTICIPANTS)));
                logger.log(Level.INFO, String.format("Model ID: %s updated.", modelId));
                return true;
            }
            return false;
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Can not update model!", e);
            return false;
        }
    }

    /**
     * Updates coordinates in database.
     *
     * @param data
     * @param coordinates_id
     */
    private void updateCoordinates(Map<DataField, Object> data, int coordinates_id) {
        Coordinates coordinates = (Coordinates) data.get(DataField.COORDINATES);
        dbConnection.sendQuery(RequestsEnum.UPDATE_COORDINATES.createStatement(coordinates.getX(), coordinates.getY(), coordinates_id));
    }

    /**
     * Updates genre in database.
     *
     * @param data
     * @param genre_id
     */
    private void updateGenre(Map<DataField, Object> data, int genre_id) {
        String genre = data.get(DataField.GENRE) == null ? "NULL" : ((MusicGenre) data.get(DataField.GENRE)).name();
        dbConnection.sendQuery(RequestsEnum.UPDATE_GENRE.createStatement(genre, genre_id));
    }

    /**
     * Updates location in database.
     *
     * @param data
     * @param location_id
     */
    private void updateLocation(Map<DataField, Object> data, int location_id) {
        Location location = ((Person) data.get(DataField.FRONTMAN)).getLocation();
        dbConnection.sendQuery(RequestsEnum.UPDATE_LOCATION.createStatement(location.getX(), location.getY(), location.getZ(), location_id));
    }

    /**
     * Updates country in database.
     *
     * @param data
     * @param nationality_id
     */
    private void updateNationality(Map<DataField, Object> data, int nationality_id) {
        Country country = ((Person) data.get(DataField.FRONTMAN)).getNationality();
        dbConnection.sendQuery(RequestsEnum.UPDATE_COUNTRY.createStatement(country.name(), nationality_id));
    }

    /**
     * Updates person in database.
     *
     * @param data
     * @param frontman_id
     */
    private void updatePerson(Map<DataField, Object> data, int frontman_id) {
        Person person = (Person) data.get(DataField.FRONTMAN);
        dbConnection.sendQuery(RequestsEnum.UPDATE_PERSON.createStatement(person.getName(), person.getHeight(), frontman_id));
    }

    /**
     * Sends new person to database.
     *
     * @param name
     * @param height
     * @param nationality_id
     * @param location_id
     * @return
     * @throws SQLException
     */
    private int sendPerson(String name, Float height, int nationality_id, int location_id) throws SQLException {
        ResultSet resultSet = dbConnection.sendQuery(RequestsEnum.SEND_PERSON.createStatement(name, height, nationality_id, location_id));
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        resultSet.close();
        return -1;
    }

    /**
     * Sends new genre to database.
     *
     * @param genre
     * @return
     * @throws SQLException
     */
    private int sendGenre(MusicGenre genre) throws SQLException {
        ResultSet resultSet = dbConnection.sendQuery(RequestsEnum.SEND_GENRE.createStatement(genre == null ? "NULL" : genre.name()));
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        resultSet.close();
        return -1;
    }

    /**
     * Sends new location to database.
     *
     * @param x
     * @param y
     * @param z
     * @return
     * @throws SQLException
     */
    private int sendLocation(int x, float y, float z) throws SQLException {
        ResultSet resultSet = dbConnection.sendQuery(RequestsEnum.SEND_LOCATION.createStatement(x, y, z));
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        resultSet.close();
        return -1;
    }

    /**
     * Sends new coordinates to database.
     *
     * @param x
     * @param y
     * @return
     * @throws SQLException
     */
    private int sendCoordinates(int x, double y) throws SQLException {
        ResultSet resultSet = dbConnection.sendQuery(RequestsEnum.SEND_COORDINATES.createStatement(x, y));
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        resultSet.close();
        return -1;
    }

    /**
     * Sends new country to database.
     *
     * @param country
     * @return
     * @throws SQLException
     */
    private int sendCountry(Country country) throws SQLException {
        ResultSet resultSet = dbConnection.sendQuery(RequestsEnum.SEND_COUNTY.createStatement(country.name()));
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        resultSet.close();
        return -1;
    }

    /**
     * Gets clients id from database.
     *
     * @param login
     * @param password
     * @return
     */
    public int getClientID(String login, String password) {
        ResultSet resultSet = dbConnection.sendQuery(RequestsEnum.GET_CLIENT_ID.createStatement(login, DigestUtils.sha256Hex(password)));
        try (resultSet) {
            resultSet.next();
            return resultSet.getInt("id");
        } catch (SQLException e) {
            logger.log(Level.WARNING, "CAN NOT FIND CLIENT");
            return -1;
        }
    }

    public boolean clientExist(String login) {
        ResultSet resultSet = dbConnection.sendQuery(RequestsEnum.CLIENT_EXIST_CHECK.createStatement(login));
        try (resultSet) {
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            logger.log(Level.WARNING, "CAN NOT FIND CLIENT");
            return false;
        }
    }

    /**
     * Registers new client in database.
     *
     * @param login
     * @param password
     */
    public void addClient(String login, String password) {
        dbConnection.sendQuery(RequestsEnum.SEND_CLIENT.createStatement(login, DigestUtils.sha256Hex(password)));
    }

    /**
     * Clear all models in database.
     */
    public void clearModels() {
        logger.log(Level.INFO, "Clearing models.");
        dbConnection.sendQuery(RequestsEnum.CLEAR_ALL_MODELS.createStatement());
    }

}

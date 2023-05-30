package server.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum RequestsEnum {
    LOAD_MODELS("SELECT musicband.id, musicband.owner_id, musicband.name AS musicband_name, coordinates.x AS coordinates_x, coordinates.y AS coordinates_y, musicband.creationdate, musicband.numberofparticipants, musicgenre.genre, person.name AS person_name, person.height, country.name AS country_name, location.x AS location_x, location.y AS location_y, location.z AS location_z\n" +
            "FROM musicband JOIN coordinates ON musicband.coordinates_id = coordinates.id JOIN musicgenre ON musicband.genre_id = musicgenre.id JOIN person ON musicband.frontman_id = person.id JOIN country on person.nationality_id = country.id JOIN location on person.location_id = location.id;"),
    SEND_MODEL("INSERT INTO musicband (owner_id, name, coordinates_id, creationdate, numberofparticipants, genre_id, frontman_id) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id;"),
    SEND_MODEL_WITH_ID("INSERT INTO musicband (id, owner_id, name, coordinates_id, creationdate, numberofparticipants, genre_id, frontman_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;"),
    GET_IDS("SELECT  coordinates_id, genre_id, frontman_id, location_id, nationality_id FROM musicband JOIN person on frontman_id = person.id WHERE musicband.id = ?;"),
    UPDATE_COORDINATES("UPDATE coordinates\n" +
            "SET x= ?, y = ?\n" +
            "WHERE id = ?;"),
    UPDATE_MODEL("UPDATE musicband\n" +
            "SET name = ?, numberofparticipants = ?\n" +
            "WHERE id = ?;"),
    UPDATE_GENRE("UPDATE musicgenre\n" +
            "SET genre = ?\n" +
            "WHERE id = ?;"),
    UPDATE_LOCATION("UPDATE location \n" +
            "SET x = ?, y = ?, z = ?\n" +
            "WHERE id = ?;"),
    UPDATE_COUNTRY("UPDATE country\n" +
            "SET name = ?\n" +
            "WHERE id = ?;"),
    UPDATE_PERSON("UPDATE person\n" +
            "SET name = ?, height = ?\n" +
            "WHERE id = ?;"),
    CREATION_REQUEST("CREATE TABLE IF NOT EXISTS Coordinates (\n" +
            "  id serial PRIMARY KEY,\n" +
            "  x integer,\n" +
            "  y float8\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS Location (\n" +
            "  id serial PRIMARY KEY,\n" +
            "  x integer,\n" +
            "  y float4 NOT NULL,\n" +
            "  z float4 NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS MusicGenre (\n" +
            "  id serial PRIMARY KEY,\n" +
            "  genre text\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS Country (\n" +
            "  id serial PRIMARY KEY,\n" +
            "  name text NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS Person (\n" +
            "  id serial PRIMARY KEY,\n" +
            "  name text NOT NULL ,\n" +
            "  height float4,\n" +
            "  nationality_id integer NOT NULL REFERENCES Country(id),\n" +
            "  location_id integer NOT NULL REFERENCES Location(id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS Client (\n" +
            "  id serial PRIMARY KEY,\n" +
            "  login text NOT NULL,\n" +
            "  password text NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS MusicBand (\n" +
            "  id bigserial PRIMARY KEY,\n" +
            "  owner_id integer NOT NULL REFERENCES Client(id),\n" +
            "  name text NOT NULL,\n" +
            "  coordinates_id integer NOT NULL REFERENCES Coordinates(id),\n" +
            "  creationDate timestamp NOT NULL,\n" +
            "  numberOfParticipants integer NOT NULL,\n" +
            "  genre_id integer NOT NULL REFERENCES MusicGenre(id),\n" +
            "  frontMan_id integer NOT NULL REFERENCES Person(id)\n" +
            ");"),
    SEND_PERSON("INSERT INTO person (name, height, nationality_id, location_id) VALUES (?, ?, ?, ?) RETURNING id;"),
    SEND_GENRE("INSERT INTO musicgenre (genre) VALUES (?) RETURNING id;"),
    SEND_LOCATION("INSERT INTO location (x, y, z) VALUES (?, ?, ?) RETURNING id;"),
    SEND_COORDINATES("INSERT INTO coordinates (x, y)  VALUES (?,?) RETURNING id"),
    SEND_COUNTY("INSERT INTO country (name) VALUES (?) RETURNING id;"),
    GET_CLIENT_ID("SELECT client.id FROM client\n" +
            "WHERE login = ? AND password = ?;"),
    CLIENT_EXIST_CHECK("SELECT id FROM client WHERE login = ?;"),
    SEND_CLIENT("INSERT INTO client (login, password) VALUES (?, ?) RETURNING id;"),
    CLEAR_ALL_MODELS("TRUNCATE TABLE coordinates, country, location, musicband, musicgenre, person RESTART IDENTITY;"),
    DELETE_FROM_COORDINATES("DELETE FROM coordinates WHERE id = ?;"),
    DELETE_FROM_MUSICGENRE("DELETE FROM musicgenre WHERE id = ?;"),
    DELETE_FROM_LOCATION("DELETE FROM location WHERE id = ?;"),
    DELETE_FROM_COUNTRY("DELETE FROM country WHERE id = ?;"),
    DELETE_FROM_PERSON("DELETE FROM person WHERE id = ?;"),
    DELETE_FROM_MUSICBAND("DELETE FROM musicband WHERE id = ?;");
    private PreparedStatement statement;
    private String query;
    static Logger logger = Logger.getLogger(RequestsEnum.class.getName());
    RequestsEnum(String query){
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setStatement(Connection connection) throws SQLException {
        statement = connection.prepareStatement(getQuery());
    }
    public PreparedStatement createStatement(Object ... params)  {
        int placeCounter = 1;
        try{
            for(Object field : params){
                if (field == null){
                    statement.setNull(placeCounter, Types.NULL);
                }
                else if (field.getClass() == String.class){
                    statement.setString(placeCounter, (String)field);
                }
                else if (field.getClass() == Integer.class){
                    statement.setInt(placeCounter, (Integer)field);
                }
                else if (field.getClass() == Long.class){
                    statement.setLong(placeCounter, (Long) field);
                }
                else if(field.getClass() == Float.class){
                    statement.setFloat(placeCounter, (Float)field);
                }
                else if(field.getClass() == Double.class){
                    statement.setDouble(placeCounter, (Double) field);
                }
                else if(field.getClass() == Timestamp.class){
                    statement.setTimestamp(placeCounter, (Timestamp)field);
                }
                placeCounter++;
            }
        }
        catch (SQLException exception){
            logger.log(Level.WARNING, "Can not create statement", exception);
        }
        return statement;
    }
}

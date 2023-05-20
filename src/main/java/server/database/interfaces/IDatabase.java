package server.database.interfaces;

import java.sql.*;

public interface IDatabase {
    boolean connect();

    ResultSet sendQuery(PreparedStatement statement);

    boolean isConnected();

    Connection getConnection();

}

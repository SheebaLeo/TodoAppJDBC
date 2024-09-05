package se.lexicon.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TodoDbConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/todoit";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PWD = "1234";


    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

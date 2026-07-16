package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/MYSQLTESTE?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";

    private static final String PASS = "mysqlPW";

    public static Connection conectar() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
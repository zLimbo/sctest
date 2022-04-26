package com.zlimbo.sctest.connect;


import com.zlimbo.sctest.config.ConnConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcConn implements AutoCloseable {

    private final ConnConfig connConfig;
    private Connection connection = null;

    public JdbcConn(ConnConfig connConfig) throws SQLException {
        this.connConfig = connConfig;

        String url = "jdbc:mysql://" +
                connConfig.getHost() + ":" +
                connConfig.getPort() + "/" +
                connConfig.getDatabase() +
                "?useSSL=false&characterEncoding=utf8";
        connection = DriverManager.getConnection(url, connConfig.getUser(), connConfig.getPassword());
        System.out.println("database " + connConfig.getConnInfo() + " connect success!");
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    public void sqlUpdate(String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    public void sqlQuery(String sql) {

    }
}

package com.ktodo_back;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnect {
    private final Properties connectionProperties = new Properties();
    private final String server;
    private final String db = "kTodo";
    private Connection jdbc;

    public JDBCConnect(String user, String pass, String server) {
        this.server = server;
        this.connectionProperties.put("user", user);
        this.connectionProperties.put("password", pass);
    }

    public void setJdbc() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.jdbc = DriverManager.getConnection("jdbc:mysql://" + this.server + ":3306/" + this.db, this.connectionProperties);
        } catch (SQLException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public Connection getJdbc() {
        return this.jdbc;
    }
}

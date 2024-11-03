package com.ktodo_back;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCConnect {
    private final Properties connectionProperties = new Properties();
    private final String server;
    private static final String db = "kTodo";
    private Connection jdbc;

    public JDBCConnect() {
        Properties propertiesFile = new Properties();
        try {
            propertiesFile.load(getClass().getClassLoader().getResourceAsStream("db_conn.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.server = propertiesFile.getProperty("server");
        this.connectionProperties.put("user", propertiesFile.getProperty("user"));
        this.connectionProperties.put("password", propertiesFile.getProperty("password"));
        this.setJdbc();
    }

    private void setJdbc() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.jdbc = DriverManager.getConnection("jdbc:mysql://" + this.server + ":3306/" + db, this.connectionProperties);
        } catch (SQLException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public Connection getJdbc() {
        return this.jdbc;
    }

    public static JSONObject rowToJSONObj(ResultSet resultSet) throws SQLException {
        JSONObject obj = new JSONObject();
        ResultSetMetaData resultSetMeta = resultSet.getMetaData();
        if (resultSet.next()) {
            for (int i = 1; i <= resultSetMeta.getColumnCount(); i++) {
                obj.put(resultSetMeta.getColumnName(i), resultSet.getObject(i));
            }
        }
        return obj;
    }
    public static JSONArray resultSetToJSONArray(ResultSet resultSet) throws SQLException {
        JSONArray array = new JSONArray();
        ResultSetMetaData resultMeta = resultSet.getMetaData();
        while (!resultSet.isLast()) {
            JSONObject row = rowToJSONObj(resultSet);
            array.put(row);
        }
        return array;
    }
}

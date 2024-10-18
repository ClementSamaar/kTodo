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
    }

    public void setJdbc() {
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

    private static JSONObject convertRowToJSONObj(ResultSet resultSet, ResultSetMetaData resultMeta) throws SQLException {
        JSONObject obj = new JSONObject();
        if (resultSet.next()) {
            for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
                obj.put(resultMeta.getColumnName(i), resultSet.getObject(i));
            }
        }
        return obj;
    }
    public static String resultSetJSONStringify(ResultSet resultSet) throws SQLException {
        JSONArray jsonResult = new JSONArray();
        ResultSetMetaData resultMeta = resultSet.getMetaData();
        while (!resultSet.isLast()) {
            JSONObject row = convertRowToJSONObj(resultSet, resultMeta);
            jsonResult.put(row);
        }
        return jsonResult.toString(4);
    }

    public static String rowJSONStringify(ResultSet resultSet, ResultSetMetaData resultMeta) throws SQLException {
        return convertRowToJSONObj(resultSet, resultMeta).toString();
    }
}

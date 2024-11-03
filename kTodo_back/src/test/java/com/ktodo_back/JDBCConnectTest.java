package com.ktodo_back;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


class JDBCConnectTest {

    @Test
    void JDBCConnect() {
        JDBCConnect jdbc = new JDBCConnect();
        assertNotNull(jdbc.getJdbc());
    }

    @Test
    void rowToJSONObj() {
        JSONObject expectedObj = new JSONObject();
        expectedObj.put("id", 1);
        expectedObj.put("title", "just a test");
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        ResultSetMetaData resultSetMeta = Mockito.mock(ResultSetMetaData.class);
        try {
            Mockito.when(resultSet.getObject(1)).thenReturn(1);
            Mockito.when(resultSet.getObject(2)).thenReturn("just a test");
            Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
            Mockito.when(resultSetMeta.getColumnCount()).thenReturn(2);
            Mockito.when(resultSetMeta.getColumnName(1)).thenReturn("id");
            Mockito.when(resultSetMeta.getColumnName(2)).thenReturn("title");
            Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMeta);
            JSONObject actualObj = JDBCConnect.rowToJSONObj(resultSet);
            assertEquals(expectedObj.toString(), actualObj.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void resultSetToJSONArray() {
        JSONArray expectedArray = new JSONArray();
        JSONObject obj1 = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj1.put("id", 1);
        obj1.put("title", "just a test");
        obj2.put("id", 2);
        obj2.put("title", "just a second test");
        expectedArray.put(obj1);
        expectedArray.put(obj2);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        ResultSetMetaData resultSetMeta = Mockito.mock(ResultSetMetaData.class);
        try {
            Mockito.when(resultSet.isLast()).thenReturn(false).thenReturn(false).thenReturn(true);
            Mockito.when(resultSet.getObject(1)).thenReturn(1).thenReturn(2);
            Mockito.when(resultSet.getObject(2)).thenReturn("just a test").thenReturn("just a second test");
            Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
            Mockito.when(resultSetMeta.getColumnCount()).thenReturn(2);
            Mockito.when(resultSetMeta.getColumnName(1)).thenReturn("id");
            Mockito.when(resultSetMeta.getColumnName(2)).thenReturn("title");
            Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMeta);
            JSONArray actualArray = JDBCConnect.resultSetToJSONArray(resultSet);
            System.out.println(actualArray.toString(3));
            assertEquals(expectedArray.toString(), actualArray.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

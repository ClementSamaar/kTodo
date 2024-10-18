package com.ktodo_back.ressources;

import com.ktodo_back.JDBCConnect;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.sql.*;

@Path("/list")
public class ListResource {
    @GET
    @Produces("text/json")
    public String getAll() {
        JDBCConnect jdbc = new JDBCConnect();
        jdbc.setJdbc();
        String response;

        try (Statement statement = jdbc.getJdbc().createStatement()) {
            response = JDBCConnect.resultSetJSONStringify(
                    statement.executeQuery("SELECT * FROM list ORDER BY update_timestamp DESC")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        return response;
    }

    @GET
    @Path("/{id}")
    @Produces("text/json")
    public String getById(@PathParam("id") Integer id) {
        JDBCConnect jdbc = new JDBCConnect();
        jdbc.setJdbc();
        String response;

        try {
            PreparedStatement statement = jdbc.getJdbc().prepareStatement("SELECT * FROM list WHERE id = ?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            response = JDBCConnect.rowJSONStringify(resultSet, resultSet.getMetaData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        return response;
    }
}

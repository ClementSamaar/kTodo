package com.ktodo_back.resources;

import com.ktodo_back.JDBCConnect;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

@Path("/list")
public class ListResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        JDBCConnect jdbc = new JDBCConnect();
        JSONArray response = new JSONArray();
        try (Statement statement = jdbc.getJdbc().createStatement()) {
            response = JDBCConnect.resultSetToJSONArray(
                    statement.executeQuery("SELECT * FROM list ORDER BY update_timestamp DESC")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Response.status(Response.Status.OK).entity(response).build().toString());
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id) {
        JDBCConnect jdbc = new JDBCConnect();
        JSONObject response = new JSONObject();
        try (
                PreparedStatement statement = jdbc.getJdbc().prepareStatement(
                        "SELECT * FROM list WHERE id = ?"
                )
        ){
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            response = JDBCConnect.rowToJSONObj(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        return Response.status(Response.Status.OK).entity(response).build();
    }
}

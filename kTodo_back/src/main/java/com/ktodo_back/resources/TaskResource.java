package com.ktodo_back.resources;

import com.ktodo_back.JDBCConnect;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Path("/task")
public class TaskResource {
    // CREATE
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask() {
        JDBCConnect jdbc = new JDBCConnect();
        JSONObject response = new JSONObject();
        return Response.status(Response.Status.OK).entity(response).build();
    }
    // READ
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        JDBCConnect jdbc = new JDBCConnect();
        JSONArray response = new JSONArray();
        try (Statement statement = jdbc.getJdbc().createStatement()) {
            response = JDBCConnect.resultSetToJSONArray(statement.executeQuery("SELECT * FROM task"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
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
                "SELECT * FROM task WHERE id = ?"
                )
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            response = JDBCConnect.rowToJSONObj(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @GET
    @Path("/list/{list_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByListId(@PathParam("list_id") Integer listId) {
        JDBCConnect jdbc = new JDBCConnect();
        JSONArray response = new JSONArray();
        try (
                PreparedStatement statement = jdbc.getJdbc().prepareStatement(
                        "SELECT * FROM task WHERE list_id = ?"
                )
        ) {
            statement.setInt(1, listId);
            ResultSet resultSet = statement.executeQuery();
            response = JDBCConnect.resultSetToJSONArray(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    // UPDATE
    // DELETE
}

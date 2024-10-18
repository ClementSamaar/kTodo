package com.ktodo_back.ressources;

import com.ktodo_back.JDBCConnect;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.sql.SQLException;
import java.sql.Statement;

@Path("/task")
public class TaskResource {
    @GET
    @Produces("text/json")
    public String getAll() {
        JDBCConnect jdbc = new JDBCConnect();
        jdbc.setJdbc();
        String response;

        try (Statement statement = jdbc.getJdbc().createStatement()) {
            response = JDBCConnect.resultSetJSONStringify(statement.executeQuery("SELECT * FROM task"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        return response;
    }
}

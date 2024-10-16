package com.ktodo_back;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Path("/list")
public class ListRessource {

    @GET
    @Produces("text/plain")
    public String getAll() {
        ResultSet result;
        JDBCConnect jdbc = new JDBCConnect("root", "CleCleSam-974!!", "localhost");
        jdbc.setJdbc();
        try {
            Statement stmt = jdbc.getJdbc().createStatement();
            result = stmt.executeQuery("SELECT * FROM list ORDER BY update_timestamp DESC");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "ye";
    }
}

package com.example.moviefilter.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Filter {
    ConnectDB db;
    Connection connection;

    public Filter() {
        this.db = new ConnectDB();
        this.connection = db.getConnection();
    }

    // Retrieves all movies from the database
    public ObservableList<Movie> getAllMovies() {
        
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        if (this.connection != null) {
            try {
                PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM movies;");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    movies.add(new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDouble("rating"), 
                        rs.getInt("runtime"), 
                        rs.getInt("year"), 
                        rs.getString("genre")));
                }
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }
}

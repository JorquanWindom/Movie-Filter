package com.example.moviefilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.example.moviefilter.models.ConnectDB;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MovieFilterApp extends Application {
    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieFilterApp.class.getResource("movie-filter-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Movies");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        //populateDB();
        launch();
    }

    // Reads Movie data from tsv files and populates the database
    public static void populateDB() {

        String file = "src\\main\\resources\\com\\example\\moviefilter\\title.basics.tsv";
        
        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate("DROP TABLE IF EXISTS movies;");
                String createTableQuery = "CREATE TABLE IF NOT EXISTS movies (id INTEGER PRIMARY KEY NOT NULL, title TEXT NOT NULL, rating REAL DEFAULT 0, runtime INTEGER, year INTEGER, genre TEXT);";
                statement.executeUpdate(createTableQuery);
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement("INSERT INTO movies (id, title, runtime, year, genre) VALUES (?, ?, ?, ?, ?);");
                Files.lines(Paths.get(file))
                    .map(row -> row.split("\t"))
                    .filter(parts -> parts[1].equals("movie")
                        && parts.length == 9
                        && !parts[5].equals("\\N")
                        && !parts[7].equals("\\N")
                        && !parts[8].equals("\\N"))
                    .forEach(parts -> {

                        try {
                            ps.setInt(1, Integer.valueOf(parts[0].replace("tt", "")));
                            ps.setString(2, parts[2]);
                            ps.setInt(3, Integer.valueOf(parts[7]));
                            ps.setInt(4, Integer.valueOf(parts[5]));
                            ps.setString(5, parts[8]);
                            ps.addBatch();
                        } catch (Exception e) {
                            System.out.println("Bad row.");
                        }
                    });

                    ps.executeBatch();
                    connection.commit();
                    ps.close();

                    file = "src\\main\\resources\\com\\example\\moviefilter\\title.ratings.tsv";
                    PreparedStatement ps2 = connection.prepareStatement("UPDATE movies SET rating = ? WHERE id = ?;");
                    
                    Files.lines(Paths.get(file))
                        .map(row -> row.split("\t"))
                        .filter(parts -> !parts[1].equals("\\N"))
                        .forEach(parts -> {
                            try {
                                ps2.setDouble(1, Double.valueOf(parts[1]));
                                ps2.setInt(2, Integer.valueOf(parts[0].replace("tt", "")));
                                ps2.addBatch();
                            } catch (Exception e) {
                                System.out.println("Bad row.");
                            }
                        });
                        ps2.executeBatch();
                        connection.commit();
                        ps2.close();

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());

            }
        }
    }
}
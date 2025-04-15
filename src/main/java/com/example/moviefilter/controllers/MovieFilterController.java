package com.example.moviefilter.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.moviefilter.models.ConnectDB;
import com.example.moviefilter.models.Movie;
import com.example.moviefilter.models.RatingFilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MovieFilterController implements Initializable{
    @FXML
    private TextField searchField;

    @FXML
    private TableView<Movie> movieTable;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    @FXML
    private TableColumn<Movie, Integer> yearColumn;

    @FXML
    private TableColumn<Movie, Integer> runtimeColumn;

    @FXML
    private TableColumn<Movie, Double> ratingColumn;

    @FXML
    private TableColumn<Movie, String> genreColumn;

    @FXML
    private RadioButton ratingButton;

    @FXML
    private RadioButton genreButton;

    @FXML
    private RadioButton yearAscButton;

    @FXML
    private RadioButton yearDescButton;

    @FXML
    private ComboBox<String> genreComboBox;

    private String selectedButton = "";
    private static ConnectDB db = new ConnectDB();
    private static Connection connection = db.getConnection();
    private RatingFilter filter = new RatingFilter();
    private final ObservableList<Movie> allMovies = filter.getAllMovies();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTable(allMovies);

        genreComboBox.setItems(getGenres());
        genreComboBox.setDisable(true);
        genreComboBox.setValue("Filter Genre");

        // Filters movies by genre
        genreComboBox.setOnAction(event -> {
            String genre = String.valueOf(genreComboBox.getValue());
            resetFilters("genre");
            if (genre.equals("Filter Genre") && selectedButton.equals("genre")) {
                populateTable(allMovies);
                return;
            }
            if (selectedButton.equals("genre")) {
                ObservableList<Movie> genreFiltered = FXCollections.observableArrayList();
                for (Movie movie : allMovies) {
                    if (movie.getGenre().contains(genre)) {
                        genreFiltered.add(movie);
                    }
                }
                populateTable(genreFiltered);
            }
        });
    }

    public void populateTable(ObservableList<Movie> movies) {
        movieTable.refresh();
        movieTable.setItems(movies);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        runtimeColumn.setCellValueFactory(new PropertyValueFactory<>("runtime"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }

    // Provides search bar functionality
    @FXML
    public void onKeyTyped() {
        String searchFor = searchField.getText().toLowerCase();
        if (searchFor.isBlank()) {
            populateTable(allMovies);

        } else {
            ObservableList<Movie> movies = FXCollections.observableArrayList();
            for (Movie movie : allMovies) {
                if (movie.getTitle().toLowerCase().contains(searchFor)) {
                    movies.add(movie);
                }
            }
            populateTable(movies);
            resetFilters("search");
            selectedButton = "";
        }
    }

    // Enables the genre combo box
    @FXML
    @SuppressWarnings("exports")
    public void onClickGenre(ActionEvent actionEvent) {
        if (selectedButton.equals("genre")) {
            return;
        }
        selectedButton = "genre";
        genreComboBox.setDisable(false);
    }

    // Filters movies by rating
    @FXML
    @SuppressWarnings("exports")
    public void onClickRating(ActionEvent actionEvent) {
        resetFilters("rating");
        if (selectedButton.equals("rating")) {
            return;
        }
        populateTable(filter.ratingFilter(allMovies));
        selectedButton = "rating";
    }

    // Filters movies by year in ascending order
    @FXML
    @SuppressWarnings("exports")
    public void onClickYearAsc(ActionEvent actionEvent) {
        resetFilters("yearAsc");
        yearAscButton.setSelected(true);
        if (selectedButton.equals("yearAsc")) {
            return;
        }
        populateTable(filter.yearAscFilter(allMovies));
        selectedButton = "yearAsc";
    }

    // Filters movies by year in descending order
    @FXML
    @SuppressWarnings("exports")
    public void onClickYearDesc(ActionEvent actionEvent) {
        resetFilters("yearDesc");
        yearDescButton.setSelected(true);
        if (selectedButton.equals("yearDesc")) {
            return;
        }
        populateTable(filter.yearDescFilter(allMovies));
        selectedButton = "yearDesc";
    }


    // Retrieves a list of genres from the database
    public ObservableList<String> getGenres() {

        ObservableList<String> allGenres = FXCollections.observableArrayList();
        allGenres.add("Filter Genre");
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT genre FROM movies;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String[] genres = rs.getString("genre").split(",");
                for (String genre : genres) {
                        if (!allGenres.contains(genre)) {
                            allGenres.add(genre);
                        }
                    }
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allGenres;
    }

    // Ensures that only one filter is active
    public void resetFilters(String filter) {
        if (!filter.equals("yearAsc")) {
            yearAscButton.setSelected(false);
        }
        if (!filter.equals("yearDesc")) {
            yearDescButton.setSelected(false);
        }
        if (!filter.equals("rating")) {
            ratingButton.setSelected(false);
        }
        if (!filter.equals("search")) {
            searchField.setText("");
        }
        if (!filter.equals("genre")) {
            genreButton.setSelected(false);
            genreComboBox.setDisable(true);
            genreComboBox.setValue("Filter Genre");
        }
    }
}
package com.example.moviefilter.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectDB {

    private Connection connection;


    public ConnectDB() {

        String url = "jdbc:sqlite:src\\main\\resources\\database\\moviedb.db";

        try {

            connection = DriverManager.getConnection(url);

            System.out.println("Connection Successful");

        } catch (SQLException e) {

            System.out.println("Error Connecting to Database");

            e.printStackTrace();

        }

    }


    @SuppressWarnings("exports")
    public Connection getConnection() {

        return connection;

    }


    public void closeConnection() {

        try {

            if (connection != null) {

                System.out.println("Connection Closed");

                connection.close();

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}

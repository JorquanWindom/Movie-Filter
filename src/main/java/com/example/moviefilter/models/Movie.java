package com.example.moviefilter.models;

public class Movie {
    private int id;
    private String title;
    private double rating;
    private int runtimeMinutes;
    private int year;
    private String genre;

    public Movie(int id, String title, double rating, int runtime, int year, String genre) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.runtimeMinutes = runtime;
        this.year = year;
        this.genre = genre;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getTitle() {
        return this.title;
    }

    public double getRating(){
        return this.rating;
    }
    
    public int getRuntime() {
        return this.runtimeMinutes;
    }

    public int getYear() {
        return this.year;
    }

    public String getGenre() {
        return this.genre;
    }
}

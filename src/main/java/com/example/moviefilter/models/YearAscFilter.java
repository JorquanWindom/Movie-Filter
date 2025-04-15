package com.example.moviefilter.models;

import java.util.ArrayList;

import javafx.collections.ObservableList;

public class YearAscFilter extends YearDescFilter{
    public YearAscFilter() {
        super();
    }

    // Returns the sorted list
    public ObservableList<Movie> yearAscFilter(ObservableList<Movie> movieList) {
        
        ArrayList<Movie> allMovies = this.observableListToArrayList(movieList);
        mergeSort(allMovies);
        ObservableList<Movie> yearDescMovies = this.arrayListToObservableList(allMovies);
        return yearDescMovies;
    }

    // Sorts movies by year ascending
    private static void mergeSort(ArrayList<Movie> movies) {

        int arraySize = movies.size();
        
        if (arraySize < 2) {
            return;
        }

        int midIndex = arraySize / 2;
        ArrayList<Movie> leftHalf = new ArrayList<>(); 
        ArrayList<Movie> rightHalf = new ArrayList<>();

        for (int i = 0; i < midIndex; i++) {
            leftHalf.add(movies.get(i));
        }

        for (int i = midIndex; i < arraySize; i++) {
            rightHalf.add(movies.get(i));
        }

        mergeSort(leftHalf);
        mergeSort(rightHalf);

        merge(movies, leftHalf, rightHalf);
    }

    private static void merge(ArrayList<Movie> movies, ArrayList<Movie> leftHalf, ArrayList<Movie> rightHalf) {

        int leftSize = leftHalf.size();
        int rightSize = rightHalf.size();

        int i = 0, j = 0, k = 0;

        while (i < leftSize && j < rightSize) {
            if (leftHalf.get(i).getYear() <= rightHalf.get(j).getYear()) {
                movies.set(k, leftHalf.get(i));
                i++;

            } else {
                movies.set(k, rightHalf.get(j));
                j++;
            }
            k++;
        }

        while (i < leftSize) {
            movies.set(k, leftHalf.get(i));
            i++;
            k++;
        }

        while (j < rightSize) {
            movies.set(k, rightHalf.get(j));
            j++;
            k++;
        }
    }
}

# Movie Filter App

A JavaFX desktop application that filters movies using IMDB `.tsv` data and a SQLite database.

## Features

- Load and Parse large IMDB datasets
- Search and Filter movies by title, year, genre, and rating
- Simple UI built with JavaFX

## Technologies Used

- Java 11 (OpenJDK 11.0.26 - Temurin)
- JavaFX 17.0.2
- SQLite JDBC (org.xerial:sqlite-jdbc:3.49.1.0)
- Maven

## Setup Instructions

1. Clone this repostitory:
    ```
    git clone https://github.com/JorquanWindom/movie-filter-app.git
    ```

2. Make sure you have the following installed:
    - Java JDK (version 11 or later)
    - Maven
    - JavaFX (Version 17.0.2)
    - SQLite JDBC driver

3. Open the project in your Java IDE.

4. Build the project using Maven:
    ```
    mvn clean install
    ```

5. Run MovieFilterApp.java to launch the app.

## Project Structure
![Project Structure Screenshot](src/main/resources/com/example/moviefilter/images/Screenshot%202025-04-15%20160303.png)

## Usage
- Start the app
- Use the radio buttons or text input to search and filter

## Future Improvements
- Implement using multiple filters at the same time
- Add a view that displays more detailed movie information
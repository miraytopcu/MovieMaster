package org.example;

public class Movie {
    private String rank;
    private String name;
    private String year;
    private String imdbRating;
    private String duration;
    private String genre;
    private String directorName;
    private int rating;  // User's rating

    // Constructor
    public Movie(String rank, String name, String year, String imdbRating, String duration, String genre, String directorName) {
        this.rank = rank;
        this.name = name;
        this.year = year;
        this.imdbRating = imdbRating;
        this.duration = duration;
        this.genre = genre;
        this.directorName = directorName;
        this.rating = -1;  // no rating
    }

    // Setters and Getters
    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public String toString() {
        return String.join(", ", rank, name, year, imdbRating, duration, genre, directorName);
    }
}



package org.example;
import java.io.*;
import java.util.*;

public class MovieLoader {
    public static List<Movie> loadMovies(String filePath) {
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Movie movie = new Movie(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                movies.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }
}

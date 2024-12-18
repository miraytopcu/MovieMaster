package org.example;
import java.util.*;

public class GenreManager {
    private List<Movie> movies;

    public GenreManager(List<Movie> movies) {
        this.movies = movies;
    }

    // Method to group movies by genre
    public Map<String, List<Movie>> groupMoviesByGenre() {
        Map<String, List<Movie>> genreMap = new HashMap<>();
        for (Movie movie : movies) {
            String genre = movie.getGenre().toLowerCase();
            genreMap.putIfAbsent(genre, new ArrayList<>());
            genreMap.get(genre).add(movie);
        }
        return genreMap;
    }

    // Method to get 3 random movies from the selected genre
    public List<Movie> getRandomMoviesByGenre(String genre) {
        Map<String, List<Movie>> genreMap = groupMoviesByGenre();
        List<Movie> genreMovies = genreMap.getOrDefault(genre.toLowerCase(), new ArrayList<>());

        // If there were less than 3 movies in this genre, return all
        Collections.shuffle(genreMovies);
        return genreMovies.subList(0, Math.min(3, genreMovies.size()));
    }

    // Method to display genres for user selection
    public void displayGenres() {
        Set<String> genres = groupMoviesByGenre().keySet();
        System.out.println("Available genres:");
        int index = 1;
        for (String genre : genres) {
            System.out.println(index++ + ". " + genre);
        }
    }

    // Method to allow the user to select a genre
    public String selectGenre() {
        Scanner scanner = new Scanner(System.in);
        displayGenres();

        String genreChoice = "";
        while (true) {
            System.out.print("Please choose a genre by number: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= groupMoviesByGenre().size()) {
                    List<String> genreList = new ArrayList<>(groupMoviesByGenre().keySet());
                    genreChoice = genreList.get(choice - 1);
                    break;
                } else {
                    System.out.println("Invalid choice. Please choose a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
            }
        }

        return genreChoice;
    }
}


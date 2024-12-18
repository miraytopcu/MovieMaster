package org.example;
import org.apache.commons.text.similarity.LevenshteinDistance;
import java.util.*;

public class MovieMatcher {
    // if there was no exact matching, shows similar options
    // match as 'similar enough'
    private static final int SIMILARITY_THRESHOLD = 5;

    public static List<Movie> findSimilarMovies(String userInput, List<Movie> movies) {
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        List<Movie> similarMovies = new ArrayList<>();

        for (Movie movie : movies) {
            int distance = levenshtein.apply(userInput.toLowerCase(), movie.getName().toLowerCase());
            if (distance <= SIMILARITY_THRESHOLD) {
                similarMovies.add(movie);
            }
        }

        return similarMovies;
    }

    public static Movie promptForMovieChoice(List<Movie> similarMovies) {
        if (similarMovies.size() == 1) {
            return similarMovies.get(0);
        }

        System.out.println("Multiple similar movies found. Please choose one:");
        for (int i = 0; i < similarMovies.size(); i++) {
            System.out.println((i + 1) + ". " + similarMovies.get(i).getName());
        }

        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice < 1 || choice > similarMovies.size()) {
            System.out.print("Enter the number of the movie you want to add: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 1 || choice > similarMovies.size()) {
                    System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        return similarMovies.get(choice - 1);
    }
}


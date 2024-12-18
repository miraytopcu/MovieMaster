package org.example;
import java.util.*;

public class MovieRecommendationSystem {
    public static void main(String[] args) {
        String moviesFilePath = "src/main/resources/movies.csv";
        String watchlistFilePath = "src/main/resources/user_list.xlsx";

        List<Movie> movies = MovieLoader.loadMovies(moviesFilePath);
        GenreManager genreManager = new GenreManager(movies);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select the action you want to perform:");
            System.out.println("1. Add a movie");
            System.out.println("2. Remove a movie from watchlist");
            System.out.println("3. Mark a movie as watched");
            System.out.println("4. View lists");
            System.out.print("Enter the number corresponding to your choice (1/2/3/4): ");
            String actionChoice = scanner.nextLine().trim();

            switch (actionChoice) {
                case "1":
                    handleAddMovieAction(scanner, genreManager, movies, watchlistFilePath);
                    break;
                case "2":
                    handleRemoveMovieAction(scanner, watchlistFilePath);
                    break;
                case "3":
                    handleMarkMovieAsWatchedAction(scanner, watchlistFilePath , movies);
                    break;
                case "4":
                    handleViewListsAction(scanner, watchlistFilePath);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.print("Do you want to perform another action? (no to exit): ");
            String continueResponse = scanner.nextLine().trim().toLowerCase();
            if (continueResponse.equals("no")) {
                System.out.println("Exiting the system. Have a great day!");
                break; // Exit the loop
            } else {
                System.out.println("----------------------------------------");
            }
        }
    }

    private static void handleAddMovieAction(Scanner scanner, GenreManager genreManager, List<Movie> movies, String watchlistFilePath) {
        System.out.println("Would you like to add to the Watchlist or Watchedlist?");
        System.out.println("1. Watchlist");
        System.out.println("2. Watchedlist");
        System.out.print("Enter the number corresponding to your choice (1/2): ");
        String listChoice = scanner.nextLine().trim().equals("1") ? "watch_list" : "watched_list";

        System.out.println("Which action would you like to perform?");
        System.out.println("1. Get suggestions by genre");
        System.out.println("2. Search for a movie");
        System.out.print("Please select the action you want to perform (1/2): ");
        String stepChoice = scanner.nextLine().trim();

        Movie selectedMovie;
        if (stepChoice.equals("1")) { // Get suggestions by genre
            String genreChoice = genreManager.selectGenre();
            List<Movie> randomMovies = genreManager.getRandomMoviesByGenre(genreChoice);

            System.out.println("Here are 3 random movies from the selected genre:");
            for (int i = 0; i < randomMovies.size(); i++) {
                System.out.println((i + 1) + ". " + randomMovies.get(i).getName());
            }

            System.out.print("Please select a movie by number: ");
            int movieChoice = scanner.nextInt();
            selectedMovie = randomMovies.get(movieChoice - 1);
            scanner.nextLine();

        } else if (stepChoice.equals("2")) { // Search for a movie
            System.out.print("Enter the movie name you are looking for: ");
            String movieName = scanner.nextLine().trim();
            Optional<Movie> exactMatch = movies.stream()
                    .filter(movie -> movie.getName().equalsIgnoreCase(movieName))
                    .findFirst();

            if (exactMatch.isPresent()) {
                selectedMovie = exactMatch.get();
            } else {
                List<Movie> similarMovies = MovieMatcher.findSimilarMovies(movieName, movies);
                if (similarMovies.isEmpty()) {
                    System.out.println("No similar movies found.");
                    return;
                } else if (similarMovies.size() == 1) {
                    selectedMovie = similarMovies.get(0);
                } else {
                    selectedMovie = MovieMatcher.promptForMovieChoice(similarMovies);
                }
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        if (listChoice.equals("watched_list")) {
            RatingManager.rateMovie(selectedMovie, listChoice, watchlistFilePath);
        } else {
            ListUpdater.addMovieToList(selectedMovie, watchlistFilePath, listChoice);
        }
    }

    private static void handleRemoveMovieAction(Scanner scanner, String watchlistFilePath) {
        System.out.print("Enter the movie name you want to remove from the watchlist: ");
        String movieName = scanner.nextLine().trim();
        MovieRemovalManager.removeMovieFromWatchlist(movieName, watchlistFilePath);
    }

    private static void handleMarkMovieAsWatchedAction(Scanner scanner, String watchlistFilePath , List<Movie> movies) {
        Movie selectedMovie;
        System.out.print("Enter the movie name you have watched: ");
        String movieName = scanner.nextLine().trim();
        Optional<Movie> exactMatch = movies.stream()
                .filter(movie -> movie.getName().equalsIgnoreCase(movieName))
                .findFirst();

        String listChoice = "watched_list";

        if (exactMatch.isPresent()) {
            selectedMovie = exactMatch.get();
            RatingManager.rateMovie(selectedMovie, listChoice, watchlistFilePath);
            handleRemoveMovieAction(scanner , watchlistFilePath);
        }
        else{
            System.out.println("Invalid choice. Please try again.");
        }

    }

    private static void handleViewListsAction(Scanner scanner, String watchlistFilePath) {
        System.out.println("Which list would you like to view?");
        System.out.println("1. Watchlist");
        System.out.println("2. Watchedlist");
        System.out.println("3. Favoritelist");
        System.out.print("Enter the number corresponding to your choice (1/2/3): ");
        String listChoice = scanner.nextLine().trim();

        ListViewer.viewList(listChoice, watchlistFilePath);
    }
}


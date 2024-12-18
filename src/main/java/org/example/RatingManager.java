package org.example;

import java.util.Scanner;

public class RatingManager {

    public static void rateMovie(Movie movie, String listChoice, String watchlistFilePath) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your rating for '" + movie.getName() + "' (0 to 10): ");
        int rating = scanner.nextInt();

        movie.setRating(rating);

        if (rating == 10) {
            System.out.println("This movie has been added to your favorite list.");
            ListUpdater.addMovieToList(movie, watchlistFilePath, "favorite_list");
        }

        // Add to the chosen list
        ListUpdater.addMovieToList(movie, watchlistFilePath, listChoice);

        // System.out.println("Movie '" + movie.getName() + "' has been rated " + rating + ".");
    }
}





package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class MovieRemovalManager {

    // String moviesFilePath = "src/main/resources/movies.csv";
    // static String watchlistFilePath = "src/main/resources/user_list.xlsx";

    // Method to remove a movie from the watchlist
    public static void removeMovieFromWatchlist(String movieName, String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("watch_list");
            if (sheet == null) {
                System.out.println("Error: The sheet 'watch_list' does not exist.");
                return;
            }

            // Find the row with the movie name
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String existingMovieName = getCellStringValue(row.getCell(2));
                if (existingMovieName.equalsIgnoreCase(movieName)) {
                    sheet.removeRow(row);
                    System.out.println("Movie '" + movieName + "' has been removed from your watchlist.");
                    break;
                }
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get the cell value as a String (for errors)
    private static String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return "";
        }
    }
}




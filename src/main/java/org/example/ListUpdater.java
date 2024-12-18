package org.example;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.Objects;

public class ListUpdater {

    // check movie is already in the sheet
    public static boolean isMovieInSheet(Movie movie, Sheet sheet) {
        if (sheet == null) {
            System.out.println("Error: Sheet is null. Cannot proceed with duplicate check.");
            return false;
        }

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String existingMovieName = row.getCell(2).getStringCellValue();
            if (existingMovieName.equalsIgnoreCase(movie.getName())) {
                return true;
            }
        }
        return false;
    }

    public static void addMovieToList(Movie movie, String filePath, String listName) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(listName);
            if (sheet == null) {
                System.out.println("Error: The sheet '" + listName + "' does not exist in the workbook.");
                return;
            }

            // Check for duplicates in the sheet
            if (isMovieInSheet(movie, sheet)) {
                System.out.println("This movie is already in your " + listName + ". Addition not possible.");
                return;
            }

            // Get the current row count, skipping the header row (index 0)
            int rowCount = sheet.getPhysicalNumberOfRows();
            Row newRow = sheet.createRow(rowCount);

            newRow.createCell(0).setCellValue(rowCount);

            String[] movieData = movie.toString().split(", ");
            for (int i = 0; i < movieData.length; i++) {
                newRow.createCell(i + 1).setCellValue(movieData[i]);
            }

            if(Objects.equals(listName, "watched_list") || Objects.equals(listName, "favorite_list")){
                newRow.createCell(8).setCellValue(movie.getRating());
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

            System.out.println("Movie added to your " + listName + ".");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ListViewer {
    // class for view list's movies

    public static void viewList(String listChoice, String watchlistFilePath) {
        String sheetName;

        switch (listChoice) {
            case "1":
                sheetName = "watch_list";
                break;
            case "2":
                sheetName = "watched_list";
                break;
            case "3":
                sheetName = "favorite_list";
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }

        try (FileInputStream fis = new FileInputStream(watchlistFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.out.println("Error: The sheet '" + sheetName + "' does not exist.");
                return;
            }

            System.out.println("Movies in your " + sheetName.replace("_", " ") + ":");
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) continue; // Skip header row
                System.out.println("- " + row.getCell(2).getStringCellValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

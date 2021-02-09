package com.example.RootApp;


import com.example.RootApp.entity.Driver;
import com.example.RootApp.sorter.DriverSorter;
import com.example.RootApp.sorter.SortMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class DriverHistoryCalculator {

    private static final String driverRegex = "^Driver(\\s+)(\\w+)";
    private static final String tripRegex = "^Trip(\\s+)(\\w+)(\\s+)([01]?[0-9]|2[0-3]):[0-5][0-9](\\s+)([01]?[0-9]|2[0-3]):[0-5][0-9](\\s+)(\\d+(?:.\\d)?)$";

    public static void main(String[] args) throws FileNotFoundException {

        if (args.length == 1) {
            throw new IllegalArgumentException("Input file should be passed");
        }
        else if(args.length>1) {
            throw new IllegalArgumentException("Single file should be passed, multiple files found");
        }
        File file = new File(args[0]);
        DriverHistoryFileReader driverHistoryFileReader = new DriverHistoryFileReader(file, driverRegex, tripRegex);
        List<Driver> driversHistory = driverHistoryFileReader.fetchDriverHistory(5, 100);
        List<Driver> driversSortedByMilesDriven = DriverSorter.sortByMilesDriven(driversHistory, SortMode.DESCENDING);
        printDriversHistory(driversSortedByMilesDriven);
    }

    private static void printDriversHistory(List<Driver> driversHistory) {

        driversHistory.stream()
                .forEach(driver -> {
                    long milesTravelled = Math.round(driver.getTotalMilesTravelled());
                    long averageSpeed = Math.round(driver.getAverageSpeed());
                    String message = null;
                    if (milesTravelled == 0) {
                        message = String.format("%s: 0 miles", driver.getName());
                    } else {
                        message = String.format("%s: %s miles @ %s mph",
                                driver.getName(),
                                milesTravelled,
                                averageSpeed);
                    }
                    System.out.println(message);
                });
    }
}

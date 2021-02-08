package com.example.RootApp.sorter;

import com.example.RootApp.entity.Driver;
import lombok.NonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DriverSorter {

    /**
     * Sorts drivers based on average speed and returns list of sorted drivers based on the sort mode passed
     * @param drivers drivers that need to be sorted
     * @param sortMode
     *      if sortMode = ASCENDING, then drivers get sorted in ascending order,
     *      else sort in descending order.
     */
    public static List<Driver> sortByMilesDriven(@NonNull List<Driver> drivers,
                                                 @NonNull SortMode sortMode) {

        int multiPlier = sortMode.equals(SortMode.ASCENDING) ? 1 : -1;

        return drivers.stream()
              .sorted(Comparator.comparing(driver -> multiPlier * driver.getTotalMilesTravelled()))
              .collect(Collectors.toList());
    }
}


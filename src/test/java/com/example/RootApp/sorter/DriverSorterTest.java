package com.example.RootApp.sorter;

import com.example.RootApp.entity.Driver;
import com.example.RootApp.entity.Trip;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertThat;

@SpringBootTest
public class DriverSorterTest {

    private static final String DRIVER_NAME_1 = "DRIVER_1";
    private static final String DRIVER_NAME_2 = "DRIVER_2";
    private static final double TRIP_1_MILES = 12.2;
    private static final double TRIP_2_MILES = 14.2;
    private Driver driver1;
    private Driver driver2;

    @BeforeEach
    public void setup() {
        Trip trip1 = getTrip(DRIVER_NAME_1, TRIP_1_MILES);
        driver1 = getDriver(DRIVER_NAME_1, trip1);

        Trip trip2 = getTrip(DRIVER_NAME_2, TRIP_2_MILES);
        driver2 = getDriver(DRIVER_NAME_2, trip2);
    }

    @Test
    public void testSortDriversByMilesDrivenSortByAscendingOrder() {

        List<Driver> actualDriversSortedList = DriverSorter
              .sortByMilesDriven(Arrays.asList(driver1, driver2), SortMode.ASCENDING);
        List<Driver> expectedDriversSortedList = Arrays.asList(driver1, driver2);
        assertThat(expectedDriversSortedList, Matchers.is(actualDriversSortedList));
    }

    @Test
    public void testSortDriversByMilesDrivenSortByDescendingOrder() {

        List<Driver> actualDriversSortedList = DriverSorter
              .sortByMilesDriven(Arrays.asList(driver1, driver2), SortMode.DESCENDING);
        List<Driver> expectedDriversSortedList = Arrays.asList(driver2, driver1);
        assertThat(expectedDriversSortedList, Matchers.is(actualDriversSortedList));
    }

    private Driver getDriver(String name, Trip trip) {
        Driver driver = new Driver(name);
        driver.addTrip(trip);
        return driver;
    }

    private Trip getTrip(String driverName, double milesDriven) {
        return new Trip(driverName, new Date(100), new Date(200), milesDriven);
    }
}

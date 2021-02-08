package com.example.RootApp;

import com.example.RootApp.entity.Driver;
import com.example.RootApp.entity.Trip;
import com.example.RootApp.exceptions.DuplicateRequestException;
import com.example.RootApp.exceptions.InvalidDriverException;
import com.example.RootApp.exceptions.InvalidTripException;
import org.hamcrest.Matchers;
import org.junit.Assert;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class DriverHistoryFileReaderTest {

    private static final String DRIVER_REGEX = "^Driver(\\s+)(\\w+)";
    private static final String TRIP_REGEX = "^Trip(\\s+)(\\w+)(\\s+)([01]?[0-9]|2[0-3]):[0-5][0-9](\\s+)([01]?[0-9]|2[0-3]):[0-5][0-9](\\s+)(\\d+(?:.\\d)?)$";
    private static final Double MIN_AVG_SPEED = Double.valueOf(5);
    private static final Double MAX_AVG_SPEED = Double.valueOf(100);

    private static final String DRIVER_DAN_NAME = "Dan";
    private static final String DRIVER_LAUREN_NAME = "Lauren";
    private static final String DRIVER_KUMI_NAME = "Kumi";

    private Driver dan;
    private Driver lauren;
    private Driver kumi;

    private Trip dan_trip_1;
    private Trip dan_trip_2;
    private Trip lauran_trip_1;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeEach
    public void setup() {
        dan = new Driver(DRIVER_DAN_NAME);
        lauren = new Driver(DRIVER_LAUREN_NAME);
        kumi = new Driver(DRIVER_KUMI_NAME);

        dan_trip_1 = new Trip(DRIVER_DAN_NAME, getDate(7, 15, 0), getDate(7,45, 0), 17.3);
        dan_trip_2 = new Trip(DRIVER_DAN_NAME, getDate(6, 12, 0), getDate(6,32, 0), 21.8);
        lauran_trip_1 = new Trip(DRIVER_LAUREN_NAME, getDate(12, 1, 0), getDate(13,16, 0), 42.0);
    }

    @Test
    public void testGetDriverHistoryWithValidDetails() throws FileNotFoundException {
        File file = new File("src/test/tst-resources/validTestFile");
        List<Driver> actualDrivers = new DriverHistoryFileReader(file, DRIVER_REGEX, TRIP_REGEX).fetchDriverHistory(MIN_AVG_SPEED, MAX_AVG_SPEED);

        dan.addTrips(Arrays.asList(dan_trip_1, dan_trip_2));
        lauren.addTrips(Arrays.asList(lauran_trip_1));
        List<Driver> expectedDrivers = Arrays.asList(dan, lauren, kumi);

        Assert.assertThat("Drivers should match", actualDrivers, Matchers.is(expectedDrivers));
    }

    @Test
    public void testGetDriverHistoryWhenTripStartDateIsAfterEndDate() throws FileNotFoundException {
        File file = new File("src/test/tst-resources/invalidTestWithEndTimeLessThanStart");
        String message = "Start time cannot be before end time";
        Exception exception = Assertions.assertThrows(InvalidTripException.class, () -> {
            new DriverHistoryFileReader(file, DRIVER_REGEX, TRIP_REGEX).fetchDriverHistory(MIN_AVG_SPEED, MAX_AVG_SPEED);
        });
        Assert.assertEquals(exception.getMessage(), message);
    }

    @Test
    public void testGetDriverHistoryWhenTripIsTakenByUnregisteredDriver() throws FileNotFoundException {
        File file = new File("src/test/tst-resources/invalidTestWithDriverMissing");
        String message = "Drivers who aren't registered took trips";
        Exception exception = Assertions.assertThrows(InvalidDriverException.class, () -> {
            new DriverHistoryFileReader(file, DRIVER_REGEX, TRIP_REGEX).fetchDriverHistory(MIN_AVG_SPEED, MAX_AVG_SPEED);
        });
        Assert.assertEquals(exception.getMessage(), message);
    }

    @Test
    public void testGetDriverHistoryWhenDuplicateDriversRegister() throws FileNotFoundException {
        File file = new File("src/test/tst-resources/invalidTestWithDuplicateDrivers");
        String message = String.format("There is an existing driver with name %s hence cannot register same driver again", DRIVER_LAUREN_NAME);
        Exception exception = Assertions.assertThrows(DuplicateRequestException.class, () -> {
            new DriverHistoryFileReader(file, DRIVER_REGEX, TRIP_REGEX).fetchDriverHistory(MIN_AVG_SPEED, MAX_AVG_SPEED);
        });
        Assert.assertEquals(exception.getMessage(), message);
    }

    private Date getDate(int hours, int minutes, int seconds) {
        Date date = new Date(0);
        date.setHours(hours);
        date.setMinutes(minutes);
        date.setSeconds(seconds);
        return date;
    }
}

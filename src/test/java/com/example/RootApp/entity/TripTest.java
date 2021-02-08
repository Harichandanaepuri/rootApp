package com.example.RootApp.entity;

import com.example.RootApp.DriverHistoryFileReader;
import com.example.RootApp.exceptions.DuplicateRequestException;
import com.example.RootApp.exceptions.InvalidTripException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.Assert.assertThat;

@SpringBootTest
public class TripTest {

    public static final String DRIVER_NAME = "DRIVER_NAME";
    public static final Date DATE_1 = new Date(0);
    public static final Date DATE_2 = new Date(1);
    public static final Double MILES_TRAVELLED = Double.valueOf(10);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * Testing trip creation when all valid inputs are passed
     */
    @Test
    public void testTripCreation() {
        Trip trip = new Trip(DRIVER_NAME, DATE_1, DATE_2, MILES_TRAVELLED);
        assertThat("Driver name should be same: ", trip.getDriverName(), Matchers.is(DRIVER_NAME));
        assertThat("Miles travelled should be same: ", trip.getMilesTravelled(), Matchers.is(MILES_TRAVELLED));
        assertThat("Start date of trip should be same: ", trip.getStartTime(), Matchers.is(DATE_1));
        assertThat("End date of trip should be same: ", trip.getEndTime(), Matchers.is(DATE_2));
    }

    /**
     * Testing trip creation failure when miles travelled < 0
     */
    @Test
    public void testTripCreationWhenMilesTravelledAreNegative() {
        String message = "Trip cannot contain negative distance";
        Exception exception = Assertions.assertThrows(InvalidTripException.class, () -> {
            new Trip(DRIVER_NAME, DATE_1, DATE_2, -1*MILES_TRAVELLED);
        });
        Assert.assertEquals(exception.getMessage(), message);
    }

    /**
     * Testing trip creation failure when start date is after end-date
     */
    @Test
    public void testTripCreationWhenStartDateIsAfterEndDate() {
        String message = "Start time cannot be before end time";
        Exception exception = Assertions.assertThrows(InvalidTripException.class, () -> {
            new Trip(DRIVER_NAME, DATE_2, DATE_1, MILES_TRAVELLED);
        });
        Assert.assertEquals(exception.getMessage(), message);
    }
}

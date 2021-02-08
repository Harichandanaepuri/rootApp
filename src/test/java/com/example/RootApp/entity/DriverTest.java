package com.example.RootApp.entity;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;

import static com.example.RootApp.DriverHistoryConstants.DRIVER_NAME;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@SpringBootTest
public class DriverTest {

    private static final Date START_DATE_1 = new Date(1000000);
    private static final Date START_DATE_2 = new Date(2000000);
    private static final Date END_DATE_1 = new Date(3000000);
    private static final Date END_DATE_2 = new Date(4000000);
    private static final Double MILES_TRAVELLED_1 = Double.valueOf(10);
    private static final Double MILES_TRAVELLED_2 = Double.valueOf(20);

    /**
     * Test if a driver is successfully created when driver did 0 trips
     */
    @Test
    public void testDriverCreationWithEmptyTrips() {

        Driver driver = new Driver(DRIVER_NAME);
        Assert.assertTrue(driver.getTripsTaken().isEmpty());
    }

    /**
     * Test if a driver is successfully created when driver did 0 trips
     */
    @Test
    public void testAddTripToDriver() {

        Trip trip = new Trip(DRIVER_NAME, START_DATE_1, END_DATE_1, MILES_TRAVELLED_1);
        Driver actualDriver = new Driver(DRIVER_NAME);
        actualDriver.addTrip(trip);

        assertThat("Drivers should be be same", actualDriver.getTripsTaken().get(0), is(trip));
    }

    /**
     * Test if a driver is successfully created when driver did 0 trips
     */
    @Test
    public void testAddTripsToDriver() {

        Trip trip1 = new Trip(DRIVER_NAME, START_DATE_1, END_DATE_1, MILES_TRAVELLED_1);
        Trip trip2 = new Trip(DRIVER_NAME, START_DATE_2, END_DATE_2, MILES_TRAVELLED_1);

        Driver actualDriver = new Driver(DRIVER_NAME);
        actualDriver.addTrips(Arrays.asList(trip1, trip2));

        assertThat("Driver should be have same trips taken",
              actualDriver.getTripsTaken(),
              is(Arrays.asList(trip1, trip2)));
    }

    /**
     * Test average speed driver has driven with 0 trips
     */
    @Test
    public void testGetAverageSpeedWithZeroTrips() {
        Driver driver = new Driver(DRIVER_NAME);
        assertEquals(driver.getAverageSpeed(), Double.valueOf(0));
    }

    /**
     * Test average speed driver has driven with multiple trips
     */
    @Test
    public void testGetAverageSpeedWithMultipleTrips() {
        Trip trip1 = new Trip(DRIVER_NAME, START_DATE_1, END_DATE_1, MILES_TRAVELLED_1);
        Trip trip2 = new Trip(DRIVER_NAME, START_DATE_1, END_DATE_1, MILES_TRAVELLED_2);

        Driver driver = new Driver(DRIVER_NAME);
        driver.addTrips(Arrays.asList(trip1, trip2));

        double seconds = (END_DATE_1.getTime()-START_DATE_1.getTime() + END_DATE_2.getTime() -START_DATE_2.getTime())/1000;
        double expectedMilesTravelled = (MILES_TRAVELLED_1+MILES_TRAVELLED_2) * 3600 / seconds ;
        Double averageSpeed = driver.getAverageSpeed();
        assertEquals(averageSpeed, Double.valueOf(expectedMilesTravelled));
    }
}

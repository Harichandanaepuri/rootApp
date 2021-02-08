package com.example.RootApp.utils;

import com.example.RootApp.DriverHistoryFileReader;
import com.example.RootApp.exceptions.DuplicateRequestException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class SpeedUtilsTest {

    private static final double DISTANCE_TRAVELLED_IN_MILES = Double.valueOf(2000);
    private static final long TIME_TRAVELLED_IN_SECONDS = 4000;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetAverageSpeed() {
        double actualAverageSpeed = SpeedUtils.getAverageSpeedInMilesPerHour(DISTANCE_TRAVELLED_IN_MILES, TIME_TRAVELLED_IN_SECONDS);
        double expectedAverageSpeed = (DISTANCE_TRAVELLED_IN_MILES * 3600) / TIME_TRAVELLED_IN_SECONDS;
        assertEquals(expectedAverageSpeed, actualAverageSpeed, 0);
    }

    @Test
    public void testGetAverageSpeedWithZeroDistanceTravelled() {
        double actualAverageSpeed = SpeedUtils.getAverageSpeedInMilesPerHour(0, TIME_TRAVELLED_IN_SECONDS);
        assertEquals(0, actualAverageSpeed, -1*actualAverageSpeed);
    }

    @Test
    public void testGetAverageSpeedWithNonZeroDistanceAndTimeTravelledIsZero() {
        String message = "Invalid inputs passed, distance travelled > 0 and time = 0";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SpeedUtils.getAverageSpeedInMilesPerHour(DISTANCE_TRAVELLED_IN_MILES, 0);
        });
        Assert.assertEquals(exception.getMessage(), message);
    }
}



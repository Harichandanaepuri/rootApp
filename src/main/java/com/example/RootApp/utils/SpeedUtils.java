package com.example.RootApp.utils;

/**
 * Utility class for various operations related to speed
 *
 * @author chandana
 */
public class SpeedUtils {

    /**
     * fetch average speed in miles per hour
     */
    public static double getAverageSpeedInMilesPerHour(double totalMilesTravelled,
                                                       long timeTravelledInSec) {

        if(totalMilesTravelled == 0) {
            return 0;
        }

        if(timeTravelledInSec == 0) {
            throw new IllegalArgumentException("Invalid inputs passed, distance travelled > 0 and time = 0");
        }

        return (totalMilesTravelled * 3600) / timeTravelledInSec;
    }
}

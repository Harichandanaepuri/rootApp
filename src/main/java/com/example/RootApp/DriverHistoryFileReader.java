package com.example.RootApp;

import com.example.RootApp.entity.Driver;
import com.example.RootApp.entity.Trip;
import com.example.RootApp.exceptions.DuplicateRequestException;
import com.example.RootApp.exceptions.InvalidDriverException;
import com.example.RootApp.exceptions.InvalidTripException;
import com.example.RootApp.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.RootApp.constants.Constants.COLUN;
import static com.example.RootApp.utils.SpeedUtils.getAverageSpeedInMilesPerHour;

@AllArgsConstructor
public class DriverHistoryFileReader {

    private @NonNull File file;
    private @NonNull String driverDetailsRegex;
    private @NonNull String tripDetailsRegex;

    /**
     * Populate driver history by iterating through each lines and registering drivers and adding trips if applicable.
     * @param minAvgSpeed minimum average speed of a valid trip. If trip average speed < minAvgSpeed then ignore trip
     * @param maxAvgSpeed maximum average speed of a valid trip. If trip average speed > minAvgSpeed then ignore trip
     * @return Drivers
     * @throws FileNotFoundException when input file to read drivers history details doesn't exist.
     * @throws IllegalArgumentException
     *      - when min average speed > max average speed or either of them are negative
     *      - when file contain command types which doesn't match with driverDetails regex or trip details regex
     * @throws InvalidTripException when drivers who aren't registered take some trips
     */
    public List<Driver> fetchDriverHistory(double minAvgSpeed, double maxAvgSpeed) throws FileNotFoundException {

        if (minAvgSpeed <= 0 || maxAvgSpeed <= 0) {
            throw new IllegalArgumentException("MaxSpeed and min speed should be positive");
        }

        if (maxAvgSpeed < minAvgSpeed) {
            throw new IllegalArgumentException("Max speed cannot be less than min speed");
        }

        Pattern driverPattern = Pattern.compile(driverDetailsRegex);
        Pattern tripPattern = Pattern.compile(tripDetailsRegex);

        Scanner scanner = new Scanner(file);
        Map<String, Driver> drivers = new HashMap<>();
        Map<String, List<Trip>> driverNameToTrips = new HashMap<>();

        while (scanner.hasNextLine()) {
            String details = scanner.nextLine();
            if (driverPattern.matcher(details).find()) {
                Driver driver = getDriver(drivers, details);
                drivers.put(driver.getName(), driver);
                continue;
            } else if (tripPattern.matcher(details).find()) {
                Trip trip = getTrip(details);
                Double averageSpeed = getAverageSpeedInMilesPerHour(trip.getMilesTravelled(), trip.getTripDurationInSeconds());
                if (averageSpeed < minAvgSpeed || averageSpeed > maxAvgSpeed) {
                    continue;
                }
                driverNameToTrips.computeIfAbsent(trip.getDriverName(), trips -> new ArrayList<>())
                      .add(trip);
                continue;
            }
            throw new IllegalArgumentException("Invalid input passed, " + details);
        }

        if(!drivers.keySet().containsAll(driverNameToTrips.keySet())) {
            throw new InvalidDriverException("Drivers who aren't registered took trips");
        }

        return drivers.values()
              .stream()
              .map(driver -> {
                  String driverName = driver.getName();
                  if (driverNameToTrips.containsKey(driverName)) {
                      driver.addTrips(driverNameToTrips.get(driverName));
                  }
                  return driver;
              })
              .collect(Collectors.toList());
    }

    private static Trip getTrip(String tripDetails) {
        String[] details = tripDetails.split("\\s+");
        String driverName = details[1];
        Date startTime = DateUtils.getDate(details[2], COLUN);
        Date endTime = DateUtils.getDate(details[3], COLUN);
        Double milesTravelled = Double.valueOf(details[4]);

        return new Trip(driverName, startTime, endTime, milesTravelled);
    }

    private static Driver getDriver(Map<String, Driver> drivers, String line) {
        String driverName = line.split("\\s+")[1];

        if(drivers.containsKey(driverName)) {
            String message = String.format("There is an existing driver with name %s hence cannot register same driver again", driverName);
            throw new DuplicateRequestException(message);
        }

        return new Driver(driverName);
    }
}

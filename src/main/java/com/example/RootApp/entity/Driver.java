package com.example.RootApp.entity;

import com.example.RootApp.utils.SpeedUtils;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * POJO for Driver class. It contains details of all trips, miles, time travelled by a driver.
 *
 */
public class Driver {

    /**
     * Driver name
     */
    private String name;

    /**
     * List of trips driver taken.
     */
    private List<Trip> tripsTaken;

    /**
     * Total Miles driver has covered
     */
    private double totalMilesTravelled;

    /**
     * Total time driver has travelled in seconds
     */
    private long timeTravelledInSec;

    public Driver(String name) {
        this.name = name;
        this.tripsTaken = new ArrayList<>();
    }

    /**
     * Adds a trip to the drivers list.
     * @param trip
     *
     * @throws IllegalArgumentException thrown when driver name doesnt match with one given in trip
     */
    public void addTrip(@NonNull Trip trip) {
        validateTrip(trip);

        tripsTaken.add(trip);
        totalMilesTravelled += trip.getMilesTravelled();
        timeTravelledInSec += trip.getTripDurationInSeconds();
    }

    /**
     * Adds list of trips to driver.
     * @param trips trips taken by a driver.
     *
     * @throws IllegalArgumentException thrown when driver name doesnt match with one given in trip
     */
    public void addTrips(@NonNull List<Trip> trips) {
        for (Trip trip : trips) {
            addTrip(trip);
        }
    }

    public String getName() {
        return name;
    }

    public List<Trip> getTripsTaken() {
        return tripsTaken;
    }

    public Double getTotalMilesTravelled() {
        return totalMilesTravelled;
    }

    /**
     * Calculate average speed in miles/hr
     */
    public Double getAverageSpeed() {
        return SpeedUtils.getAverageSpeedInMilesPerHour(this.totalMilesTravelled, this.timeTravelledInSec);
    }

    private void validateTrip(Trip trip) {

        if(!trip.getDriverName().equals(getName())) {
            String errorMessage = String.format("Trip doesn't belong to driver %s", getName());
            throw new IllegalArgumentException(errorMessage);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Double.compare(driver.totalMilesTravelled, totalMilesTravelled) == 0 &&
              timeTravelledInSec == driver.timeTravelledInSec &&
              Objects.equals(name, driver.name) &&
              Objects.equals(tripsTaken, driver.tripsTaken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tripsTaken, totalMilesTravelled, timeTravelledInSec);
    }

    @Override
    public String toString() {
        return "Driver{" +
              "name='" + name + '\'' +
              ", tripsTaken=" + tripsTaken +
              ", totalMilesTravelled=" + totalMilesTravelled +
              ", timeTravelledInSec=" + timeTravelledInSec +
              '}';
    }
}

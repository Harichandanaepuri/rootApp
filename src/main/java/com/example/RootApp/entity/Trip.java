package com.example.RootApp.entity;

import com.example.RootApp.exceptions.InvalidTripException;
import com.example.RootApp.utils.DateUtils;
import lombok.NonNull;

import java.util.Date;
import java.util.Objects;

/**
 * POJO for Trip class.
 *
 */
public class Trip {

    /**
     * Name of the driver who took the trip
     */
    private String driverName;

    /**
     * Start time for trip
     */
    private Date startTime;

    /**
     * End time for trip
     */
    private Date endTime;

    /**
     * Miles travelled in a trip
     */
    private double milesTravelled;

    public Trip(@NonNull String driverName,
                @NonNull Date startTime,
                @NonNull Date endTime,
                @NonNull Double milesTravelled) {

        validateTripDetails(startTime, endTime, milesTravelled);
        this.driverName = driverName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.milesTravelled = milesTravelled;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public double getMilesTravelled() {
        return this.milesTravelled;
    }

    /**
     * Returns trip duration in seconds
     */
    public long getTripDurationInSeconds() {
        return (this.endTime.getTime() - this.startTime.getTime())/1000;
    }

    private void validateTripDetails(Date startTime, Date endTime, Double milesTravelled) {

        if(DateUtils.isAfter(startTime, endTime)) {
            throw new InvalidTripException("Start time cannot be before end time");
        }

        if(milesTravelled <0) {
            throw new InvalidTripException("Trip cannot contain negative distance");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Double.compare(trip.milesTravelled, milesTravelled) == 0 &&
              Objects.equals(driverName, trip.driverName) &&
              Objects.equals(startTime, trip.startTime) &&
              Objects.equals(endTime, trip.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverName, startTime, endTime, milesTravelled);
    }

    @Override
    public String toString() {
        return "Trip{" +
              "driverName='" + driverName + '\'' +
              ", startTime=" + startTime +
              ", endTime=" + endTime +
              ", milesTravelled=" + milesTravelled +
              '}';
    }
}

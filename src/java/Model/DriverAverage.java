package Model;

import Controller.Converter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DriverAverage {

    private String driverName;
    private int driverNumber;

    ArrayList<TripPeriod2X> abHighTripPeriods;
    ArrayList<TripPeriod2X> abLowTripPeriods;

    ArrayList<TripPeriod2X> baHighTripPeriods;
    ArrayList<TripPeriod2X> baLowTripPeriods;

    private Converter converter;

    public DriverAverage() {
        this.abHighTripPeriods = new ArrayList();
        this.abLowTripPeriods = new ArrayList();
        this.baHighTripPeriods = new ArrayList();
        this.baLowTripPeriods = new ArrayList();
        converter = new Converter();
    }

    public void addAbHighTripPeriod(TripPeriod2X tripPeriod) {
        this.abHighTripPeriods.add(tripPeriod);
    }

    public void addAbLowTripPeriod(TripPeriod2X tripPeriod) {
        this.abLowTripPeriods.add(tripPeriod);
    }

    //---------- 
    public void addBaHighTripPeriod(TripPeriod2X tripPeriod) {
        this.baHighTripPeriods.add(tripPeriod);
    }

    public void addBaLowTripPeriod(TripPeriod2X tripPeriod) {
        this.baLowTripPeriods.add(tripPeriod);
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(int driverNumber) {
        this.driverNumber = driverNumber;
    }

    public ArrayList<TripPeriod2X> getAbHighTripPeriods() {
        return abHighTripPeriods;
    }

    public void setAbHighTripPeriods(ArrayList<TripPeriod2X> abHighTripPeriods) {
        this.abHighTripPeriods = abHighTripPeriods;
    }

    public ArrayList<TripPeriod2X> getAbLowTripPeriods() {
        return abLowTripPeriods;
    }

    public void setAbLowTripPeriods(ArrayList<TripPeriod2X> abLowTripPeriods) {
        this.abLowTripPeriods = abLowTripPeriods;
    }

    public ArrayList<TripPeriod2X> getBaHighTripPeriods() {
        return baHighTripPeriods;
    }

    public void setBaHighTripPeriods(ArrayList<TripPeriod2X> baHighTripPeriods) {
        this.baHighTripPeriods = baHighTripPeriods;
    }

    public ArrayList<TripPeriod2X> getBaLowTripPeriods() {
        return baLowTripPeriods;
    }

    public void setBaLowTripPeriods(ArrayList<TripPeriod2X> baLowTripPeriods) {
        this.baLowTripPeriods = baLowTripPeriods;
    }

    public String getAbLowAverageString() {
        long totalTime = 0;
        for (TripPeriod tripPeriod : this.abLowTripPeriods) {
            LocalDateTime startTimeScheduled = tripPeriod.getStartTimeScheduled();;
            LocalDateTime startTimeActual = tripPeriod.getStartTimeActual();

            LocalDateTime arrivalTimeScheduled = tripPeriod.getArrivalTimeScheduled();
            LocalDateTime arrivalTimeActual = tripPeriod.getArrivalTimeActual();

            Duration tripPeriodTimeActual = Duration.between(startTimeActual, arrivalTimeActual);
            Duration tripPeriodTimeScheduled = Duration.between(startTimeScheduled, arrivalTimeScheduled);
            totalTime += tripPeriodTimeActual.getSeconds();

        }
        if (this.abLowTripPeriods.size() == 0) {
            return "";
        }

        return converter.convertDurationToString(Duration.ofSeconds(totalTime / this.abLowTripPeriods.size()));
    }

    public String getAbHighAverageString() {
        long totalTime = 0;
        for (TripPeriod tripPeriod : this.abHighTripPeriods) {
            LocalDateTime startTimeScheduled = tripPeriod.getStartTimeScheduled();;
            LocalDateTime startTimeActual = tripPeriod.getStartTimeActual();

            LocalDateTime arrivalTimeScheduled = tripPeriod.getArrivalTimeScheduled();
            LocalDateTime arrivalTimeActual = tripPeriod.getArrivalTimeActual();

            Duration tripPeriodTimeActual = Duration.between(startTimeActual, arrivalTimeActual);
            Duration tripPeriodTimeScheduled = Duration.between(startTimeScheduled, arrivalTimeScheduled);
            totalTime += tripPeriodTimeActual.getSeconds();

        }
        if (this.abHighTripPeriods.size() == 0) {
            return "";
        }

        return converter.convertDurationToString(Duration.ofSeconds(totalTime / this.abHighTripPeriods.size()));
    }

    //------------------
    public String getBaLowAverageString() {
        long totalTime = 0;
        for (TripPeriod tripPeriod : this.baLowTripPeriods) {
            LocalDateTime startTimeScheduled = tripPeriod.getStartTimeScheduled();;
            LocalDateTime startTimeActual = tripPeriod.getStartTimeActual();

            LocalDateTime arrivalTimeScheduled = tripPeriod.getArrivalTimeScheduled();
            LocalDateTime arrivalTimeActual = tripPeriod.getArrivalTimeActual();

            Duration tripPeriodTimeActual = Duration.between(startTimeActual, arrivalTimeActual);
            Duration tripPeriodTimeScheduled = Duration.between(startTimeScheduled, arrivalTimeScheduled);
            totalTime += tripPeriodTimeActual.getSeconds();

        }
        if (this.baLowTripPeriods.size() == 0) {
            return "";
        }

        return converter.convertDurationToString(Duration.ofSeconds(totalTime / this.baLowTripPeriods.size()));
    }

    public String getBaHighAverageString() {
        long totalTime = 0;
        for (TripPeriod tripPeriod : this.baHighTripPeriods) {
            LocalDateTime startTimeScheduled = tripPeriod.getStartTimeScheduled();;
            LocalDateTime startTimeActual = tripPeriod.getStartTimeActual();

            LocalDateTime arrivalTimeScheduled = tripPeriod.getArrivalTimeScheduled();
            LocalDateTime arrivalTimeActual = tripPeriod.getArrivalTimeActual();

            Duration tripPeriodTimeActual = Duration.between(startTimeActual, arrivalTimeActual);
            Duration tripPeriodTimeScheduled = Duration.between(startTimeScheduled, arrivalTimeScheduled);
            totalTime += tripPeriodTimeActual.getSeconds();

        }
        if (this.baHighTripPeriods.size() == 0) {
            return "";
        }

        return converter.convertDurationToString(Duration.ofSeconds(totalTime / this.baHighTripPeriods.size()));
    }

}

package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MisconductTripPeriod extends IntervalTripPeriod {

    private String dateStamp;
    private short baseNumber;
    private String routeNumber;
    private short exodusNumber;
    private String busNumber;

    private short driverNumber;
    private String driverName;
    private LocalDateTime previousTripPeriodArrvialTimeActual;
    private LocalDateTime previousBusStartTimeActual;

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public short getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(short driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public short getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(short baseNumber) {
        this.baseNumber = baseNumber;
    }

    public short getExodusNumber() {
        return exodusNumber;
    }

    public void setExodusNumber(short exodusNumber) {
        this.exodusNumber = exodusNumber;
    }

    public LocalDateTime getPreviousTripPeriodArrvialTimeActual() {
        return previousTripPeriodArrvialTimeActual;
    }

    public String getPreviousTripPeriodArrvialTimeActualString() {
        if (previousTripPeriodArrvialTimeActual == null) {
            return "";
        }
        return previousTripPeriodArrvialTimeActual.format(DateTimeFormatter.ofPattern("HH:mm"));

    }

    public void setPreviousTripPeriodArrvialTimeActual(LocalDateTime previousTripPeriodArrvialTimeActual) {
        this.previousTripPeriodArrvialTimeActual = previousTripPeriodArrvialTimeActual;
    }

    public LocalDateTime getPreviousBusStartTimeActual() {
        return previousBusStartTimeActual;
    }

    public String getPreviousBusStartTimeActualString() {
        if (previousBusStartTimeActual == null) {
            return "";
        }
        return previousBusStartTimeActual.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void setPreviousBusStartTimeActual(LocalDateTime previousBusStartTimeActual) {
        this.previousBusStartTimeActual = previousBusStartTimeActual;
    }

}

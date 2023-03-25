package Model;

import java.time.Duration;
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
        return previousTripPeriodArrvialTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

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
        return previousBusStartTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setPreviousBusStartTimeActual(LocalDateTime previousBusStartTimeActual) {
        this.previousBusStartTimeActual = previousBusStartTimeActual;
    }

    //-----------------------------------
    public LocalDateTime getShouldStartTime() {
        if (this.getPreviousBusStartTimeActual() == null) {
            System.out.println("PREVIOUS NULL"+this.getRouteNumber() + ":" + this.getDateStamp() + ":" + super.getStartTimeScheduledString());
            return null;
        }
        if (this.getScheduledInterval() == null) {
            System.out.println("THIS NULL"+this.getRouteNumber() + ":" + this.getDateStamp() + ":" + super.getStartTimeScheduledString());
            return null;
        }
        LocalDateTime shouldStartTime = this.getPreviousBusStartTimeActual().plus(this.getScheduledInterval());
        return shouldStartTime;
    }

    public String getShouldStartTimeString() {

        if (getShouldStartTime() == null) {
            return "";

        }
        return getShouldStartTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public LocalDateTime getCouldStartTime() {
        if (getPreviousTripPeriodArrvialTimeActual() != null) {
            return this.getPreviousTripPeriodArrvialTimeActual().plus(this.getHaltTimeScheduled());
        } else {
            return null;
        }
    }

    public String getCouldStartTimeString() {

        if (getCouldStartTime() == null) {
            return "";

        }
        return getCouldStartTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getMisconductTimeDurationString() {
        LocalDateTime startTimeActual = this.getStartTimeActual();
        LocalDateTime couldStartTime = this.getCouldStartTime();
        LocalDateTime shouldStartTime = this.getShouldStartTime();
        if (startTimeActual != null && couldStartTime != null && shouldStartTime != null) {
            if (super.getStartTimeScheduled().isAfter(couldStartTime)) {
                couldStartTime = super.getStartTimeScheduled();
            }

            if (shouldStartTime.isAfter(couldStartTime)) {

                return converter.convertDurationToString(Duration.between(shouldStartTime, startTimeActual));

            }
            return converter.convertDurationToString(Duration.between(couldStartTime, startTimeActual));
        } else {
            return "";
        }
    }

}

package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FirstTripPeriod extends TripPeriod {

    private LocalDateTime baseTripStartTimeScheduled;
    private LocalDateTime baseTripStartTimeActual;
    private String baseTripStartTimeDifference;
    private boolean brokenExodus;

    private String routeNumber;
    private String dateStamp;
    private short baseNumber;
    private String busNumber;
    private short exodusNumber;

    public LocalDateTime getBaseTripStartTimeScheduled() {
        return baseTripStartTimeScheduled;
    }

    public void setBaseTripStartTimeScheduled(LocalDateTime baseTripStartTimeScheduled) {
        this.baseTripStartTimeScheduled = baseTripStartTimeScheduled;
    }

    public LocalDateTime getBaseTripStartTimeActual() {
        return baseTripStartTimeActual;
    }

    public void setBaseTripStartTimeActual(LocalDateTime baseTripStartTimeActual) {
        this.baseTripStartTimeActual = baseTripStartTimeActual;
    }

    public String getBaseTripStartTimeDifference() {
        return baseTripStartTimeDifference;
    }

    public void setBaseTripStartTimeDifference(String baseTripStartTimeDifference) {
        this.baseTripStartTimeDifference = baseTripStartTimeDifference;
    }

    public String getBaseTripStartTimeScheduledString() {
        return baseTripStartTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getBaseTripStartTimeActualString() {
        if (baseTripStartTimeActual == null) {
            return "";
        }
        return baseTripStartTimeActual.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public short getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(short baseNumber) {
        this.baseNumber = baseNumber;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public short getExodusNumber() {
        return exodusNumber;
    }

    public void setExodusNumber(short exodusNumber) {
        this.exodusNumber = exodusNumber;
    }

    public boolean isBrokenExodus() {
        return brokenExodus;
    }

    public void setBrokenExodus(boolean brokenExodus) {
        this.brokenExodus = brokenExodus;
    }

    public String getColor() {
        if (this.isBrokenExodus()) {
            return "#bf00ff";
        }
        return "inherited";
    }

}

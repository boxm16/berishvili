package Model;

import java.time.Duration;

public class IntervalTripPeriod extends DetailedTripPeriod {

    private short exodusNumber;
    private Duration scheduledInterval;
    private Duration actualInterval;

    public short getExodusNumber() {
        return exodusNumber;
    }

    public void setExodusNumber(short exodusNumber) {
        this.exodusNumber = exodusNumber;
    }

    //---------intervals setters getters
    public Duration getScheduledInterval() {
        return scheduledInterval;
    }

    public void setScheduledInterval(Duration scheduledInterval) {
        this.scheduledInterval = scheduledInterval;
    }

    public Duration getActualInterval() {
        return actualInterval;
    }

    public void setActualInterval(Duration actualInterval) {
        this.actualInterval = actualInterval;
    }
//----------strings------------

    public String getScheduledIntervalString() {
        return converter.convertDurationToString(scheduledInterval);
    }

    public String getActualIntervalString() {
        return converter.convertDurationToString(actualInterval);
    }
    //-------colors-------

    public String getActualIntervalColor() {
        if (scheduledInterval == null || actualInterval == null) {
            return "inherited";
        }
        return converter.convertDurationToThreeColors(scheduledInterval.minus(actualInterval));
    }

    public String getGpsIntervalColor() {
        if (scheduledInterval == null || getGpsInterval() == null) {
            return "inherited";
        }
        return converter.convertDurationToThreeColors(scheduledInterval.minus(getGpsInterval()));
    }
}

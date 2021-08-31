package Model;

import java.time.Duration;

public class IntervalTripPeriod extends DetailedTripPeriod {

    private short scheduledTimetableSequenceNumber;
    private short exodusNumber;
    private Duration scheduledInterval;
    private Duration actualInterval;
    private String misconduct;

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

    public String getMisconductColor() {

        if (this.scheduledInterval != null && this.getGpsInterval() != null && !this.getLostTimeString().equals("")) {

            Duration intervalDifference = this.getGpsInterval().minus(this.scheduledInterval);
            Duration lostTimeDuration = converter.convertStringToDuration(this.getLostTimeString());

            if (intervalDifference.getSeconds() < -301 && lostTimeDuration.getSeconds() < -301) {
                misconduct = "-";
                return "green";
            } else if (intervalDifference.getSeconds() > 300 && lostTimeDuration.getSeconds() > 300) {
                misconduct = "+";
                return "green";

            } else {
                misconduct = "";
                return "";

            }
        } else {
            misconduct = "";
            return "";
        }
    }

    public String getMisconduct() {
        return misconduct;
    }

    public void setMisconduct(String misconduct) {
        this.misconduct = misconduct;
    }

    public short getScheduledTimetableSequenceNumber() {
        return scheduledTimetableSequenceNumber;
    }

    public void setScheduledTimetableSequenceNumber(short scheduledTimetableSequenceNumber) {
        this.scheduledTimetableSequenceNumber = scheduledTimetableSequenceNumber;
    }
    

}

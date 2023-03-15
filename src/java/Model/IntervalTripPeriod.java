package Model;

import java.time.Duration;
import java.time.LocalDateTime;

public class IntervalTripPeriod extends DetailedTripPeriod {

    private String busNumber;
    private String driverName;
    private short scheduledTimetableSequenceNumber;
    private short exodusNumber;
    private short baseNumber;
    private Duration scheduledInterval;
    private Duration actualInterval;
    private String misconduct;
    private String misconductDuration;
    private Duration misconductDurationDuration;
    private String runOver;
    private boolean spacialCase;
    private LocalDateTime fakeStartTimeActual;
    
    

    public IntervalTripPeriod() {
        runOver = "";
    }

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
        if (misconduct == "-") {
            return "lightgreen";
        }
        if (misconduct == "+") {
            return "lightgreen";
        }
        misconduct = "";
        return "";

    }

    public String getMisconduct() {
        return misconduct;
    }

    public void calculateMisconduct() {

        if (this.scheduledInterval != null && this.getGpsInterval() != null && !this.getLostTimeString().equals("")) {

            Duration intervalDifference = this.getGpsInterval().minus(this.scheduledInterval);
            Duration lostTimeDuration = converter.convertStringToDuration(this.getLostTimeString());

            if (intervalDifference.getSeconds() < -301 && lostTimeDuration.getSeconds() < -61) {
                misconduct = "-";
                misconductDuration = converter.convertDurationToString(lostTimeDuration);
                misconductDurationDuration = lostTimeDuration;
            } else if (intervalDifference.getSeconds() > 300 && lostTimeDuration.getSeconds() > 60) {
                misconduct = "+";
                misconductDuration = converter.convertDurationToString(lostTimeDuration);
                misconductDurationDuration = lostTimeDuration;
               
                  
            } else {
                misconduct = "";
            }
        } else {
            misconduct = "";

        }
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

    public String getRunOver() {
        return runOver;
    }

    public void setRunOver(String runOver) {
        this.runOver = runOver;
    }

    public void addRunOver(String runOver) {
        this.runOver += runOver;
    }

    public String getRunOverColor() {
        if (runOver.equals("")) {
            return "inherited";
        } else {
            return "lightgreen";
        }
    }

    public String getMisconductDuration() {
        return misconductDuration;
    }

    public Double getMisconductDurationExcelFormat() {
        if (misconductDurationDuration == null) {
            return null;
        } else {
            double ss = misconductDurationDuration.getSeconds();
            return ss / 86400;
        }

    }

    public void setMisconductDuration(String misconductDuration) {
        this.misconductDuration = misconductDuration;
    }

    public short getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(short baseNumber) {
        this.baseNumber = baseNumber;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public boolean isSpacialCase() {
        return spacialCase;
    }

    public void setSpacialCase(boolean spacialCase) {
        this.spacialCase = spacialCase;
    }

    public LocalDateTime getFakeStartTimeActual() {
        return fakeStartTimeActual;
    }

    public void setFakeStartTimeActual(LocalDateTime fakeStartTimeActual) {
        this.fakeStartTimeActual = fakeStartTimeActual;
        
       
    }

}

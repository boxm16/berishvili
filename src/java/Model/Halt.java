package Model;

import Controller.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Michail Sitmalidis
 */
public class Halt {

    private String point;
    private LocalDateTime startTime;
    private short exodusNumber;
    private LocalDateTime tripPeriodStartTimeScheduled;
    private LocalDateTime fakeStartTime;
    private LocalDateTime endTime;
    protected Converter converter;

    public Halt() {
        this.converter = new Converter();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFakeStartTime() {
        return fakeStartTime;
    }

    public void setFakeStartTime(LocalDateTime fakeStartTime) {
        this.fakeStartTime = fakeStartTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getStartTimeString() {
        return this.startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getEndTimeString() {
        return this.endTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public LocalDateTime getTripPeriodStartTimeScheduled() {
        return tripPeriodStartTimeScheduled;
    }

    public void setTripPeriodStartTimeScheduled(LocalDateTime tripPeriodStartTimeScheduled) {
        this.tripPeriodStartTimeScheduled = tripPeriodStartTimeScheduled;
    }

    public short getExodusNumber() {
        return exodusNumber;
    }

    public void setExodusNumber(short exodusNumber) {
        this.exodusNumber = exodusNumber;
    }
    
    

}

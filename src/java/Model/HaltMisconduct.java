package Model;

import Controller.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HaltMisconduct {

    private String routeNumber;
    private String dateStamp;
    private LocalDateTime startTime;
    private ArrayList<Halt> participantHalts;
    private String point;
    private Converter converter;

    public HaltMisconduct() {
        this.converter = new Converter();
    }

    public void setParticipantHalts(ArrayList<Halt> participantHalts) {
        this.participantHalts = participantHalts;
    }

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeString() {
        return this.startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public ArrayList<Halt> getParticipantHalts() {
        return participantHalts;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
    
}

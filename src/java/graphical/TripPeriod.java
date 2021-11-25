package graphical;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TripPeriod {

    LocalDateTime startTime;
    Duration duration;
    double length;
    String color;
    String type;

    public TripPeriod() {
    }

    public TripPeriod(LocalDateTime startTime, Duration duration, String type) {
        this.startTime = startTime;
        this.duration = duration;
        this.type = type;
    }

    public double getStartPoint() {

        long a = startTime.toEpochSecond(ZoneOffset.UTC);

        //  System.out.println(a);
        return (double) a / 60 - 270;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLength() {
        //System.out.println(duration.getSeconds() / 60);
        length = (double) duration.getSeconds() / 60;
        return length;

    }

    public String getColor() {
        switch (this.type) {
            case "ab":
                return "blue";
            case "ba":
                return "green";
            case "halt":
                return "black";
            case "break":
                return "yellow";
            default:
                return "white";
        }

    }

}

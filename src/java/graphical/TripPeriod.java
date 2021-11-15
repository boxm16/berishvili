package graphical;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TripPeriod {

    LocalDateTime startTime;
    Duration duration;
    long length;
    String color;
    String type;

    public TripPeriod() {
    }

    public TripPeriod(LocalDateTime startTime, Duration duration, String type) {
        this.startTime = startTime;
        this.duration = duration;
        this.type = type;
    }

    public long getStartPoint() {

        long a = startTime.toEpochSecond(ZoneOffset.UTC);

        System.out.println(a);
        return a / 60 - 270;
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

    public long getLength() {

        return duration.getSeconds() / 60;

    }

    public String getColor() {
        switch (this.type) {
            case "ab":
                return "blue";
            case "ba":
                return "green";
            case "halt":
                return "black";
            default:
                return "white";
        }

    }

}

package Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GuarantyTripPeriod {

    private String type;
    private LocalDateTime startTimeScheduled;
    private LocalDateTime arrivalTimeScheduled;
    private Duration tripPeriodTime;

    public GuarantyTripPeriod(String type, LocalDateTime startTimeScheduled, LocalDateTime arrivalTimeScheduled) {
        this.type = type;
        this.startTimeScheduled = startTimeScheduled;
        this.arrivalTimeScheduled = arrivalTimeScheduled;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartTimeScheduled(LocalDateTime startTimeScheduled) {
        this.startTimeScheduled = startTimeScheduled;
    }

    public void setArrivalTimeScheduled(LocalDateTime arrivalTimeScheduled) {
        this.arrivalTimeScheduled = arrivalTimeScheduled;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getStartTimeScheduled() {
        return startTimeScheduled;
    }

    public LocalDateTime getArrivalTimeScheduled() {
        return arrivalTimeScheduled;
    }

    public String getStartTimeScheduledString() {
        return startTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getArrivalTimeScheduledString() {
        return arrivalTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public Duration getTripPeriodTime() {
        return tripPeriodTime;
    }

    public void setTripPeriodTime(Duration tripPeriodTime) {
        this.tripPeriodTime = tripPeriodTime;
    }
    

}

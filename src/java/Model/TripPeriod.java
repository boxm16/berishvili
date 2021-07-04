package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TripPeriod {

    private String type;
    private LocalDateTime startTimeScheduled;
    private LocalDateTime startTimeActual;
    private String startTimeDifference;
    private LocalDateTime arrivalTimeScheduled;
    private LocalDateTime arrivalTimeActual;
    private String arrivalTimeDifference;

    public TripPeriod() {
    }

    public TripPeriod(String type, LocalDateTime startTimeScheduled, LocalDateTime startTimeActual, String startTimeDifference, LocalDateTime arrivalTimeScheduled, LocalDateTime arrivalTimeActual, String arrivalTimeDifference) {
        this.type = type;
        this.startTimeScheduled = startTimeScheduled;
        this.startTimeActual = startTimeActual;
        this.startTimeDifference = startTimeDifference;
        this.arrivalTimeScheduled = arrivalTimeScheduled;
        this.arrivalTimeActual = arrivalTimeActual;
        this.arrivalTimeDifference = arrivalTimeDifference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartTimeScheduled() {
        return startTimeScheduled;
    }

    public void setStartTimeScheduled(LocalDateTime startTimeScheduled) {
        this.startTimeScheduled = startTimeScheduled;
    }

    public LocalDateTime getStartTimeActual() {
        return startTimeActual;
    }

    public void setStartTimeActual(LocalDateTime startTimeActual) {
        this.startTimeActual = startTimeActual;
    }

    public String getStartTimeDifference() {
        return startTimeDifference;
    }

    public void setStartTimeDifference(String startTimeDifference) {
        this.startTimeDifference = startTimeDifference;
    }

    public LocalDateTime getArrivalTimeScheduled() {
        return arrivalTimeScheduled;
    }

    public void setArrivalTimeScheduled(LocalDateTime arrivalTimeScheduled) {
        this.arrivalTimeScheduled = arrivalTimeScheduled;
    }

    public LocalDateTime getArrivalTimeActual() {
        return arrivalTimeActual;
    }

    public void setArrivalTimeActual(LocalDateTime arrivalTimeActual) {
        this.arrivalTimeActual = arrivalTimeActual;
    }

    public String getArrivalTimeDifference() {
        return arrivalTimeDifference;
    }

    public void setArrivalTimeDifference(String arrivalTimeDifference) {
        this.arrivalTimeDifference = arrivalTimeDifference;
    }

    public String getStartTimeScheduledString() {
        return startTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getArrivalTimeScheduledString() {
        return arrivalTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getArrivalTimeActualString() {
        if (arrivalTimeActual == null) {
            return "";
        }
        return arrivalTimeActual.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getStartTimeActualString() {
        if (startTimeActual == null) {
            return "";
        }
        return startTimeActual.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

}

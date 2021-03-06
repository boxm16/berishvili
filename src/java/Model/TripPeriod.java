package Model;

import Controller.Converter;
import java.time.Duration;
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
    protected Converter converter;

    public TripPeriod() {
        converter = new Converter();
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

    public String getTypeG() {
        switch (type) {
            case "baseLeaving_A":
                return "ბაზა_A";
            case "baseLeaving_B":
                return "ბაზა_B";
            case "break":
                return "შესვენება";
            case "ab":
                return "A_B";
            case "ba":
                return "B_A";
            case "A_baseReturn":
                return "A_ბაზა";
            case "B_baseReturn":
                return "B_ბაზა";
            default:
                return "";
        }
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
        return startTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getArrivalTimeScheduledString() {
        return arrivalTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getArrivalTimeActualString() {
        if (arrivalTimeActual == null) {
            return "";
        }
        return arrivalTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getStartTimeActualString() {
        if (startTimeActual == null) {
            return "";
        }
        return startTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public Duration getTripPeriodTimeScheduled() {
        return Duration.between(startTimeScheduled, arrivalTimeScheduled);
    }

    public String getTripPeriodTimeScheduledString() {

        return converter.convertDurationToString(Duration.between(startTimeScheduled, arrivalTimeScheduled));
    }

    public String getTripPeriodTimeActualString() {
        if (startTimeActual == null || arrivalTimeActual == null) {
            return "";
        }

        return converter.convertDurationToString(Duration.between(startTimeActual, arrivalTimeActual));
    }

    public Duration getTripPeriodTimeActual() {
        if (startTimeActual == null || arrivalTimeActual == null) {
            return null;
        }

        return Duration.between(startTimeActual, arrivalTimeActual);
    }

    public String getTripPeriodTimeDifferenceString() {
        if (startTimeActual == null || arrivalTimeActual == null) {
            return "";
        }

        Duration difference = Duration.between(startTimeScheduled, arrivalTimeScheduled).minus(Duration.between(startTimeActual, arrivalTimeActual));

        return converter.convertDurationToString(difference);
    }

    public Duration getTripPeriodTimeDifference() {
        if (startTimeActual == null || arrivalTimeActual == null) {
            return null;
        }

        return Duration.between(startTimeScheduled, arrivalTimeScheduled).minus(Duration.between(startTimeActual, arrivalTimeActual));

    }

    public String getTripPeriodTimeDifferenceColor() {
        if (startTimeActual == null || arrivalTimeActual == null) {
            return "white";
        }
        Duration difference = Duration.between(startTimeScheduled, arrivalTimeScheduled).minus(Duration.between(startTimeActual, arrivalTimeActual));
        long seconds = difference.getSeconds();
        if (Math.abs(seconds) > (5 * 60)) {
            return "red";
        }
        if (Math.abs(seconds) > (1 * 60)) {
            return "yellow";
        }
        return "white";

    }

    public String getStartTimeDifferenceColor() {
        return converter.convertStringDurationToThreeColors(startTimeDifference);
    }

    public String getArrivalTimeDifferenceColor() {
        return converter.convertStringDurationToThreeColors(arrivalTimeDifference);
    }

    public Double getStartTimeScheduledExcelFormat() {
        if (getStartTimeScheduled() == null) {
            return null;
        }
        long h = getStartTimeScheduled().getHour();
        long m = getStartTimeScheduled().getMinute();
        long s = getStartTimeScheduled().getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;
    }

    public Double getStartTimeActualExcelFormat() {
        if (getStartTimeActual() == null) {
            return null;
        }
        long h = getStartTimeActual().getHour();
        long m = getStartTimeActual().getMinute();
        long s = getStartTimeActual().getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;
    }

    public Double getArrivaltTimeScheduledExcelFormat() {
        if (getArrivalTimeScheduled() == null) {
            return null;
        }
        long h = getArrivalTimeScheduled().getHour();
        long m = getArrivalTimeScheduled().getMinute();
        long s = getArrivalTimeScheduled().getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;
    }

    public Double getArrivalTimeActualExcelFormat() {
        if (getArrivalTimeActual() == null) {
            return null;
        }
        long h = getArrivalTimeActual().getHour();
        long m = getArrivalTimeActual().getMinute();
        long s = getArrivalTimeActual().getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;
    }

    public Double getTripPeriodTimeScheduledExcelFormat() {
        if (getTripPeriodTimeScheduled() == null) {
            return null;
        }
        double ss = getTripPeriodTimeScheduled().getSeconds();
        return ss / 86400;
    }

    public Double getTripPeriodTimeActualExcelFormat() {
        if (getTripPeriodTimeActual() == null) {
            return null;
        }
        double ss = getTripPeriodTimeActual().getSeconds();
        return ss / 86400;
    }

    public Double getTripPeriodTimeDifferenceExcelFormat() {
        if (getTripPeriodTimeDifference() == null) {
            return null;
        }
        double ss = getTripPeriodTimeDifference().getSeconds();
        return ss / 86400;
    }

}

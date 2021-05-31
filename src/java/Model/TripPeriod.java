package Model;

public class TripPeriod {

    private String type;
    private String startTimeScheduled;
    private String startTimeActual;
    private String startTimeDifference;
    private String arrivalTimeScheduled;
    private String arrivalTimeActual;
    private String arrivalTimeDifference;

    public TripPeriod(String type, String startTimeScheduled, String startTimeActual, String startTimeDifference, String arrivalTimeScheduled, String arrivalTimeActual, String arrivalTimeDifference) {
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

    public String getStartTimeScheduled() {
        return startTimeScheduled;
    }

    public void setStartTimeScheduled(String startTimeScheduled) {
        this.startTimeScheduled = startTimeScheduled;
    }

    public String getStartTimeActual() {
        return startTimeActual;
    }

    public void setStartTimeActual(String startTimeActual) {
        this.startTimeActual = startTimeActual;
    }

    public String getStartTimeDifference() {
        return startTimeDifference;
    }

    public void setStartTimeDifference(String startTimeDifference) {
        this.startTimeDifference = startTimeDifference;
    }

    public String getArrivalTimeScheduled() {
        return arrivalTimeScheduled;
    }

    public void setArrivalTimeScheduled(String arrivalTimeScheduled) {
        this.arrivalTimeScheduled = arrivalTimeScheduled;
    }

    public String getArrivalTimeActual() {
        return arrivalTimeActual;
    }

    public void setArrivalTimeActual(String arrivalTimeActual) {
        this.arrivalTimeActual = arrivalTimeActual;
    }

    public String getArrivalTimeDifference() {
        return arrivalTimeDifference;
    }

    public void setArrivalTimeDifference(String arrivalTimeDifference) {
        this.arrivalTimeDifference = arrivalTimeDifference;
    }

}

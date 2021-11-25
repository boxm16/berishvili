package graphical;

public class RouteData {

    private String roundCheckBoxChecked;
    private String roundInputHourValue;
    private String roundInputMinuteValue;
    private String roundInputSecondValue;
    private String roundInputMinutesValue;
    private String roundInputSecondsValue;
    private String busCheckBoxChecked;
    private String busInputValue;
    private String intervalCheckBoxChecked;
    private String intervalInputHourValue;
    private String intervalInputMinuteValue;
    private String intervalInputSecondValue;
    private String plusMinusInputValue;

    private String starterTrip;
    private String firstTripStartTime;
    private String lastTripStartTime;
    private int abTripTimeMinutes;
    private int abTripTimeSeconds;
    private int baTripTimeMinutes;
    private int baTripTimeSeconds;
    private String roundTripTime;

    private int haltTimeMinutes;
    private int haltTimeSeconds;

    private int abBusCount;
    private int baBusCount;
    private int busCount;
    private String intervalTime;
    private boolean circularRoute;

    private int breakTimeMinutes;
    private String firstBreakStartTime;
    private String lastBreakEndTime;
    private String breakStayPoint;

    public RouteData() {
        roundCheckBoxChecked = "";
        roundInputHourValue = "00";
        roundInputMinuteValue = "00";
        roundInputSecondValue = "00";
        roundInputMinutesValue = "00";
        roundInputSecondsValue = "00";
        busCheckBoxChecked = "";
        busInputValue = "0";
        intervalCheckBoxChecked = "";
        intervalInputHourValue = "00";
        intervalInputMinuteValue = "00";
        intervalInputSecondValue = "00";
        plusMinusInputValue = "2";

        starterTrip = "ab";
        firstTripStartTime = "08:00:00";
        lastTripStartTime = "21:00:00";
        haltTimeMinutes = 5;
        haltTimeSeconds = 0;
        abTripTimeMinutes = 55;
        abTripTimeSeconds = 00;
        baTripTimeMinutes = 55;
        baTripTimeSeconds = 00;
        roundTripTime = "120:00";
        abBusCount = 4;
        baBusCount = 4;
        busCount = 8;
        intervalTime = "00:15:00";

        circularRoute = false;

    }

    public String getRoundCheckBoxChecked() {
        return roundCheckBoxChecked;
    }

    public void setRoundCheckBoxChecked(String roundCheckBoxChecked) {
        this.roundCheckBoxChecked = roundCheckBoxChecked;
    }

    public String getRoundInputHourValue() {
        return roundInputHourValue;
    }

    public void setRoundInputHourValue(String roundInputHourValue) {
        this.roundInputHourValue = roundInputHourValue;
    }

    public String getRoundInputMinuteValue() {
        return roundInputMinuteValue;
    }

    public void setRoundInputMinuteValue(String roundInputMinuteValue) {
        this.roundInputMinuteValue = roundInputMinuteValue;
    }

    public String getRoundInputSecondValue() {
        return roundInputSecondValue;
    }

    public void setRoundInputSecondValue(String roundInputSecondValue) {
        this.roundInputSecondValue = roundInputSecondValue;
    }

    public String getRoundInputMinutesValue() {
        return roundInputMinutesValue;
    }

    public void setRoundInputMinutesValue(String roundInputMinutesValue) {
        this.roundInputMinutesValue = roundInputMinutesValue;
    }

    public String getRoundInputSecondsValue() {
        return roundInputSecondsValue;
    }

    public void setRoundInputSecondsValue(String roundInputSecondsValue) {
        this.roundInputSecondsValue = roundInputSecondsValue;
    }

    public String getBusCheckBoxChecked() {
        return busCheckBoxChecked;
    }

    public void setBusCheckBoxChecked(String busCheckBoxChecked) {
        this.busCheckBoxChecked = busCheckBoxChecked;
    }

    public String getBusInputValue() {
        return busInputValue;
    }

    public void setBusInputValue(String busInputValue) {
        this.busInputValue = busInputValue;
    }

    public String getIntervalCheckBoxChecked() {
        return intervalCheckBoxChecked;
    }

    public void setIntervalCheckBoxChecked(String intervalCheckBoxChecked) {
        this.intervalCheckBoxChecked = intervalCheckBoxChecked;
    }

    public String getIntervalInputHourValue() {
        return intervalInputHourValue;
    }

    public void setIntervalInputHourValue(String intervalInputHourValue) {
        this.intervalInputHourValue = intervalInputHourValue;
    }

    public String getIntervalInputMinuteValue() {
        return intervalInputMinuteValue;
    }

    public void setIntervalInputMinuteValue(String intervalInputMinuteValue) {
        this.intervalInputMinuteValue = intervalInputMinuteValue;
    }

    public String getIntervalInputSecondValue() {
        return intervalInputSecondValue;
    }

    public void setIntervalInputSecondValue(String intervalInputSecondValue) {
        this.intervalInputSecondValue = intervalInputSecondValue;
    }

    public String getPlusMinusInputValue() {
        return plusMinusInputValue;
    }

    public void setPlusMinusInputValue(String plusMinusInputValue) {
        this.plusMinusInputValue = plusMinusInputValue;
    }

    public String getStarterTrip() {
        return starterTrip;
    }

    public void setStarterTrip(String starterTrip) {
        this.starterTrip = starterTrip;
    }

    public String getFirstTripStartTime() {
        return firstTripStartTime;
    }

    public void setFirstTripStartTime(String firstTripStartTime) {
        this.firstTripStartTime = firstTripStartTime;
    }

    public String getLastTripStartTime() {
        return lastTripStartTime;
    }

    public void setLastTripStartTime(String lastTripStartTime) {
        this.lastTripStartTime = lastTripStartTime;
    }

    public int getAbTripTimeMinutes() {
        return abTripTimeMinutes;
    }

    public void setAbTripTimeMinutes(int abTripTimeMinutes) {
        this.abTripTimeMinutes = abTripTimeMinutes;
    }

    public int getAbTripTimeSeconds() {
        return abTripTimeSeconds;
    }

    public void setAbTripTimeSeconds(int abTripTimeSeconds) {
        this.abTripTimeSeconds = abTripTimeSeconds;
    }

    public int getBaTripTimeMinutes() {
        return baTripTimeMinutes;
    }

    public void setBaTripTimeMinutes(int baTripTimeMinutes) {
        this.baTripTimeMinutes = baTripTimeMinutes;
    }

    public int getBaTripTimeSeconds() {
        return baTripTimeSeconds;
    }

    public void setBaTripTimeSeconds(int baTripTimeSeconds) {
        this.baTripTimeSeconds = baTripTimeSeconds;
    }

    public String getRoundTripTime() {
        int tripTimeInSeconds = (abTripTimeMinutes + baTripTimeMinutes) * 60 + abTripTimeSeconds + baTripTimeSeconds + (haltTimeMinutes * 60 + haltTimeSeconds) * 2;
        int minutes = tripTimeInSeconds / 60;
        int seconds = tripTimeInSeconds % 60;
        String minutesString;
        String secondsString;
        if (minutes < 10) {
            minutesString = "0" + String.valueOf(minutes);
        } else {
            minutesString = String.valueOf(minutes);
        }
        if (seconds < 10) {
            secondsString = "0" + String.valueOf(seconds);
        } else {
            secondsString = String.valueOf(seconds);
        }
        return minutesString + ":" + secondsString;

    }

    public int getAbBusCount() {
        return abBusCount;
    }

    public void setAbBusCount(int abBusCount) {
        this.abBusCount = abBusCount;
    }

    public int getBaBusCount() {
        return baBusCount;
    }

    public void setBaBusCount(int baBusCount) {
        this.baBusCount = baBusCount;
    }

    public int getBusCount() {
        return busCount;
    }

    public void setBusCount(int busCount) {
        this.busCount = busCount;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public boolean isCircularRoute() {
        return circularRoute;
    }

    public void setCircularRoute(boolean circularRoute) {
        this.circularRoute = circularRoute;
    }

    public int getHaltTimeMinutes() {
        return haltTimeMinutes;
    }

    public void setHaltTimeMinutes(int haltTimeMinutes) {
        this.haltTimeMinutes = haltTimeMinutes;
    }

    public int getHaltTimeSeconds() {

        return haltTimeSeconds;
    }

    public void setHaltTimeSeconds(int haltTimeSeconds) {
        this.haltTimeSeconds = haltTimeSeconds;
    }

    public int getBreakTimeMinutes() {
        return breakTimeMinutes;
    }

    public void setBreakTimeMinutes(int breakTimeMinutes) {
        this.breakTimeMinutes = breakTimeMinutes;
    }

    public String getFirstBreakStartTime() {
        return firstBreakStartTime;
    }

    public void setFirstBreakStartTime(String firstBreakStartTime) {
        this.firstBreakStartTime = firstBreakStartTime;
    }

    public String getLastBreakEndTime() {
        return lastBreakEndTime;
    }

    public void setLastBreakEndTime(String lastBreakEndTime) {
        this.lastBreakEndTime = lastBreakEndTime;
    }

    public String getBreakStayPoint() {
        return breakStayPoint;
    }

    public void setBreakStayPoint(String breakStayPoint) {
        this.breakStayPoint = breakStayPoint;
    }
    
    

}

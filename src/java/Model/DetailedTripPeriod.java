package Model;

import java.time.Duration;

public class DetailedTripPeriod extends TripPeriod {

    private Duration haltTimeScheduled;
    private Duration haltTimeActual;
    private String lostTimeString;

    public Duration getHaltTimeScheduled() {
        return haltTimeScheduled;
    }

    public void setHaltTimeScheduled(Duration haltTimeScheduled) {
        this.haltTimeScheduled = haltTimeScheduled;
    }

    public Duration getHaltTimeActual() {
        return haltTimeActual;
    }

    public void setHaltTimeActual(Duration haltTimeActual) {
        this.haltTimeActual = haltTimeActual;
    }

    public String getHaltTimeScheduledString() {
        if (haltTimeScheduled == null) {
            return "";
        }
        return converter.convertDurationToString(haltTimeScheduled);
    }

    public String getHaltTimeActualString() {
        if (haltTimeActual == null) {
            return "";
        }
        return converter.convertDurationToString(haltTimeActual);
    }

    public void calculateLostTime() {
        Duration startTimeDifferenceDuration = converter.convertStringToDuration(super.getStartTimeDifference());
        if (startTimeDifferenceDuration != null) {

            if (startTimeDifferenceDuration.getSeconds() <= 0) {//if a driver starts earlier than he should start
                // $lostTime = $timeController->getTimeStampFromSeconds($lostTimeInSeconds);
                lostTimeString = super.getStartTimeDifference();

            } else {
                //dialdi, if a driver starts later then he shoud start, while he could start (has enough halt time) 
                if (this.getHaltTimeActual() == null) {
                    lostTimeString = "";

                } else {
                    if (startTimeDifferenceDuration.getSeconds() >= this.getHaltTimeActual().getSeconds()) {
                        lostTimeString = getHaltTimeActualString();

                    } else {
                        lostTimeString = super.getStartTimeDifference();

                    }
                }
            }
        } else {
            lostTimeString = "";
        }
    }

    public String getLostTimeString() {
        return lostTimeString;
    }

    public void setLostTimeString(String lostTimeString) {
        this.lostTimeString = lostTimeString;
    }

    
    
    public String getLostTimeColor() {
        if (lostTimeString.isEmpty()) {
            System.out.println("-------------");
        }
        return converter.convertStringDurationToThreeColors(lostTimeString);
    }
}

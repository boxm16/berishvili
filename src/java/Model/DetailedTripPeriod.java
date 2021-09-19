package Model;

import java.time.Duration;

public class DetailedTripPeriod extends TripPeriod {

    private Duration haltTimeScheduled;
    private Duration haltTimeActual;
    private String lostTimeString;//stupid that you left time as String and not duration

    private Duration gpsInterval;

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
                //dialdi, if a driver starts later than he shoud start, while he could start (has enough halt time) 
                if (this.getHaltTimeActual() == null) {
                    lostTimeString = "";

                } else {
                    if (startTimeDifferenceDuration.getSeconds() >= this.getHaltTimeActual().getSeconds() - this.getHaltTimeScheduled().getSeconds()) {
                        lostTimeString = converter.convertDurationToString(getHaltTimeActual().minus(getHaltTimeScheduled()));

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
        return converter.convertStringDurationToThreeColors(lostTimeString);
    }

    public Duration getGpsInterval() {
        return gpsInterval;
    }

    public void setGpsInterval(Duration gpsInterval) {
        this.gpsInterval = gpsInterval;
    }

    public String getGpsIntervalString() {
        return converter.convertDurationToString(gpsInterval);
    }

    public Double getHaltTimeScheduledExcelFormat() {
        if (getHaltTimeScheduled() == null) {
            return null;
        } else {
            double ss = getHaltTimeScheduled().getSeconds();
            return ss / 86400;
        }
    }

    public Double getHaltTimeActualExcelFormat() {
        if (getHaltTimeActual() == null) {
            return null;
        } else {
            double ss = getHaltTimeActual().getSeconds();
            return ss / 86400;
        }
    }

    public Double getLostTimeExcelFormat() {
        Duration lostTimeDuration = converter.convertStringToDuration(lostTimeString);
        if (lostTimeDuration == null) {
            return null;
        } else {
            double ss = lostTimeDuration.getSeconds();
            return ss / 86400;
        }
    }

    public Double getGpsIntervalExcelFormat() {
        if (getGpsInterval() == null) {
            return null;
        } else {
            double ss = getGpsInterval().getSeconds();
            return ss / 86400;
        }
    }

}

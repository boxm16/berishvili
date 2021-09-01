package Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

public class IntervalDay extends DetailedDay {

    private TreeMap<LocalDateTime, IntervalTripPeriod> abTimetable;
    private TreeMap<LocalDateTime, IntervalTripPeriod> baTimetable;

    public IntervalDay() {
        abTimetable = new TreeMap();
        baTimetable = new TreeMap();

    }

    public TreeMap<LocalDateTime, IntervalTripPeriod> getAbTimetable() {
        return abTimetable;
    }

    public void setAbTimetable(TreeMap<LocalDateTime, IntervalTripPeriod> abTimetable) {

        this.abTimetable = abTimetable;
    }

    public TreeMap<LocalDateTime, IntervalTripPeriod> getBaTimetable() {
        return baTimetable;
    }

    public void setBaTimetable(TreeMap<LocalDateTime, IntervalTripPeriod> baTimetable) {
        this.baTimetable = baTimetable;
    }

    //this method sets intervals and scheduledTimetableSequenceNumber
    public void calculateScheduledIntervals() {
        if (abTimetable.size() > 0) {

            LocalDateTime previousTripPeriodStartTimeScheduled = null;
            int index = 0;
            short scheduledTimetableSequenceNumber = 0;
            for (Map.Entry<LocalDateTime, IntervalTripPeriod> abTimetableEntry : abTimetable.entrySet()) {
                scheduledTimetableSequenceNumber++;
                abTimetableEntry.getValue().setScheduledTimetableSequenceNumber(scheduledTimetableSequenceNumber);

                if (index == 0) {
                    previousTripPeriodStartTimeScheduled = abTimetableEntry.getValue().getStartTimeScheduled();

                } else {
                    abTimetableEntry.getValue().setScheduledInterval(Duration.between(previousTripPeriodStartTimeScheduled, abTimetableEntry.getValue().getStartTimeScheduled()));
                    previousTripPeriodStartTimeScheduled = abTimetableEntry.getValue().getStartTimeScheduled();
                }
                index++;
            }
        }
        if (baTimetable.size() > 0) {

            LocalDateTime previousTripPeriodStartTimeScheduled = null;
            int index = 0;
            short scheduledTimetableSequenceNumber = 0;
            for (Map.Entry<LocalDateTime, IntervalTripPeriod> baTimetableEntry : baTimetable.entrySet()) {
                scheduledTimetableSequenceNumber++;
                baTimetableEntry.getValue().setScheduledTimetableSequenceNumber(scheduledTimetableSequenceNumber);

                if (index == 0) {
                    previousTripPeriodStartTimeScheduled = baTimetableEntry.getValue().getStartTimeScheduled();
                } else {
                    baTimetableEntry.getValue().setScheduledInterval(Duration.between(previousTripPeriodStartTimeScheduled, baTimetableEntry.getValue().getStartTimeScheduled()));
                    previousTripPeriodStartTimeScheduled = baTimetableEntry.getValue().getStartTimeScheduled();
                }
                index++;
            }
        }
    }

    public void calculateActualIntervals() {
        if (abTimetable.size() > 0) {

            LocalDateTime previousTripPeriodStartTimeActual = null;
            int index = 0;
            for (Map.Entry<LocalDateTime, IntervalTripPeriod> abTimetableEntry : abTimetable.entrySet()) {
                if (index == 0) {
                    previousTripPeriodStartTimeActual = abTimetableEntry.getValue().getStartTimeActual();
                } else {
                    if (previousTripPeriodStartTimeActual == null || abTimetableEntry.getValue().getStartTimeActual() == null) {
                        abTimetableEntry.getValue().setActualInterval(null);
                    } else {
                        abTimetableEntry.getValue().setActualInterval(Duration.between(previousTripPeriodStartTimeActual, abTimetableEntry.getValue().getStartTimeActual()));
                    }
                    previousTripPeriodStartTimeActual = abTimetableEntry.getValue().getStartTimeActual();
                }
                index++;
            }
        }
        if (baTimetable.size() > 0) {

            LocalDateTime previousTripPeriodStartTimeActual = null;
            int index = 0;
            for (Map.Entry<LocalDateTime, IntervalTripPeriod> baTimetableEntry : baTimetable.entrySet()) {
                if (index == 0) {
                    previousTripPeriodStartTimeActual = baTimetableEntry.getValue().getStartTimeActual();
                } else {
                    if (previousTripPeriodStartTimeActual == null || baTimetableEntry.getValue().getStartTimeActual() == null) {
                        baTimetableEntry.getValue().setActualInterval(null);
                    } else {

                        baTimetableEntry.getValue().setActualInterval(Duration.between(previousTripPeriodStartTimeActual, baTimetableEntry.getValue().getStartTimeActual()));
                    }
                    previousTripPeriodStartTimeActual = baTimetableEntry.getValue().getStartTimeActual();
                }
                index++;
            }
        }
    }

    @Override
    public void calculateGpsIntervals() {
        IntervalTripPeriod intervalTripPeriod;
        IntervalTripPeriod previousTripPeriod = null;

        LocalDateTime previousTripPeriodStartTimeActual = null;

        if (getAbGpsTimetable().size() > 0) {

            int index = 0;
            for (Map.Entry<LocalDateTime, DetailedTripPeriod> abGpsTimetableEntry : getAbGpsTimetable().entrySet()) {
                intervalTripPeriod = (IntervalTripPeriod) abGpsTimetableEntry.getValue();
                if (index == 0) {
                    previousTripPeriodStartTimeActual = intervalTripPeriod.getStartTimeActual();
                    previousTripPeriod = intervalTripPeriod;
                } else {
                    intervalTripPeriod.setGpsInterval(Duration.between(previousTripPeriodStartTimeActual, intervalTripPeriod.getStartTimeActual()));
                    if (previousTripPeriod.getScheduledTimetableSequenceNumber() > intervalTripPeriod.getScheduledTimetableSequenceNumber()) {
                        intervalTripPeriod.addRunOver("<");
                        previousTripPeriod.addRunOver(">");
                    }
                    previousTripPeriodStartTimeActual = intervalTripPeriod.getStartTimeActual();
                    previousTripPeriod = intervalTripPeriod;
                }

                intervalTripPeriod.calculateMisconduct();
                index++;
            }
        }
        if (getBaGpsTimetable().size() > 0) {

            int index = 0;
            for (Map.Entry<LocalDateTime, DetailedTripPeriod> baGpsTimetableEntry : getBaGpsTimetable().entrySet()) {
                intervalTripPeriod = (IntervalTripPeriod) baGpsTimetableEntry.getValue();
                if (index == 0) {
                    previousTripPeriodStartTimeActual = intervalTripPeriod.getStartTimeActual();
                    previousTripPeriod = intervalTripPeriod;
                } else {
                    intervalTripPeriod.setGpsInterval(Duration.between(previousTripPeriodStartTimeActual, intervalTripPeriod.getStartTimeActual()));
                    if (previousTripPeriod.getScheduledTimetableSequenceNumber() > intervalTripPeriod.getScheduledTimetableSequenceNumber()) {
                        intervalTripPeriod.addRunOver("<");
                        previousTripPeriod.addRunOver(">");
                    }
                    previousTripPeriodStartTimeActual = intervalTripPeriod.getStartTimeActual();
                    previousTripPeriod = intervalTripPeriod;
                }

                intervalTripPeriod.calculateMisconduct();
                index++;
            }
        }
    }

}

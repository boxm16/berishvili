package Model;

import java.time.LocalDateTime;
import java.util.TreeMap;

public class IntervalDay extends Day {

    private TreeMap<LocalDateTime, IntervalTripPeriod> abTimetable;
    private TreeMap<LocalDateTime, IntervalTripPeriod> baTimetable;

    private TreeMap<LocalDateTime, IntervalTripPeriod> abGpsTimetable;
    private TreeMap<LocalDateTime, IntervalTripPeriod> baGpsTimetable;

    public IntervalDay() {
        abTimetable = new TreeMap();
        baTimetable = new TreeMap();

        abGpsTimetable = new TreeMap();
        baGpsTimetable = new TreeMap();
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

    public TreeMap<LocalDateTime, IntervalTripPeriod> getAbGpsTimetable() {
        return abGpsTimetable;
    }

    public void setAbGpsTimetable(TreeMap<LocalDateTime, IntervalTripPeriod> abGpsTimetable) {
        this.abGpsTimetable = abGpsTimetable;
    }

    public TreeMap<LocalDateTime, IntervalTripPeriod> getBaGpsTimetable() {
        return baGpsTimetable;
    }

    public void setBaGpsTimetable(TreeMap<LocalDateTime, IntervalTripPeriod> baGpsTimetable) {
        this.baGpsTimetable = baGpsTimetable;
    }

    public void calculateIntervals() {
    }

}

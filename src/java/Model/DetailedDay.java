/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

public class DetailedDay extends Day {

    private TreeMap<LocalDateTime, DetailedTripPeriod> abGpsTimetable;
    private TreeMap<LocalDateTime, DetailedTripPeriod> baGpsTimetable;

    public DetailedDay() {
        abGpsTimetable = new TreeMap();
        baGpsTimetable = new TreeMap();
    }

    public TreeMap<LocalDateTime, DetailedTripPeriod> getAbGpsTimetable() {
        return abGpsTimetable;
    }

    public TreeMap<LocalDateTime, DetailedTripPeriod> getBaGpsTimetable() {
        return baGpsTimetable;
    }

    public void calculateGpsIntervals() {
        if (abGpsTimetable.size() > 0) {

            LocalDateTime previousBusTripPeriodStartTimeActual = null;
            int index = 0;
            for (Map.Entry<LocalDateTime, DetailedTripPeriod> abGpsTimetableEntry : abGpsTimetable.entrySet()) {
                if (index == 0) {
                    previousBusTripPeriodStartTimeActual = abGpsTimetableEntry.getValue().getStartTimeActual();
                } else {
                    abGpsTimetableEntry.getValue().setGpsInterval(Duration.between(previousBusTripPeriodStartTimeActual, abGpsTimetableEntry.getValue().getStartTimeActual()));
                    previousBusTripPeriodStartTimeActual = abGpsTimetableEntry.getValue().getStartTimeActual();
                }
                index++;
            }
        }
        if (baGpsTimetable.size() > 0) {

            LocalDateTime previousBusTripPeriodStartTimeActual = null;
            int index = 0;
            for (Map.Entry<LocalDateTime, DetailedTripPeriod> baGpsTimetableEntry : baGpsTimetable.entrySet()) {
                if (index == 0) {
                    previousBusTripPeriodStartTimeActual = baGpsTimetableEntry.getValue().getStartTimeActual();
                } else {
                    baGpsTimetableEntry.getValue().setGpsInterval(Duration.between(previousBusTripPeriodStartTimeActual, baGpsTimetableEntry.getValue().getStartTimeActual()));
                    previousBusTripPeriodStartTimeActual = baGpsTimetableEntry.getValue().getStartTimeActual();

                }
                index++;
            }
        }
    }
}

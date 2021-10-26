package Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class DetailedRoute extends BasicRoute {

    public void calculateData() {

        TreeMap<Date, Day> days = super.getDays();
        for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {

            DetailedDay detailedDay = (DetailedDay) dayEntry.getValue();
            TreeMap<Short, Exodus> exoduses = detailedDay.getExoduses();
            for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {
                TreeMap<String, TripVoucher> tripVouchers = exodusEntry.getValue().getTripVouchers();

                for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                    ArrayList<TripPeriod> tripPeriods = tripVoucherEntry.getValue().getTripPeriods();
                    LocalDateTime previousTripPeriodArrivalTimeScheduled = tripPeriods.get(0).getArrivalTimeScheduled();
                    LocalDateTime previousTripPeriodArrivalTimeActual = tripPeriods.get(0).getArrivalTimeActual();
                    int index = 0;
                    for (TripPeriod tripPeriod : tripPeriods) {
                        DetailedTripPeriod detailedTripPeriod = (DetailedTripPeriod) tripPeriod;
                        if (index > 0) {
                            detailedTripPeriod.setHaltTimeScheduled(Duration.between(previousTripPeriodArrivalTimeScheduled, tripPeriod.getStartTimeScheduled()));

                            if (previousTripPeriodArrivalTimeActual == null || tripPeriod.getStartTimeActual() == null) {
                                detailedTripPeriod.setHaltTimeActual(null);
                            } else {
                                detailedTripPeriod.setHaltTimeActual(Duration.between(previousTripPeriodArrivalTimeActual, tripPeriod.getStartTimeActual()));
                            }
                            previousTripPeriodArrivalTimeScheduled = tripPeriod.getArrivalTimeScheduled();
                            previousTripPeriodArrivalTimeActual = tripPeriod.getArrivalTimeActual();

                        }

                        detailedTripPeriod.calculateLostTime();

                        if (detailedTripPeriod.getStartTimeActual() != null) {
                            if (detailedTripPeriod.getType().equals("ab")) {
                                if (detailedDay.getAbGpsTimetable().containsKey(detailedTripPeriod.getStartTimeActual())) {
                                    //in unlikable, but possible case when two busses dispatch at the same time from the same point
                                    DetailedTripPeriod alterTripPeriod = detailedDay.getAbGpsTimetable().remove(detailedTripPeriod.getStartTimeActual());
                                    if (alterTripPeriod.getStartTimeScheduled().isAfter(detailedTripPeriod.getStartTimeScheduled())) {
                                        alterTripPeriod.setStartTimeActual(detailedTripPeriod.getStartTimeActual().plusNanos(1l));
                                    } else {
                                        detailedTripPeriod.setStartTimeActual(detailedTripPeriod.getStartTimeActual().plusNanos(1l));
                                    }
                                    detailedDay.getAbGpsTimetable().put(detailedTripPeriod.getStartTimeActual(), alterTripPeriod);
                                    detailedDay.getAbGpsTimetable().put(detailedTripPeriod.getStartTimeActual(), detailedTripPeriod);
                                } else {
                                    detailedDay.getAbGpsTimetable().put(detailedTripPeriod.getStartTimeActual(), detailedTripPeriod);
                                }
                            }
                            if (detailedTripPeriod.getType().equals("ba")) {
                                if (detailedDay.getBaGpsTimetable().containsKey(detailedTripPeriod.getStartTimeActual())) {
                                    //in unlikable, but possible case when two busses dispatch at the same time from the same point
                                    DetailedTripPeriod alterTripPeriod = detailedDay.getBaGpsTimetable().remove(detailedTripPeriod.getStartTimeActual());
                                    if (alterTripPeriod.getStartTimeScheduled().isAfter(detailedTripPeriod.getStartTimeScheduled())) {
                                        alterTripPeriod.setStartTimeActual(detailedTripPeriod.getStartTimeActual().plusNanos(1l));
                                    } else {
                                        detailedTripPeriod.setStartTimeActual(detailedTripPeriod.getStartTimeActual().plusNanos(1l));
                                    }
                                    detailedDay.getBaGpsTimetable().put(detailedTripPeriod.getStartTimeActual(), alterTripPeriod);
                                    detailedDay.getBaGpsTimetable().put(detailedTripPeriod.getStartTimeActual(), detailedTripPeriod);
                                } else {
                                    detailedDay.getBaGpsTimetable().put(detailedTripPeriod.getStartTimeActual(), detailedTripPeriod);
                                }
                            }
                        }
                        index++;
                    }
                }
            }
            detailedDay.calculateGpsIntervals();
        }
    }

    public void calculateIntervalsData() {
        MisconductTripPeriod misconductTripPeriod;
        TreeMap<Date, Day> days = super.getDays();
        for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {

            IntervalDay intervalDay = (IntervalDay) dayEntry.getValue();
            TreeMap<Short, Exodus> exoduses = intervalDay.getExoduses();
            for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {
                TreeMap<String, TripVoucher> tripVouchers = exodusEntry.getValue().getTripVouchers();

                for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                    ArrayList<TripPeriod> tripPeriods = tripVoucherEntry.getValue().getTripPeriods();
                    LocalDateTime previousTripPeriodArrivalTimeScheduled = tripPeriods.get(0).getArrivalTimeScheduled();
                    LocalDateTime previousTripPeriodArrivalTimeActual = tripPeriods.get(0).getArrivalTimeActual();
                    int index = 0;
                    for (TripPeriod tripPeriod : tripPeriods) {
                        IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) tripPeriod;
                        if (index > 0) {
                            intervalTripPeriod.setHaltTimeScheduled(Duration.between(previousTripPeriodArrivalTimeScheduled, tripPeriod.getStartTimeScheduled()));

                            if (previousTripPeriodArrivalTimeActual == null || tripPeriod.getStartTimeActual() == null) {
                                intervalTripPeriod.setHaltTimeActual(null);
                            } else {
                                intervalTripPeriod.setHaltTimeActual(Duration.between(previousTripPeriodArrivalTimeActual, tripPeriod.getStartTimeActual()));
                            }
                            //here is insertion for MisconductTripPeriod 
                            if (tripPeriod instanceof MisconductTripPeriod) {
                                misconductTripPeriod = (MisconductTripPeriod) tripPeriod;
                                misconductTripPeriod.setPreviousTripPeriodArrvialTimeActual(previousTripPeriodArrivalTimeActual);
                            }
                            previousTripPeriodArrivalTimeScheduled = tripPeriod.getArrivalTimeScheduled();
                            previousTripPeriodArrivalTimeActual = tripPeriod.getArrivalTimeActual();

                        }

                        intervalTripPeriod.calculateLostTime();

                        if (intervalTripPeriod.getType().equals("ab")) {
                            intervalDay.getAbTimetable().put(intervalTripPeriod.getStartTimeScheduled(), intervalTripPeriod);
                            if (intervalTripPeriod.getStartTimeActual() != null) {
                                if (intervalDay.getAbGpsTimetable().containsKey(intervalTripPeriod.getStartTimeActual())) {

                                    IntervalTripPeriod alterTripPeriod = (IntervalTripPeriod) intervalDay.getAbGpsTimetable().remove(intervalTripPeriod.getStartTimeActual());

                                    if (alterTripPeriod.getStartTimeScheduled().isAfter(intervalTripPeriod.getStartTimeScheduled())) {
                                        alterTripPeriod.setStartTimeActual(alterTripPeriod.getStartTimeActual().plusNanos(1l));
                                    } else {
                                        intervalTripPeriod.setStartTimeActual(intervalTripPeriod.getStartTimeActual().plusNanos(1l));
                                    }
                                    intervalDay.getAbGpsTimetable().put(alterTripPeriod.getStartTimeActual(), alterTripPeriod);
                                    intervalDay.getAbGpsTimetable().put(intervalTripPeriod.getStartTimeActual(), intervalTripPeriod);

                                } else {

                                    intervalDay.getAbGpsTimetable().put(intervalTripPeriod.getStartTimeActual(), intervalTripPeriod);
                                }
                            }
                        }
                        if (intervalTripPeriod.getType().equals("ba")) {
                            intervalDay.getBaTimetable().put(intervalTripPeriod.getStartTimeScheduled(), intervalTripPeriod);
                            if (intervalTripPeriod.getStartTimeActual() != null) {
                                if (intervalDay.getBaGpsTimetable().containsKey(intervalTripPeriod.getStartTimeActual())) {

                                    IntervalTripPeriod alterTripPeriod = (IntervalTripPeriod) intervalDay.getBaGpsTimetable().remove(intervalTripPeriod.getStartTimeActual());

                                    if (alterTripPeriod.getStartTimeScheduled().isAfter(intervalTripPeriod.getStartTimeScheduled())) {
                                        alterTripPeriod.setStartTimeActual(alterTripPeriod.getStartTimeActual().plusNanos(1l));
                                    } else {
                                        intervalTripPeriod.setStartTimeActual(intervalTripPeriod.getStartTimeActual().plusNanos(1l));
                                    }
                                    intervalDay.getBaGpsTimetable().put(alterTripPeriod.getStartTimeActual(), alterTripPeriod);
                                    intervalDay.getBaGpsTimetable().put(intervalTripPeriod.getStartTimeActual(), intervalTripPeriod);
                                } else {

                                    intervalDay.getBaGpsTimetable().put(intervalTripPeriod.getStartTimeActual(), intervalTripPeriod);
                                }
                            }
                        }

                        index++;
                    }
                }
            }
            //seuquence do matter
            intervalDay.calculateScheduledIntervals();
            // intervalDay.calculateActualIntervals()//dont need anymore
            intervalDay.calculateGpsIntervals();
        }
    }

    public void calculateIntervalsDataVVersion() {

        TreeMap<Date, Day> days = super.getDays();
        for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
            IntervalDay intervalDay = (IntervalDay) dayEntry.getValue();

            TreeMap<Short, Exodus> exoduses = intervalDay.getExoduses();

            for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {

                TreeMap<String, TripVoucher> tripVouchers = exodusEntry.getValue().getTripVouchers();

                for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                    ArrayList<TripPeriod> tripPeriods = tripVoucherEntry.getValue().getTripPeriods();
                    for (TripPeriod tripPeriod : tripPeriods) {
                        IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) tripPeriod;

                        if (intervalTripPeriod.getType().equals("ab")) {
                            //first af all putting trip into SCHEDULED TIMETABLE
                            intervalDay.getAbTimetable().put(intervalTripPeriod.getStartTimeScheduled(), intervalTripPeriod);
                            if (intervalDay.getAbTimetable().size() > 2) {
                                intervalDay.getAbTimetable().pollFirstEntry();
                                //keeping table with only two trips
                            }
                            //now going for GPS (Actual) TIMETABEL
                            LocalDateTime startTimeActual = intervalTripPeriod.getStartTimeActual();
                            if (startTimeActual == null) {
                                //try to deside sequence by comparing arrivalTimeActual of this trip and those trips that are inside gpsTimetable
                                if (intervalTripPeriod.getArrivalTimeActual() == null) {
                                    //do nothing, you cant do here something
                                } else {
                                    if (intervalDay.getAbGpsTimetable().size() == 1) {
                                        Map.Entry<LocalDateTime, DetailedTripPeriod> firstEntry = intervalDay.getAbGpsTimetable().firstEntry();
                                        DetailedTripPeriod tabledTripPeriod = firstEntry.getValue();
                                        if (tabledTripPeriod.getArrivalTimeActual() != null) {
                                            LocalDateTime tabledTripPeriodArrivalTimeActual = tabledTripPeriod.getArrivalTimeActual();
                                            LocalDateTime newTripPeriodArrivalTimeActual = intervalTripPeriod.getArrivalTimeActual();
                                            if (newTripPeriodArrivalTimeActual.isAfter(tabledTripPeriodArrivalTimeActual)) {
                                                LocalDateTime fakeStartTimeActual = firstEntry.getKey().plusSeconds(1);
                                                intervalTripPeriod.setSpacialCase(true);
                                                intervalDay.getAbGpsTimetable().put(fakeStartTimeActual, intervalTripPeriod);
                                            } else {
                                                LocalDateTime fakeStartTimeActual = firstEntry.getKey().minusSeconds(1);
                                                intervalTripPeriod.setSpacialCase(true);
                                                intervalDay.getAbGpsTimetable().put(fakeStartTimeActual, intervalTripPeriod);
                                            }
                                        }
                                    }
                                    if (intervalDay.getAbGpsTimetable().size() == 2) {
                                        Map.Entry<LocalDateTime, DetailedTripPeriod> firstEntry = intervalDay.getAbGpsTimetable().firstEntry();
                                        Map.Entry<LocalDateTime, DetailedTripPeriod> lastEntry = intervalDay.getAbGpsTimetable().lastEntry();
                                        DetailedTripPeriod firstTabledTripPeriod = firstEntry.getValue();
                                        DetailedTripPeriod secondTabledTripPeriod = lastEntry.getValue();
                                        if (secondTabledTripPeriod.getArrivalTimeActual() != null) {
                                            LocalDateTime secondTabledTripPeriodArrivalTimeActual = secondTabledTripPeriod.getArrivalTimeActual();
                                            LocalDateTime newTripPeriodArrivalTimeActual = intervalTripPeriod.getArrivalTimeActual();
                                            if (newTripPeriodArrivalTimeActual.isAfter(secondTabledTripPeriodArrivalTimeActual)) {
                                                LocalDateTime fakeStartTimeActual = lastEntry.getKey().plusSeconds(1);
                                                intervalTripPeriod.setSpacialCase(true);
                                                intervalDay.getAbGpsTimetable().put(fakeStartTimeActual, intervalTripPeriod);
                                            } else {
                                                LocalDateTime firstTabledTripPeriodArrivalTimeActual = firstTabledTripPeriod.getArrivalTimeActual();
                                                if (firstTabledTripPeriodArrivalTimeActual == null) {

                                                    //if there is not arrivalTimeActual we cant use it in guaranty trips
                                                    System.out.println("Ox mana mou; ROute Num:" + getNumber() + "//DateStamp:" + dayEntry.getValue().getDateStamp() + "//Exodus NUmb:" + exodusEntry.getValue().getNumber() + "//STS" + intervalTripPeriod.getStartTimeScheduledString());
                                                } else {
                                                    if (newTripPeriodArrivalTimeActual.isAfter(firstTabledTripPeriodArrivalTimeActual)) {
                                                        LocalDateTime fakeStartTimeActual = firstEntry.getKey().plusSeconds(1);
                                                        intervalTripPeriod.setSpacialCase(true);
                                                        intervalDay.getAbGpsTimetable().put(fakeStartTimeActual, intervalTripPeriod);
                                                    } else {
                                                        LocalDateTime fakeStartTimeActual = firstEntry.getKey().minusSeconds(1);
                                                        intervalTripPeriod.setSpacialCase(true);
                                                        intervalDay.getAbGpsTimetable().put(fakeStartTimeActual, intervalTripPeriod);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                intervalDay.getAbGpsTimetable().put(startTimeActual, intervalTripPeriod);
                            }

                            //keeping gpsTimetable small
                            if (intervalDay.getAbGpsTimetable().size() > 2) {
                                intervalDay.getAbGpsTimetable().pollFirstEntry();
                            }
                        }

                        //--------------ba-------------
                    }
                }
            }
        }
    }

}

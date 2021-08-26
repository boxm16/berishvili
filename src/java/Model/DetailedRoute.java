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
                                detailedDay.getAbGpsTimetable().put(detailedTripPeriod.getStartTimeActual(), detailedTripPeriod);
                            }
                            if (detailedTripPeriod.getType().equals("ba")) {
                                detailedDay.getBaGpsTimetable().put(detailedTripPeriod.getStartTimeActual(), detailedTripPeriod);
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
                        IntervalTripPeriod detailedTripPeriod = (IntervalTripPeriod) tripPeriod;
                        if (detailedTripPeriod.getStartTimeActual() != null) {
                            if (detailedTripPeriod.getType().equals("ab")) {
                                intervalDay.getAbGpsTimetable().put(detailedTripPeriod.getStartTimeActual(), detailedTripPeriod);
                            }
                            if (detailedTripPeriod.getType().equals("ba")) {
                                intervalDay.getBaGpsTimetable().put(detailedTripPeriod.getStartTimeActual(), detailedTripPeriod);
                            }
                        }
                        index++;
                    }
                }
            }
            intervalDay.calculateIntervals();
        }
    }

}

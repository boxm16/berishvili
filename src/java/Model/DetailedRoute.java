package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class DetailedRoute extends BasicRoute {

    public void calculateData() {
        int index = 0;
        LocalDateTime previousTripPeriodArrivalTimeScheduled;
        LocalDateTime previousTripPeriodArrivalTimeActual;
        TreeMap<Date, Day> days = super.getDays();
        for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
            TreeMap<Short, Exodus> exoduses = dayEntry.getValue().getExoduses();
            for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {
                TreeMap<String, TripVoucher> tripVouchers = exodusEntry.getValue().getTripVouchers();

                for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                    ArrayList<TripPeriod> tripPeriods = tripVoucherEntry.getValue().getTripPeriods();

                    for (TripPeriod tripPeriod : tripPeriods) {
                        if (index == 0) {
                        }
                        index++;
                    }

                }

            }
        }
    }
}

package graphical;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeMap;

public class Exodus {

    private ArrayList<TripPeriod> tripPeriods;

    public Exodus() {
        tripPeriods = new ArrayList();
    }

    public ArrayList<TripPeriod> getTripPeriods() {
        return tripPeriods;
    }

    public void setTripPeriods(ArrayList<TripPeriod> tripPeriods) {
        this.tripPeriods = tripPeriods;
    }

    public TreeMap<LocalDateTime, TripPeriod> getTripPeriodsWithoutBreak(ExodusIgnitionCode exodusIgnitionCode) {

        return new TreeMap();
    }

}

package Model;

import java.util.ArrayList;

public class DetailedRoutesPager {

    private ArrayList<String> routeNumbers;
    private ArrayList<String> dateStamps;
    private String currentRoute;

    public DetailedRoutesPager() {
        routeNumbers = new ArrayList<>();
        dateStamps = new ArrayList<>();
    }

    public void addRouteNumber(String routeNumber) {
        if (!this.routeNumbers.contains(routeNumber)) {
            this.routeNumbers.add(routeNumber);
        }
    }

    public void addDateStamp(String dateStamp) {
        if (!this.dateStamps.contains(dateStamp)) {
            this.dateStamps.add(dateStamp);
        }
    }

    public ArrayList<String> getRouteNumbers() {
        return routeNumbers;
    }

    public void setRouteNumbers(ArrayList<String> routeNumbers) {
        this.routeNumbers = routeNumbers;
    }

    public ArrayList<String> getDateStamps() {
        return dateStamps;
    }

    public void setDateStamps(ArrayList<String> dateStamps) {
        this.dateStamps = dateStamps;
    }

    public String getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(String currentRoute) {
        if (currentRoute.equals("initial")) {
            if (this.routeNumbers.size() > 0) {
                this.currentRoute = this.routeNumbers.get(0);
            } else {
                this.currentRoute = null;
            }
        } else {
            this.currentRoute = currentRoute;
        }
    }
}

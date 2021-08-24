package Model;

import java.util.ArrayList;

public class DetailedRoutesPager {

    private ArrayList<String> routeNumbers;
    private ArrayList<String> dateStamps;
    private int currentPage;

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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getCurrentRouteNumber() {
        return this.routeNumbers.get(this.currentPage - 1);
    }

}

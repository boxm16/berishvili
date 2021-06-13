package Model;

import java.util.TreeMap;

public class GuarantyRoute extends Route {

    private String dateStamp;
    private String busType;
    private TreeMap<Short, GuarantyExodus> exoduses;

    public GuarantyRoute() {
        this.exoduses = new TreeMap<>();
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public TreeMap<Short, GuarantyExodus> getExoduses() {
        return exoduses;
    }

    public void setExoduses(TreeMap<Short, GuarantyExodus> exoduses) {
        this.exoduses = exoduses;
    }

}

package Model;

import java.util.TreeMap;

public class Exodus {

    private short number;
    private TreeMap<String, TripVoucher> tripVouchers;
    private String driverName;

    public Exodus() {
        this.tripVouchers = new TreeMap();
    }

    public short getNumber() {
        return number;
    }

    public void setNumber(short number) {
        this.number = number;
    }

    public TreeMap<String, TripVoucher> getTripVouchers() {
        return tripVouchers;
    }

    public void setTripVouchers(TreeMap<String, TripVoucher> tripVouchers) {
        this.tripVouchers = tripVouchers;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

}

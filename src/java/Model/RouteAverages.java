package Model;

import Controller.Converter;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RouteAverages {

    private String routeNumber;

    private HashMap<Duration, Integer> abTripPeriodTimes;
    private int abLowCount;
    private long abLowTotal;
    private int abHighCount;
    private long abHighTotal;

    private HashMap<Duration, Integer> baTripPeriodTimes;
    private int baLowCount;
    private long baLowTotal;
    private int baHighCount;
    private long baHighTotal;

    private String dateStamps;

    private Converter converter;

    HashMap<Integer, DriverAverage> driverAverages;

    public RouteAverages() {
        abTripPeriodTimes = new HashMap<>();
        baTripPeriodTimes = new HashMap<>();

        converter = new Converter();

        driverAverages = new HashMap<>();
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public int getAbLowCount() {
        return abLowCount;
    }

    public void setAbLowCount(int abLowCount) {
        this.abLowCount = abLowCount;
    }

    public int getBaLowCount() {
        return baLowCount;
    }

    public void setBaLowCount(int baLowCount) {
        this.baLowCount = baLowCount;
    }

    public long getAbLowTotal() {
        return abLowTotal;
    }

    public void setAbLowTotal(long abLowTotal) {
        this.abLowTotal = abLowTotal;
    }

    public long getBaLowTotal() {
        return baLowTotal;
    }

    public void setBaLowTotal(long baLowTotal) {
        this.baLowTotal = baLowTotal;
    }

    public int getAbHighCount() {
        return abHighCount;
    }

    public void setAbHighCount(int abHighCount) {
        this.abHighCount = abHighCount;
    }

    public long getAbHighTotal() {
        return abHighTotal;
    }

    public void setAbHighTotal(long abHighTotal) {
        this.abHighTotal = abHighTotal;
    }

    public int getBaHighCount() {
        return baHighCount;
    }

    public void setBaHighCount(int baHighCount) {
        this.baHighCount = baHighCount;
    }

    public long getBaHighTotal() {
        return baHighTotal;
    }

    public void setBaHighTotal(long baHighTotal) {
        this.baHighTotal = baHighTotal;
    }

    public Duration getAbLowAverage() {
        return Duration.ofSeconds(this.abLowTotal / this.abLowCount);

    }

    public Duration getBaLowAverage() {
        return Duration.ofSeconds(this.baLowTotal / this.baLowCount);
    }

    public String getAbLowAverageString() {
        if (0 == this.abLowCount) {
            return "";
        }
        return converter.convertDurationToString(Duration.ofSeconds(this.abLowTotal / this.abLowCount));
    }

    public String getBaLowAverageString() {
        if (0 == this.baLowCount) {
            return "";
        }
        return converter.convertDurationToString(Duration.ofSeconds(this.baLowTotal / this.baLowCount));
    }

    //--
    public String getAbHighAverageString() {
        if (0 == this.abHighCount) {
            return "";
        }
        return converter.convertDurationToString(Duration.ofSeconds(this.abHighTotal / this.abHighCount));
    }

    public String getBaHighAverageString() {
        if (0 == this.baHighCount) {
            return "";
        }
        return converter.convertDurationToString(Duration.ofSeconds(this.baHighTotal / this.baHighCount));
    }

    public int getAbLowAndHighCount() {
        return this.abLowCount + this.abHighCount;
    }

    public int getBaLowAndHighCount() {
        return this.baLowCount + this.baHighCount;
    }

    public String getAbLowAndHighAverage() {
        if (getAbLowAndHighCount() == 0) {
            return "";
        }
        return converter.convertDurationToString(Duration.ofSeconds((this.abLowTotal + this.abHighTotal) / getAbLowAndHighCount()));

    }

    public String getBaLowAndHighAverage() {
        if (getBaLowAndHighCount() == 0) {
            return "";
        }
        return converter.convertDurationToString(Duration.ofSeconds((this.baLowTotal + this.baHighTotal) / getBaLowAndHighCount()));
    }

    public void addABTripPeriodTime(Duration tripPeriodStandartTime) {
        if (this.abTripPeriodTimes.containsKey(tripPeriodStandartTime)) {
            int count = this.abTripPeriodTimes.get(tripPeriodStandartTime);
            count++;
            this.abTripPeriodTimes.put(tripPeriodStandartTime, count);
        } else {
            this.abTripPeriodTimes.put(tripPeriodStandartTime, 1);
        }
    }

    public void addBATripPeriodTime(Duration tripPeriodStandartTime) {
        if (this.baTripPeriodTimes.containsKey(tripPeriodStandartTime)) {
            int count = this.baTripPeriodTimes.get(tripPeriodStandartTime);
            count++;
            this.baTripPeriodTimes.put(tripPeriodStandartTime, count);
        } else {
            this.baTripPeriodTimes.put(tripPeriodStandartTime, 1);
        }
    }

    public String abTripPeriodTimeIsMultiple() {
        if (this.abTripPeriodTimes.size() > 1) {
            return "X";
        } else {
            return "";
        }
    }

    public String baTripPeriodTimeIsMultiple() {
        if (this.baTripPeriodTimes.size() > 1) {
            return "X";
        } else {
            return "";
        }
    }

    public String getABTripPeriodStandartTimeString() {
        Duration abStandartTripPeriodTime = Duration.ZERO;
        if (this.abTripPeriodTimes.size() > 0) {
            //iterating map to find max value
            Map.Entry<Duration, Integer> maxEntry = null;
            for (Map.Entry<Duration, Integer> entry : abTripPeriodTimes.entrySet()) {
                if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                    maxEntry = entry;
                }
            }
            abStandartTripPeriodTime = maxEntry.getKey();
            return converter.convertDurationToString(abStandartTripPeriodTime);
        } else {
            return "";
        }
    }

    public String getBATripPeriodStandartTimeString() {
        Duration baStandartTripPeriodTime = Duration.ZERO;
        if (this.baTripPeriodTimes.size() > 0) {
            //iterating map to find max value
            Map.Entry<Duration, Integer> maxEntry = null;
            for (Map.Entry<Duration, Integer> entry : baTripPeriodTimes.entrySet()) {
                if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                    maxEntry = entry;
                }
            }
            baStandartTripPeriodTime = maxEntry.getKey();
            return converter.convertDurationToString(baStandartTripPeriodTime);
        } else {
            return "";
        }
    }

    public String getTripRoundStandartTimeString() {
        Duration abStandartTripPeriodTime = Duration.ZERO;
        if (this.abTripPeriodTimes.size() > 0) {
            //iterating map to find max value
            Map.Entry<Duration, Integer> maxEntry = null;
            for (Map.Entry<Duration, Integer> entry : abTripPeriodTimes.entrySet()) {
                if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                    maxEntry = entry;
                }
            }
            abStandartTripPeriodTime = maxEntry.getKey();
        }
//--------------
        Duration baStandartTripPeriodTime = Duration.ZERO;
        if (this.baTripPeriodTimes.size() > 0) {
            //iterating map to find max value
            Map.Entry<Duration, Integer> maxEntry = null;
            for (Map.Entry<Duration, Integer> entry : baTripPeriodTimes.entrySet()) {
                if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                    maxEntry = entry;
                }
            }
            baStandartTripPeriodTime = maxEntry.getKey();
        }
        return converter.convertDurationToString(abStandartTripPeriodTime.plus(baStandartTripPeriodTime));
    }

    public int getAllCount() {
        return this.getAbLowAndHighCount() + this.getBaLowAndHighCount();
    }

    public String getAllAverage() {
        if (getAbLowAndHighCount() == 0 && getBaLowAndHighCount() == 0) {
            return "";
        }
        Duration abAllAverage = Duration.ZERO;
        Duration baAllAverage = Duration.ZERO;
        if (this.abLowCount + this.abHighCount != 0) {
            abAllAverage = Duration.ofSeconds((this.abLowTotal + this.abHighTotal) / (this.abLowCount + this.abHighCount));
        }
        if (this.baLowCount + this.baHighCount != 0) {
            baAllAverage = Duration.ofSeconds((this.baLowTotal + this.baHighTotal) / (this.baLowCount + this.baHighCount));
        }
        return converter.convertDurationToString(abAllAverage.plus(baAllAverage));
    }

    public String getDateStamps() {
        return dateStamps;
    }

    public void setDateStamps(String dateStamps) {
        // dateStamps = dateStamps.replaceAll("'", "%27");

        this.dateStamps = dateStamps;
    }

    public void addAbHighTripPeriod(int driverNumber, String driverName, TripPeriod2X tripPeriod) {
        DriverAverage driverAverage = this.driverAverages.get(driverNumber);
        if (driverAverage == null) {
            driverAverage = new DriverAverage();
            driverAverage.setDriverNumber(driverNumber);
            driverAverage.setDriverName(driverName);
        }
        driverAverage.addAbHighTripPeriod(tripPeriod);
        this.driverAverages.put(driverNumber, driverAverage);
    }

    public void addAbLowTripPeriod(int driverNumber, String driverName, TripPeriod2X tripPeriod) {

        DriverAverage driverAverage = this.driverAverages.get(driverNumber);
        if (driverAverage == null) {
            driverAverage = new DriverAverage();
            driverAverage.setDriverNumber(driverNumber);
            driverAverage.setDriverName(driverName);
        }
        driverAverage.addAbLowTripPeriod(tripPeriod);
        this.driverAverages.put(driverNumber, driverAverage);
    }

    //---------- 
    public void addBaHighTripPeriod(int driverNumber, String driverName, TripPeriod2X tripPeriod) {
        DriverAverage driverAverage = this.driverAverages.get(driverNumber);
        if (driverAverage == null) {
            driverAverage = new DriverAverage();
            driverAverage.setDriverNumber(driverNumber);
            driverAverage.setDriverName(driverName);
        }
        driverAverage.addBaHighTripPeriod(tripPeriod);
        this.driverAverages.put(driverNumber, driverAverage);
    }

    public void addBaLowTripPeriod(int driverNumber, String driverName, TripPeriod2X tripPeriod) {
        DriverAverage driverAverage = this.driverAverages.get(driverNumber);
        if (driverAverage == null) {
            driverAverage = new DriverAverage();
            driverAverage.setDriverNumber(driverNumber);
            driverAverage.setDriverName(driverName);
        }
        driverAverage.addBaLowTripPeriod(tripPeriod);
        this.driverAverages.put(driverNumber, driverAverage);
    }

    public HashMap<Integer, DriverAverage> getDriverAverages() {
        return driverAverages;
    }

    public void setDriversAverages(HashMap<Integer, DriverAverage> driverAverages) {
        this.driverAverages = driverAverages;
    }

}

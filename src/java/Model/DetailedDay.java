/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    public void setAbGpsTimetable(TreeMap<LocalDateTime, DetailedTripPeriod> abGpsTimetable) {
        this.abGpsTimetable = abGpsTimetable;
    }
    
    public void setBaGpsTimetable(TreeMap<LocalDateTime, DetailedTripPeriod> baGpsTimetable) {
        this.baGpsTimetable = baGpsTimetable;
    }
    
    public HashMap<String, TreeMap> getHaltTimeTables() {
        HashMap<String, TreeMap> haltTimeTables = new HashMap();
        TreeMap<LocalDateTime, Halt> aPointHaltTimetable = new TreeMap<>();
        TreeMap<LocalDateTime, Halt> bPointHaltTimetable = new TreeMap<>();
        
        TreeMap<Short, Exodus> exoduses = super.getExoduses();
        for (Map.Entry<Short, Exodus> exodusEntry
                : exoduses.entrySet()) {
            TreeMap<String, TripVoucher> tripVouchers = exodusEntry.getValue().getTripVouchers();
            
            for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                ArrayList<TripPeriod> tripPeriods = tripVoucherEntry.getValue().getTripPeriods();
                LocalDateTime previousTripPeriodArrivalTimeActual = tripPeriods.get(0).getArrivalTimeActual();
                int index = 0;
                for (TripPeriod tripPeriod : tripPeriods) {
                    DetailedTripPeriod detailedTripPeriod = (DetailedTripPeriod) tripPeriod;
                    if (index > 0) {
                        
                        if (previousTripPeriodArrivalTimeActual == null || tripPeriod.getStartTimeActual() == null) {
                            
                        } else {
                            Halt halt = new Halt();
                            halt.setStartTime(previousTripPeriodArrivalTimeActual);
                            halt.setTripPeriodStartTimeScheduled(tripPeriod.getStartTimeScheduled());
                            halt.setExodusNumber(exodusEntry.getValue().getNumber());
                            halt.setEndTime(tripPeriod.getStartTimeActual());
                            if (tripPeriod.getType().equals("ab")) {
                                halt.setPoint("a");
                                aPointHaltTimetable.put(previousTripPeriodArrivalTimeActual, halt);
                            }
                            if (tripPeriod.getType().equals("ba")) {
                                halt.setPoint("b");
                                bPointHaltTimetable.put(previousTripPeriodArrivalTimeActual, halt);
                            }
                        }
                        previousTripPeriodArrivalTimeActual = tripPeriod.getArrivalTimeActual();
                    }
                    index++;
                }
            }
            
        }
        haltTimeTables.put("aPoint", aPointHaltTimetable);
        haltTimeTables.put("bPoint", bPointHaltTimetable);
        return haltTimeTables;
        
    }
    
}

package Controller;

import DAO.RouteDao;
import Model.GuarantyExodus;
import Model.GuarantyRoute;
import Model.GuarantyTripPeriod;
import Model.RouteData;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class GuarantyController {
    
    private String basementDirectory;
    
    public GuarantyController() {
        BasementController basementController = new BasementController();
        this.basementDirectory = basementController.getBasementDirectory();
        
    }
    
    @RequestMapping(value = "guarantyTripsUploadPage")
    public String goToGuarantyTripsUploadPage(ModelMap model) {
        
        model.addAttribute("uploadedFileExists", true);
        return "guarantyTripsUploadPage";
    }
    
    @RequestMapping(value = "/saveGuarantyExcelFile", method = RequestMethod.POST)
    public String upload(@RequestParam CommonsMultipartFile file, ModelMap model) {
        if (file.isEmpty()) {
            model.addAttribute("status", "Upload could not been completed");
            model.addAttribute("errorMessage", "emptyFile");
            return "guarantyTripsUploadPage";
        }
        String filename = "uploadedGuarantyExcelFile.xlsx";
        try {
            byte barr[] = file.getBytes();
            
            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(this.basementDirectory + "/uploads/" + filename));
            bout.write(barr);
            bout.flush();
            bout.close();
            
        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("status", "Upload could not been completed");
            model.addAttribute("errorMessage", e);
            return "guarantyTripsUploadPage";
        }
        return "guarantyTripsDashboard";
    }
    
    @RequestMapping(value = "/gotGuarantyDashboard", method = RequestMethod.GET)
    public String gotGuarantyDashboard() {
        
        return "guarantyTripsDashboard";
    }
    
    @RequestMapping(value = "guarantyExport", method = RequestMethod.POST)
    public String garantyExport(String fileName, ModelMap model) {
        
        RouteFactory routeFactory = new RouteFactory();
        TreeMap<Float, GuarantyRoute> guarantyRoutes = new TreeMap();
        guarantyRoutes = routeFactory.createGuarantyRoutesFromUploadedFile();
        
        if (guarantyRoutes.containsKey(0.001f)) {//0.001f is code for error when actualStartTimes exist in file,(it cant be, because this file have to be from futer dates

            model.addAttribute("error", "ატვირთული ფაილი არ არის საგარანტიო გასვლების გამოსათვლელად გამოსადეგი (ფაილში იძებნება ფაქტიური გასვლის დრო, რაც აქ დაუშვებელია)"
                    + "<a  href=\"guarantyTripsUploadPage.htm\">დაბრუნდი და ატვირთე ახალი ფაილი</a>");
            
        }
        if (guarantyRoutes.containsKey(0.002f)) {//0.002 is a error code for error when uploaded file contains data with more than one datestamp

            model.addAttribute("dateStampError", "ატვირთული ფაილი არ არის საგარანტიო გასვლების გამოსათვლელად გამოსადეგი (ფაილში იძებნება სხვადასხვა რიცხვი, რაც აქ დაუშვებელია)&nbsp&nbsp"
                    + "<a  href=\"guarantyTripsUploadPage.htm\">დაბრუნდი და ატვირთე ახალი ფაილი</a>");
            
        }

        //here we calculate given data to get some results inside guarantyRoutes
        calculateData(guarantyRoutes);
        //now add route point names and schemes  from db
        RouteDao routeDao = new RouteDao();
        TreeMap<Float, RouteData> routesDataFromDB = routeDao.getRoutesDataFromDB();
        for (Map.Entry<Float, GuarantyRoute> guarantyRoutesEntry : guarantyRoutes.entrySet()) {
            RouteData routeData = routesDataFromDB.get(guarantyRoutesEntry.getKey());
            if (routeData == null) {
                //here i select new routes (routes that exist in excel file but dont exist in database)
            } else {
                GuarantyRoute guarantyRoute = guarantyRoutesEntry.getValue();
                guarantyRoute.setaPoint(routeData.getaPoint());
                guarantyRoute.setbPoint(routeData.getbPoint());
                guarantyRoute.setScheme(routeData.getScheme());
            }
        }
        //now write the results
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.exportGuarantyRoutes(guarantyRoutes, fileName);
        
        model.addAttribute("fileName", fileName);
        return "guarantyTripsDashboard";
        
    }
    
    private void calculateData(TreeMap<Float, GuarantyRoute> guarantyRoutes) {
        //not very elegant code here, but i`m thinking about memory usage here ,man

        //man, its total mess, you would`t be able to find out anything here after 2 weeks
        ArrayList<LocalDateTime> abTimeTable = new ArrayList();
        ArrayList<LocalDateTime> baTimeTable = new ArrayList();
        ArrayList<GuarantyTripPeriod> tripPeriods;
        for (Map.Entry<Float, GuarantyRoute> routeEntry : guarantyRoutes.entrySet()) {
            
            GuarantyRoute guarantyRoute = routeEntry.getValue();
            TreeMap<Short, GuarantyExodus> exoduses = guarantyRoute.getExoduses();
            for (Map.Entry<Short, GuarantyExodus> exodusEntry : exoduses.entrySet()) {
                GuarantyExodus exodus = exodusEntry.getValue();
                ArrayList<GuarantyTripPeriod> guarantyTripPeriods = exodus.getGuarantyTripPeriods();
                for (GuarantyTripPeriod tripPeriod : guarantyTripPeriods) {
                    String tripPeriodType = tripPeriod.getType();
                    
                    if (tripPeriodType.equals("A_baseReturn") || tripPeriodType.equals("B_baseReturn")) {
                        LocalDateTime tripPeriodStartTime = tripPeriod.getStartTimeScheduled();
                        LocalDateTime routeEndTime = guarantyRoute.getRouteEndTime();
                        if (routeEndTime == null) {
                            guarantyRoute.setRouteEndTime(tripPeriodStartTime);
                        } else {
                            if (tripPeriodStartTime.isAfter(routeEndTime)) {
                                guarantyRoute.setRouteEndTime(tripPeriodStartTime);
                            }
                        }
                    } else {
                        if (tripPeriodType.equals("ab")) {
                            abTimeTable.add(tripPeriod.getStartTimeScheduled());
                        }
                        if (tripPeriodType.equals("ba")) {
                            baTimeTable.add(tripPeriod.getStartTimeScheduled());
                        }
                    }
                }

                //here i set tripPeriodTime to tripPeriods, i know its not elegant, again, but thinking memory usage
                /*becouse i use here only one arrayList for all exoduses, while if i put this code inside GuarantyROute(for example
               there will be created arrayList for each route 
                 */
                tripPeriods = exodus.getGuarantyTripPeriods();
                
                for (int x = 0; x < tripPeriods.size() - 1; x++) {//-1 because last trip period is base return
                    GuarantyTripPeriod tripPeriod = tripPeriods.get(x);
                    
                    LocalDateTime tripPeriodStartTime = tripPeriod.getStartTimeScheduled();
                    GuarantyTripPeriod nextTripPeriod = tripPeriods.get(x + 1);
                    LocalDateTime nextTripPeriodStartTime = nextTripPeriod.getStartTimeScheduled();
                    Duration difference = Duration.between(tripPeriodStartTime, nextTripPeriodStartTime);
                    tripPeriod.setTripPeriodTime(difference);
                }
                //end of setting tripPeriodTimes to tripPeriods

            }
            Collections.sort(abTimeTable);
            Collections.sort(baTimeTable);
            if (abTimeTable.size() > 2) {
                guarantyRoute.setAbGuarantyTripPeriodStartTimeScheduled(abTimeTable.get(abTimeTable.size() - 1));
                guarantyRoute.setAbSubguarantyTripPeriodStartTimeScheduled(abTimeTable.get(abTimeTable.size() - 2));
            }
            if (baTimeTable.size() > 2) {
                guarantyRoute.setBaGuarantyTripPeriodStartTimeScheduled(baTimeTable.get(baTimeTable.size() - 1));
                guarantyRoute.setBaSubguarantyTripPeriodStartTimeScheduled(baTimeTable.get(baTimeTable.size() - 2));
            }
            
            guarantyRoute.setStandardIntervalTime(calculateStandardIntervalTime(abTimeTable));
            guarantyRoute.setStandardTripPeriodTime(calculateStandardTripPeriodTime(guarantyRoute));

            //here i set totalRaces
            guarantyRoute.setTotalRaces(Float.valueOf(0));
            if (abTimeTable.size() > 0 && baTimeTable.size() > 0) {
                guarantyRoute.setTotalRaces((float) (abTimeTable.size() + (float) baTimeTable.size()) / 2);
                
            }
            if (abTimeTable.size() > 0 && baTimeTable.isEmpty()) {
                guarantyRoute.setTotalRaces((float) abTimeTable.size());
            }
            if (abTimeTable.isEmpty() && baTimeTable.size() > 0) {
                guarantyRoute.setTotalRaces((float) baTimeTable.size());
            }

            // here i set routeStartTime
            if (baTimeTable.size() > 0) {
                LocalDateTime abStartTime = abTimeTable.get(0);
                LocalDateTime baStartTime = baTimeTable.get(0);
                guarantyRoute.setRouteStartTime(abStartTime.isEqual(baStartTime) || abStartTime.isBefore(baStartTime) ? abStartTime : baStartTime);
            } else {
                guarantyRoute.setRouteStartTime(abTimeTable.get(0));
            }
            
            abTimeTable.clear();
            baTimeTable.clear();
            
        }
    }
    
    private Duration calculateStandardIntervalTime(ArrayList<LocalDateTime> timeTable) {
        HashMap<Duration, Integer> intervals = new HashMap();
        int index = 1;
        if (timeTable.size() < 2) {
            return null;
        } else {
            while (index < timeTable.size()) {
                Duration interval = Duration.between(timeTable.get(index - 1), timeTable.get(index));
                if (intervals.containsKey(interval)) {
                    int count = intervals.get(interval);
                    count++;
                    intervals.put(interval, count);
                } else {
                    intervals.put(interval, 0);
                }
                index++;
            }
        }
        //iterating map to find max value
        Map.Entry<Duration, Integer> maxEntry = null;
        for (Map.Entry<Duration, Integer> entry : intervals.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }
    
    private Duration calculateStandardTripPeriodTime(GuarantyRoute guarantyRoute) {
        
        HashMap<Duration, Integer> abTripPeriodTimes = new HashMap();
        HashMap<Duration, Integer> baTripPeriodTimes = new HashMap();
        
        TreeMap<Short, GuarantyExodus> exoduses = guarantyRoute.getExoduses();
        for (GuarantyExodus exodus : exoduses.values()) {
            
            for (GuarantyTripPeriod tripPeriod : exodus.getGuarantyTripPeriods()) {
                //----------------ab-----------
                if (tripPeriod.getType().equals("ab")) {
                    
                    Duration tripPeriodTime = tripPeriod.getTripPeriodTime();
                    
                    if (abTripPeriodTimes.containsKey(tripPeriodTime)) {
                        int count = abTripPeriodTimes.get(tripPeriodTime);
                        count++;
                        abTripPeriodTimes.put(tripPeriodTime, count);
                    } else {
                        abTripPeriodTimes.put(tripPeriodTime, 0);
                    }
                }
                //----------------ba-----------
                if (tripPeriod.getType().equals("ba")) {
                    Duration tripPeriodTime = tripPeriod.getTripPeriodTime();
                    if (baTripPeriodTimes.containsKey(tripPeriodTime)) {
                        int count = baTripPeriodTimes.get(tripPeriodTime);
                        count++;
                        baTripPeriodTimes.put(tripPeriodTime, count);
                    } else {
                        baTripPeriodTimes.put(tripPeriodTime, 0);
                    }
                }
            }
        }
        Duration abStandartTripPeriodTime = Duration.ZERO;
        Duration baStandartTripPeriodTime = Duration.ZERO;
        
        if (abTripPeriodTimes.size() > 0) {
            //iterating map to find max value
            Map.Entry<Duration, Integer> maxEntry = null;
            for (Map.Entry<Duration, Integer> entry : abTripPeriodTimes.entrySet()) {
                if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                    maxEntry = entry;
                }
            }
            abStandartTripPeriodTime = maxEntry.getKey();
        }
        
        if (baTripPeriodTimes.size() > 0) {
            //iterating map to find max value
            Map.Entry<Duration, Integer> maxEntry = null;
            for (Map.Entry<Duration, Integer> entry : baTripPeriodTimes.entrySet()) {
                if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                    maxEntry = entry;
                }
            }
            baStandartTripPeriodTime = maxEntry.getKey();
        }
        return abStandartTripPeriodTime.plus(baStandartTripPeriodTime);
    }
}

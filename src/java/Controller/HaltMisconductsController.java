/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DetailedRouteDao;
import Model.Day;
import Model.DetailedDay;
import Model.DetailedRoute;
import Model.DetailedRoutesPager;
import Model.Halt;
import Model.HaltFilter;
import Model.HaltMisconduct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HaltMisconductsController {

    @Autowired
    private DetailedRouteDao detailedRouteDao;
    private MemoryUsage memoryUsage;
    private Converter converter;

    @RequestMapping(value = "haltMisconducts")
    public String haltMisconducts(@RequestParam("routes:dates") String routeDates, HttpSession session, ModelMap model) {
        DetailedRoutesPager detailedRoutesPager = createDetailedRoutesPager(routeDates);
        TreeMap<Float, DetailedRoute> detailedRoutes = detailedRouteDao.getDetailedRoutes(detailedRoutesPager);
        ArrayList<HaltMisconduct> haltMisconductsList = getHaltMisconducts(detailedRoutes);
        model.addAttribute("haltMisconductsList", haltMisconductsList);
        return "haltMisconducts";
    }

    private DetailedRoutesPager createDetailedRoutesPager(String routeDates) {

        DetailedRoutesPager detailedRoutesPager = new DetailedRoutesPager("detailedRoutes");

        //trimming and cleaning input
        routeDates = routeDates.trim();
        if (routeDates.length() == 0) {
            return detailedRoutesPager;
        }
        if (routeDates.substring(routeDates.length() - 1, routeDates.length()).equals(",")) {
            routeDates = routeDates.substring(0, routeDates.length() - 1).trim();
        }
        String[] routeDatesArray = routeDates.split(",");
        for (String routeDate : routeDatesArray) {

            String[] routeDateArray = routeDate.split(":");
            String routeNumber = routeDateArray[0];
            String dateStamp = routeDateArray[1];
            detailedRoutesPager.addRouteNumber(routeNumber);
            detailedRoutesPager.addDateStamp(dateStamp);
        }
        return detailedRoutesPager;
    }

    private ArrayList<HaltMisconduct> getHaltMisconducts(TreeMap<Float, DetailedRoute> detailedRoutes) {
        ArrayList<HaltMisconduct> haltMisconductsList = new ArrayList();
        for (Map.Entry<Float, DetailedRoute> routeEntry : detailedRoutes.entrySet()) {
            DetailedRoute detailedRoute = routeEntry.getValue();
            //   System.out.println("Route ++++++++++++++++++++++++++++" + detailedRoute.getNumber() + "+++++++++++++++++++++++++++++++++++");

            TreeMap<Date, Day> days = detailedRoute.getDays();
            for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {

                DetailedDay detailedDay = (DetailedDay) dayEntry.getValue();
                // System.out.println("DAY  ----------------------" + detailedDay.getDateStamp() + "--------------------------");

                HashMap<String, TreeMap> haltTimeTables = detailedDay.getHaltTimeTables();
                TreeMap<LocalDateTime, Halt> aPointHaltTimetable = haltTimeTables.get("aPoint");
                //System.out.println("A POINT SIZE:" + aPointTimetable.size());

                ArrayList<Halt> aPointHaltsArryList = new ArrayList<Halt>(aPointHaltTimetable.values());
                HaltFilter haltFilter = null;
                HaltMisconduct haltMisconductCandidate = null;
                for (int x = 0; x < aPointHaltsArryList.size() - 1; x++) {
                    Halt halt = aPointHaltsArryList.get(x);
                    if (x == 0) {
                        haltFilter = new HaltFilter();
                    }
                    int sizeBeforeSifting = haltFilter.getParticipantHalts().size();
                    if (sizeBeforeSifting >= 2) {
                        if (sizeBeforeSifting == 2) {
                            haltMisconductCandidate = new HaltMisconduct();
                        } else {
                            haltMisconductCandidate.setParticipantHalts(haltFilter.getParticipantHalts());

                        }
                    }
                    if (x > 0) {
                        haltFilter.siftOutParticipantHaltsForGivenTime(halt.getStartTime());
                    }
                    int sizeAfterSifting = haltFilter.getParticipantHalts().size();
                    if (sizeBeforeSifting > 2 && sizeAfterSifting <= 2) {
                        haltMisconductCandidate.setRouteNumber(detailedRoute.getNumber());
                        haltMisconductCandidate.setDateStamp(detailedDay.getDateStamp());
                        haltMisconductCandidate.setStartTime(haltFilter.getStartTime());
                        haltMisconductsList.add(haltMisconductCandidate);
                        haltFilter = new HaltFilter();
                    }
                    haltFilter.addHalt(halt);
                }
            }
        }
        return haltMisconductsList;
    }

}

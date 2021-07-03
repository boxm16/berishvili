package Model;

import Controller.Converter;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class RoutesBlock {

    private int maxSize;
    private TreeMap<Float, BasicRoute> routes;
    private int routesDatesCount;
    private Converter converter;

    public RoutesBlock(int maxSize) {

        this.routes = new TreeMap<>();
        this.routesDatesCount = 0;
        this.maxSize = maxSize;
        converter = new Converter();
    }

    public void addRouteDate(String routeDate) {
        String[] routeDateArray = routeDate.split(":");
        String routeNumber = routeDateArray[0];
        String dateStamp = routeDateArray[1];
        Date date = this.converter.convertDateStampDatabaseFormatToDate(dateStamp);
        BasicRoute route;
        if (routes.containsKey(this.converter.convertRouteNumber(routeNumber))) {
            route = routes.get(this.converter.convertRouteNumber(routeNumber));
            Day day = new Day();
            day.setDateStamp(dateStamp);
            route.getDays().put(date, day);
            routesDatesCount++;
        } else {
            route = new BasicRoute();
            route.setNumber(routeNumber);

            Day day = new Day();
            day.setDateStamp(dateStamp);
            route.getDays().put(date, day);
            routesDatesCount++;
            routes.put(this.converter.convertRouteNumber(routeNumber), route);
        }

    }

    public boolean isFull() {
        // System.out.println("RoutesDatesCounte:" + this.routesDatesCount + "Max:" + this.maxSize);
        return (this.routesDatesCount > this.maxSize);
    }

    public BasicRoute removeLastRoute() {
        Float lastKey = this.routes.lastKey();
        BasicRoute route = this.routes.get(lastKey);
        this.routes.pollLastEntry();
        return route;
    }

    public void addRoute(BasicRoute route) {
        this.routes.put(this.converter.convertRouteNumber(route.getNumber()), route);
        this.routesDatesCount = +route.getDays().size();
    }

    public TreeMap<Float, BasicRoute> getRoutes() {
        return this.routes;
    }

    public String getName() {
        String name = "";
        for (Map.Entry<Float, BasicRoute> routeEntry : this.routes.entrySet()) {
            if (name.equals("")) {
                name += routeEntry.getValue().getNumber();
            } else {
                name += ", " + routeEntry.getValue().getNumber();
            }
        }
        return name;
    }

}

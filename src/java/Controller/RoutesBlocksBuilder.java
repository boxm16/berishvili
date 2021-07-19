package Controller;

import Model.BasicRoute;
import Model.Day;
import Model.RoutesBlock;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class RoutesBlocksBuilder {
    
    private Converter converter;
    
    public RoutesBlocksBuilder() {
        converter = new Converter();
    }
    
    public ArrayList<RoutesBlock> createRoutesBlocks(String routeDates) {
        int routeBlockMaxSize = 25;
        String[] routeDatesArray = routeDates.split(",");
        ArrayList<RoutesBlock> routesBlocksArray = new ArrayList();
        RoutesBlock routesBlock = new RoutesBlock(routeBlockMaxSize);
        for (String routeDate : routeDatesArray) {
            if (!routeDate.equals("")) {
                String[] routeDateArray = routeDate.split(":");
                String routeNumber = routeDateArray[0];
                String dateStamp = routeDateArray[1];
                Date date = this.converter.convertDateStampDatabaseFormatToDate(dateStamp);
                if (routesBlock.isFull() && !routesBlock.getRoutes().containsKey(this.converter.convertRouteNumber(routeNumber))) {
                    //after&& is let complete route (otherwise sometime route can be broken into parts)
                    if (routesBlock.getRoutes().size() > 1) {
                        BasicRoute lastRoute = routesBlock.removeLastRoute();
                        routesBlocksArray.add(routesBlock);
                        routesBlock = new RoutesBlock(routeBlockMaxSize);
                        routesBlock.addRoute(lastRoute);
                        routesBlock.addRouteDate(routeNumber, dateStamp);
                    } else {
                        routesBlocksArray.add(routesBlock);
                        routesBlock = new RoutesBlock(routeBlockMaxSize);
                        routesBlock.addRouteDate(routeNumber, dateStamp);
                    }
                } else {
                    routesBlock.addRouteDate(routeNumber, dateStamp);
                }
            }
        }
        routesBlocksArray.add(routesBlock);
        
        for (RoutesBlock rb : routesBlocksArray) {
            for (Map.Entry<Float, BasicRoute> entry : rb.getRoutes().entrySet()) {
                BasicRoute route = entry.getValue();
                for (Map.Entry<Date, Day> entryD : route.getDays().entrySet()) {
                    System.out.println("RouteNumber:" + route.getNumber() + " Date:" + entryD.getValue().getDateStamp());
                }
            }
            System.out.println("-------block end---------");
        }
        return routesBlocksArray;
    }
}

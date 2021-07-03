package Controller;

import Model.Day;
import Model.Route;
import Model.RoutesBlock;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class RoutesBlocksBuilder {

    public ArrayList<RoutesBlock> createRoutesBlocks(String routeDates) {
        String[] routeDatesArray = routeDates.split(",");
        ArrayList<RoutesBlock> routesBlocksArray = new ArrayList();
        RoutesBlock routesBlock = new RoutesBlock(50);
        for (String routeDate : routeDatesArray) {
            if (!routeDate.equals("")) {
                if (routesBlock.isFull()) {
                    Route lastRoute = routesBlock.removeLastRoute();
                    routesBlocksArray.add(routesBlock);
                    routesBlock = new RoutesBlock(50);
                    routesBlock.addRoute(lastRoute);
                    routesBlock.addRouteDate(routeDate);
                } else {
                    routesBlock.addRouteDate(routeDate);
                }
            }
        }
        routesBlocksArray.add(routesBlock);

        for (RoutesBlock rb : routesBlocksArray) {
            for (Map.Entry<Float, Route> entry : rb.getRoutes().entrySet()) {
                Route route = entry.getValue();
                for (Map.Entry<Date, Day> entryD : route.getDays().entrySet()) {
                    System.out.println("RouteNumber:" + route.getNumber() + " Date:" + entryD.getValue().getDateStamp());
                }
            }
            System.out.println("-------block end---------");
        }
        return routesBlocksArray;
    }
}

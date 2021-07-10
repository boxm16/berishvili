package Controller;

import Model.BasicRoute;
import Model.Day;
import Model.RoutesBlock;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class RoutesBlocksBuilder {

    public ArrayList<RoutesBlock> createRoutesBlocks(String routeDates) {
        int routeBlockMaxSize = 5;
        String[] routeDatesArray = routeDates.split(",");
        ArrayList<RoutesBlock> routesBlocksArray = new ArrayList();
        RoutesBlock routesBlock = new RoutesBlock(routeBlockMaxSize);
        for (String routeDate : routeDatesArray) {
            if (!routeDate.equals("")) {
                if (routesBlock.isFull()) {
                    if (routesBlock.getRoutes().size() > 1) {
                        BasicRoute lastRoute = routesBlock.removeLastRoute();
                        routesBlocksArray.add(routesBlock);
                        routesBlock = new RoutesBlock(routeBlockMaxSize);
                        routesBlock.addRoute(lastRoute);
                        routesBlock.addRouteDate(routeDate);
                    } else {
                        routesBlocksArray.add(routesBlock);
                        routesBlock = new RoutesBlock(routeBlockMaxSize);
                        routesBlock.addRouteDate(routeDate);
                    }
                } else {
                    routesBlock.addRouteDate(routeDate);
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

package Controller;

public class RouteNumberConverter {

    public Double convertRouteNumber(String routeNumber) {
        if (routeNumber.contains("-")) {
            routeNumber.replace("-", ".");
            return Double.valueOf(routeNumber);

        } else {
            return Double.valueOf(routeNumber);
        }
    }

    public String convertRouteNumber(Double routeNumber) {
        if (routeNumber % 1 == 0) {
            int routeNumberInt = (int) Math.round(routeNumber);
            return String.valueOf(routeNumberInt);
        } else {
            String routeNumberStr = String.valueOf(routeNumber);
            return routeNumberStr.replace(".", "-");
        }
    }
}

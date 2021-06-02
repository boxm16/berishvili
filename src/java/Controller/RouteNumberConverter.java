package Controller;

public class RouteNumberConverter {

    public float convertRouteNumber(String routeNumber) {
        if (routeNumber.contains("-")) {
           

            routeNumber = routeNumber.replace("-", ".");
            return Float.parseFloat(routeNumber);

        } else {
            return Float.parseFloat(routeNumber);
        }
    }

    public String convertRouteNumber(float routeNumber) {
        if (routeNumber % 1 == 0) {
            int routeNumberInt = (int) Math.round(routeNumber);
            return String.valueOf(routeNumberInt);
        } else {
            String routeNumberStr = String.valueOf(routeNumber);
            return routeNumberStr.replace(".", "-");
        }
    }
}

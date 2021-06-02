package Controller;

import Model.Route;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExcelController {

    private Converter converter;

    public ExcelController() {
        this.converter = new Converter();
    }

    @RequestMapping(value = "readUploadedExcelFile")//request name will be changed
    public String getRoutesFromUploadedFile(ModelMap model) {
        Instant inst1 = Instant.now();

        BasementController basementController = new BasementController();
        String filePath = basementController.getBasementDirectory() + "/uploads/uploadedExcelFile.xlsx";
        HashMap<String, String> data = new HashMap();
        TreeMap<Float, Route> routes = new TreeMap();
        try {
            ExcelReader excelReader = new ExcelReader();
            data = excelReader.getCellsFromExcelFile(filePath);
            routes = convertExcelDataToRoutes(data);
        } catch (Exception ex) {
            Logger.getLogger(ExcelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int rowIndex = 10;

        while (rowIndex < 60000) {
            String key = "H" + String.valueOf(rowIndex);

            rowIndex++;
        }

        Instant inst2 = Instant.now();
        System.out.println("Time needed for reading excel file and making routes for it: " + Duration.between(inst1, inst2).toString());
        model.addAttribute("data", data);//will be deleted later
        model.addAttribute("routes", routes);
        return "readSuccess";
    }

    private TreeMap<Float, Route> convertExcelDataToRoutes(HashMap<String, String> data) {
        TreeMap<Float, Route> routes = new TreeMap<>();

        int rowIndex = 8;
        while (!data.isEmpty()) {
            String routeNumberLocationInTheRow = new StringBuilder("H").append(String.valueOf(rowIndex)).toString();
            String routeNumberString = data.remove(routeNumberLocationInTheRow);//at the same time reading and removing the cell from hash Map
            if (routeNumberString == null) {//in theory this means that you reached the end of rows with data
                break;
            }
            Float routeNumberFloat = this.converter.convertRouteNumber(routeNumberString);
            if (routes.containsKey(routeNumberFloat)) {
                //add row items to existing route
                Route route = routes.get(routeNumberFloat);
                route = addRowElementsToRoute(route, data, rowIndex);
                route.setNumber(routeNumberString);
                routes.put(routeNumberFloat, route);

            } else {
                //create new route and insert into it items of the row, and add to routes
                Route route = new Route();
                route = addRowElementsToRoute(route, data, rowIndex);
                route.setNumber(routeNumberString);
                routes.put(routeNumberFloat, route);

            }
            rowIndex++;
        }

        return routes;
    }

    private Route addRowElementsToRoute(Route route, HashMap<String, String> data, int rowIndex) {
        //----first baseNumber
        String baseNumberLocationInTheRow = new StringBuilder("A").append(String.valueOf(rowIndex)).toString();
        short baseNumber = Short.valueOf(data.remove(baseNumberLocationInTheRow));
        route.setBaseNumber(baseNumber);
        //now see the date
        String dateStampLocationInTheRow = new StringBuilder("F").append(String.valueOf(rowIndex)).toString();
        String dateStamp = data.remove(dateStampLocationInTheRow);

        return route;
    }

}

package Controller;

import Model.Route;
import java.time.Duration;
import java.time.Instant;
import java.util.TreeMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExcelController {

    private MemoryUsage mu;

    public ExcelController() {
        this.mu = new MemoryUsage();
    }

    @RequestMapping(value = "readUploadedExcelFile")//request name will be changed
    public String getRoutesFromUploadedFile(ModelMap model) {
        Instant inst1 = Instant.now();

        TreeMap<Float, Route> routes = new TreeMap();

        System.out.println();
        System.out.println("----- MEMORY USAGE ---BEFORE----  FILE READING---------");
        mu.showMemoryUsage();
        System.out.println("----END OF MEMORY USAGE ---BEFORE----  FILE READING---------");
        RouteFactory routeFactory = new RouteFactory();
        routes = routeFactory.createRoutesFromUploadedFile();
        //tring to deallocate memmory
        /* for (Iterator<Map.Entry<String, String>> it = data.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = it.next();
            entry = null;
        }
        System.out.println("DATA size:" + data.size());
        data = null;*/
        //end of triaing
        Instant inst2 = Instant.now();
        System.out.println();
        System.out.println("Time needed for reading excel file and making routes for it: " + Duration.between(inst1, inst2).toString());
        System.out.println();
        model.addAttribute("routes", routes);
        System.out.println();
        System.out.println("++++++++++++++++ MEMORY USAGE AFTER FILE READING+++++++++++");
        mu.showMemoryUsage();
        System.out.println("++++++++++++++++END OF MEMORY USAGE AFTER FILE READING+++++++++++");

        return "readSuccess";
    }

    //------------writing--------
    @RequestMapping(value = "writeExcelFile")
    public String writeExcelFile() {
        ExcelWriter excelWriter = new ExcelWriter();
        RouteFactory routeFactory = new RouteFactory();
        TreeMap<Float, Route> routes = routeFactory.createRoutesFromUploadedFile();
        excelWriter.write(routes);
        return "index";
    }
    
  

}

package Controller;

import Model.Route;
import Model.RoutesBlock;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExcelFormController {

    @RequestMapping(value = "excelFormInitialRequest")
    public String goToExcelFormPage(@RequestParam("routes:dates") String routeDates, @RequestParam("blockNumber") String routeNumber, ModelMap model, HttpSession session) {
        RoutesBlocksBuilder rbb = new RoutesBlocksBuilder();
        ArrayList<RoutesBlock> selectedRoutesBlocks = rbb.createRoutesBlocks(routeDates);
        session.setAttribute("selectedRoutesBlocks", selectedRoutesBlocks);
        TreeMap<Float, Route> routes = getSelectedBlockRoutes(session, "0");
        model.addAttribute("routes", routes);
        model.addAttribute("selectedRoutesBlocks", selectedRoutesBlocks);

        return "excelForm";
    }

    @RequestMapping(value = "excelForm")
    public String dispalySelectedBlock(ModelMap model, HttpSession session, @RequestParam String blockIndex) {
        if (session.getAttribute("selectedRoutesBlocks") == null) {
            return "errorPage";
        }

        TreeMap<Float, Route> routes = getSelectedBlockRoutes(session, blockIndex);
        model.addAttribute("routes", routes);
        return "excelForm";
    }

    private TreeMap<Float, Route> getSelectedBlockRoutes(HttpSession session, String blockIndex) {
        ArrayList<RoutesBlock> selectedRoutesBlocks = (ArrayList<RoutesBlock>) session.getAttribute("selectedRoutesBlocks");
        RoutesBlock block = selectedRoutesBlocks.get(Integer.valueOf(blockIndex));
    
        RouteFactory routeFactory = new RouteFactory();
        TreeMap<Float, Route> routes = routeFactory.createSelectedRoutesFromUploadedFile(block);
        return routes;
    }
}

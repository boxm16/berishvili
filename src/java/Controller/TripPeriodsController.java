package Controller;

import DAO.RouteDao;
import Model.BasicRoute;
import Model.RoutesBlock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TripPeriodsController {

    @Autowired
    private RouteDao routeDao;

    @RequestMapping(value = "tripPeriodsInitialRequest")
    public String goToTripPeriodsPage(@RequestParam("routes:dates") String routeDates, @RequestParam("blockNumber") String routeNumber, ModelMap model, HttpSession session) {
        RoutesBlocksBuilder rbb = new RoutesBlocksBuilder();
        ArrayList<RoutesBlock> selectedRoutesBlocks = rbb.createRoutesBlocks(routeDates);
        session.setAttribute("selectedRoutesBlocks", selectedRoutesBlocks);
        TreeMap<Float, BasicRoute> routes = getSelectedBlockRoutes(session, "0");
        model.addAttribute("routes", routes);
        model.addAttribute("selectedRoutesBlocks", selectedRoutesBlocks);

        String previousBlockHtml = "";
        String currentBlockHtml = "<button type=\"button\" class=\"btn  btn-outline-primary\" disabled>"
                + " <span>" + selectedRoutesBlocks.get(0).getName() + "</span>"
                + " </button>";

        String nextBlockHtml = "";

        if (selectedRoutesBlocks.get(1) != null) {
            nextBlockHtml = " <button type=\"button\" class=\"btn btn-outline-success\">"
                    + " <span> <a  href=\"tripPeriods.htm?blockIndex=1\">" + selectedRoutesBlocks.get(1).getName() + "</a></span>"
                    + " </button>";
        }
        model.addAttribute("previousBlock", previousBlockHtml);
        model.addAttribute("currentBlock", currentBlockHtml);
        model.addAttribute("nextBlock", nextBlockHtml);
        return "tripPeriods";
    }

    @RequestMapping(value = "tripPeriods")
    public String dispalySelectedBlock(ModelMap model, HttpSession session, @RequestParam String blockIndex) {
        if (session.getAttribute("selectedRoutesBlocks") == null) {
            return "errorPage";
        }
        ArrayList<RoutesBlock> selectedRoutesBlocks = (ArrayList<RoutesBlock>) session.getAttribute("selectedRoutesBlocks");

        TreeMap<Float, BasicRoute> routes = getSelectedBlockRoutes(session, blockIndex);
        model.addAttribute("routes", routes);

        String previousBlockHtml = "";
        String currentBlockHtml = "<button type=\"button\" class=\"btn btn-outline-primary\" disabled>"
                + " <span>" + selectedRoutesBlocks.get(Integer.valueOf(blockIndex)).getName() + "</span>"
                + " </button>";
        String nextBlockHtml = "";

        if (Integer.valueOf(blockIndex) > 0) {
            previousBlockHtml = " <button type=\"button\" class=\"btn btn-outline-success\">"
                    + " <span> <a href=\"tripPeriods.htm?blockIndex=" + (Integer.valueOf(blockIndex) - 1) + "\">" + selectedRoutesBlocks.get(Integer.valueOf(blockIndex) - 1).getName() + "</a> </span>"
                    + " </button>";
        }

        if (Integer.valueOf(blockIndex) < selectedRoutesBlocks.size() - 1) {
            nextBlockHtml = " <button type=\"button\" class=\"btn btn-outline-success\">"
                    + "<span><a  href=\"tripPeriods.htm?blockIndex=" + (Integer.valueOf(blockIndex) + 1) + "\">" + selectedRoutesBlocks.get(Integer.valueOf(blockIndex) + 1).getName() + "</a></span>"
                    + " </button>";
        }

        model.addAttribute("previousBlock", previousBlockHtml);
        model.addAttribute("currentBlock", currentBlockHtml);
        model.addAttribute("nextBlock", nextBlockHtml);

        return "tripPeriods";
    }

    private TreeMap<Float, BasicRoute> getSelectedBlockRoutes(HttpSession session, String blockIndex) {
        Instant start = Instant.now();
        ArrayList<RoutesBlock> selectedRoutesBlocks = (ArrayList<RoutesBlock>) session.getAttribute("selectedRoutesBlocks");
        RoutesBlock block = selectedRoutesBlocks.get(Integer.valueOf(blockIndex));

        //RouteFactory routeFactory = new RouteFactory();
        //TreeMap<Float, BasicRoute> routes = routeFactory.createSelectedRoutesFromUploadedFile(block);
        TreeMap<Float, BasicRoute> routes = routeDao.getSelectedRoutes(block);

        Instant end = Instant.now();
        System.out.println("TripPeriods routes created. Creation time:" + Duration.between(start, end));
        return routes;
    }
}

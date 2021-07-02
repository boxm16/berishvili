package Controller;

import DAO.RouteDao;
import Model.RouteData;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteController {

    @Autowired
    private RouteDao routeDao;

    @RequestMapping(value = "routes")
    public String goToRoutesPage(ModelMap model) {
        TreeMap<Float, RouteData> routes = routeDao.getRoutesDataFromDB();
        model.addAttribute("routes", routes);
        return "routes";
    }

    @RequestMapping(value = "saveRoute", method = RequestMethod.POST)
    public String saveRoute(ModelMap model, @RequestParam String routeNumber, @RequestParam String aPoint, @RequestParam String bPoint, @RequestParam String scheme) {
        RouteData route=new RouteData();
        route.setNumber(routeNumber);
        route.setaPoint(aPoint);
        route.setbPoint(bPoint);
        route.setScheme(scheme);
        routeDao.updateRoute(route);
        return "redirect:/routes.htm";
    }

}

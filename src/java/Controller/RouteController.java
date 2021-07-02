package Controller;

import DAO.RouteDao;
import Model.RouteData;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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

}

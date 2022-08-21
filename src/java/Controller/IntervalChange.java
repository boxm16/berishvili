package Controller;

import graphical.RouteData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IntervalChange {

    @RequestMapping(value = "intervalChangeInitialRequest", method = RequestMethod.GET)
    public String intervalChangeInitialRequest(ModelMap model) {

        RouteData routeData = new RouteData();
        model.addAttribute("routeData", routeData);

        return "intervalChangeDashboard";
    }
}

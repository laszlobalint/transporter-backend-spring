package transporter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import transporter.services.PassengerService;

@Controller
public class PassengerController {

    private PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @RequestMapping(value = "/passenger", method = RequestMethod.POST)
    public ModelAndView savePassenger() {
        return new ModelAndView("", "passenger", null);
    }

    @RequestMapping(value = "/passenger", method = RequestMethod.GET)
    public ModelAndView listPassenger() {
        return new ModelAndView("", "passenger", null);
    }

    @RequestMapping(value = "/passenger/all", method = RequestMethod.GET)
    public ModelAndView listAllPassengers() {
        return new ModelAndView("index", "passengers", passengerService.listAllPassengers());
    }

    @RequestMapping(value = "/passenger", method = RequestMethod.PUT)
    public ModelAndView modifyPassenger() {
        return new ModelAndView("", "passenger", null);
    }

    @RequestMapping(value = "/passenger", method = RequestMethod.DELETE)
    public ModelAndView removePassenger() {
        return new ModelAndView("", "passenger", null);
    }
}

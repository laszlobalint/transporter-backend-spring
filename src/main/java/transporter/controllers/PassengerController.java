package transporter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import transporter.services.PassengerService;

@Controller
public class PassengerController {

    private PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @RequestMapping("/")
    public ModelAndView listPassgengers() {
        return new ModelAndView("index", "passengers", passengerService.listPassengers());
    }
}

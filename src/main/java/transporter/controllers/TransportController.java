package transporter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import transporter.services.TransportService;

@Controller
public class TransportController {

    private TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @RequestMapping(value = "/transport", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    public ModelAndView saveTransport() {
        return new ModelAndView("", "passenger", null);
    }

    @RequestMapping(value = "/transport", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public ModelAndView listTransport() {
        return new ModelAndView("", "passenger", null);
    }

    @RequestMapping(value = "/transport/all", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public ModelAndView listAllTransports() {
        return new ModelAndView("index", "passengers", null);
    }

    @RequestMapping(value = "/transport", method = RequestMethod.PUT)
    @CrossOrigin(origins = "http://localhost:4200")
    public ModelAndView modifyTransport() {
        return new ModelAndView("", "passenger", null);
    }

    @RequestMapping(value = "/transport", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "http://localhost:4200")
    public ModelAndView removeTransport() {
        return new ModelAndView("", "passenger", null);
    }
}

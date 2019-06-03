package transporter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import transporter.entities.Transport;
import transporter.services.TransportService;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/transport")
@CrossOrigin(origins = "http://localhost:4200")
public class TransportController {

    private TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Model saveTransport(@Valid Transport transport, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Hibás adatokat adtál meg az utazásnál!");
        } else {
            transportService.saveTransport(transport);
            model.addAttribute("message", "Sikeresen mentetted az utazást!");
        }
        return model;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Transport listTransport() {
        Long id = null;
        return transportService.listTransport(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Transport> listAllTransports() {
        return transportService.listAllTransport();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Model modifyTransport(@ModelAttribute Transport transport, Model model) {
        Long id = null;
        transportService.modifyTransport(transport.getFreeSeats(), id);
        model.addAttribute("message", "Sikeresen módosítottad az utazást!");
        return model;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Model removeTransport(Model model) {
        Long id = null;
        transportService.removeTransport(id);
        model.addAttribute("message", "Sikeresen törölted az utazást!");
        return model;
    }
}

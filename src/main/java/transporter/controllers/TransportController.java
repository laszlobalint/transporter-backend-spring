package transporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import transporter.entities.Transport;
import transporter.services.TransportService;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/transport")
public class TransportController {

    @Autowired
    private TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Transport listTransport(@PathVariable(value = "id") Long id) {
        return transportService.listTransport(id);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Transport> listAllTransports() {
        return transportService.listAllTransport();
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Model modifyTransport(@ModelAttribute Transport transport, Model model) {
        Long id = null;
        transportService.modifyTransport(transport.getFreeSeats(), id);
        model.addAttribute("message", "Sikeresen módosítottad az utazást!");
        return model;
    }

    @DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Model removeTransport(Model model) {
        Long id = null;
        transportService.removeTransport(id);
        model.addAttribute("message", "Sikeresen törölted az utazást!");
        return model;
    }
}
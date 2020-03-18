package transporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import transporter.authorizations.AuthService;
import transporter.entities.Transport;
import transporter.services.TransportService;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/transport")
public class TransportController {

    @Autowired
    private TransportService transportService;
    @Autowired
    private AuthService authService;
    @Autowired
    private Environment environment;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Object> saveTransport(@RequestBody Transport body, HttpServletRequest request) {
        if (authService.validateToken(request) &&
                authService.resolveToken(request).getIssuer().equals(environment.getProperty("adminEmail"))) {
            Transport t = new Transport(body.getRoute(), LocalDateTime.parse(body.getDepartureTimeString()), null);
            transportService.saveTransport(t);
            return ResponseEntity.status(200).body("Sikeresen meghirdetted az új fuvart!");
        }
        return ResponseEntity.status(400).body("Az új fuvar meghirdetése sikertelen!");
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
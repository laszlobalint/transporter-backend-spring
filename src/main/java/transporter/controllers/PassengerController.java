package transporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import transporter.auth.AuthService;
import transporter.entities.Passenger;
import transporter.services.PassengerService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/passenger")
@CrossOrigin(origins = "http://localhost:4200")
public class PassengerController {

    private PassengerService passengerService;

    @Autowired
    private AuthService authService;

    @Autowired
    private Environment environment;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping(produces = "application/json")
    @ResponseBody
    public ResponseEntity<Passenger> savePassenger(@RequestBody MultiValueMap<String, String> body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(400).body(null);
        } else {
            Passenger p = new Passenger(body.getFirst("name"), body.getFirst("password"),
                    body.getFirst("phoneNumber"), body.getFirst("email"),
                    Objects.requireNonNull(body.getFirst("picture")).getBytes());
            passengerService.savePassenger(p);
            return ResponseEntity.status(200).body(passengerService.listPassengerByEmail(body.getFirst("email")));
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Passenger> listPassenger(HttpServletRequest request) {
        Long id = Long.parseLong(authService.resolveToken(request).getSubject(), 10);
        if (authService.validateToken(request)) {
            return ResponseEntity.status(200).body(passengerService.listPassenger(id));
        } else  {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<Passenger>> listAllPassengers(@RequestParam MultiValueMap<String, String> body,
                                                             HttpServletRequest request) {
        if (authService.validateToken(request) &&
                authService.resolveToken(request).getIssuer().equals(environment.getProperty("adminEmail"))) {
            return ResponseEntity.status(200).body(passengerService.listAllPassengers());
        } else  {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<Passenger> modifyPassenger(@RequestParam MultiValueMap<String, String> body,
                                                     HttpServletRequest request) {
        Long id = Long.parseLong(authService.resolveToken(request).getSubject(), 10);
        Passenger p = new Passenger(body.getFirst("name"), body.getFirst("password"),
                body.getFirst("phoneNumber"), body.getFirst("email"),
                Objects.requireNonNull(body.getFirst("picture")).getBytes());
        passengerService.modifyPassenger(p, id);
        return ResponseEntity.status(200).body(passengerService.listPassenger(id));
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<String> removePassenger(HttpServletRequest request) {
        Long id = Long.parseLong(authService.resolveToken(request).getSubject(), 10);
        passengerService.removePassenger(id);
        return ResponseEntity.status(200).body("Sikeresen törölted a profilodat!");
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<String> loginPassenger(@RequestParam MultiValueMap<String, String> body) {
        return ResponseEntity.status(200).body(passengerService.loginPassenger(body.getFirst("email"),
                body.getFirst("plainPassword")));
    }
}

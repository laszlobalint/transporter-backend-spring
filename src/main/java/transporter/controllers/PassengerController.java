package transporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import transporter.authorizations.AuthService;
import transporter.entities.LoginPassenger;
import transporter.entities.Passenger;
import transporter.services.PassengerService;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/passenger")
public class PassengerController {

    private PassengerService passengerService;

    @Autowired
    private AuthService authService;

    @Autowired
    private Environment environment;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Object> savePassenger(@RequestBody Passenger body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(400).body(bindingResult.getAllErrors());
        } else if (passengerService.listPassengerByEmail(body.getEmail()) != null) {
            return ResponseEntity.status(409).body("Az e-mail cím már használatban van!");
        } else {
            Passenger p = new Passenger(body.getName(), body.getPassword(),
                    body.getPhoneNumber(), body.getEmail());
            passengerService.savePassenger(p);
            return ResponseEntity.status(200).body(passengerService.listPassengerByEmail(body.getEmail()));
        }
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity listPassenger(HttpServletRequest request) {
        Long id;
        if (authService.validateToken(request)) {
            id = Long.parseLong(authService.resolveToken(request).getSubject(), 10);
            if (id != null)
                return ResponseEntity.status(200).body(passengerService.listPassenger(id));
            else
                return ResponseEntity.status(401).body("Nincs felhasználó a megadott ID-val!");
        } else {
            return ResponseEntity.status(401).body("Nem kérhetőek le a felhasználó adatai!");
        }
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity listAllPassengers(@RequestParam MultiValueMap<String, String> body,
                                                             HttpServletRequest request) {
        if (authService.validateToken(request) &&
                authService.resolveToken(request).getIssuer().equals(environment.getProperty("adminEmail"))) {
            return ResponseEntity.status(200).body(passengerService.listAllPassengers());
        } else {
            return ResponseEntity.status(401).body("Adminisztrátori jogok szükségesek a kéréshez!");
        }
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Passenger> modifyPassenger(@RequestParam MultiValueMap<String, String> body,
                                                     HttpServletRequest request) {
        Long id = Long.parseLong(authService.resolveToken(request).getSubject(), 10);
        Passenger p = new Passenger(body.getFirst("name"), body.getFirst("password"),
                body.getFirst("phoneNumber"), body.getFirst("email"));
        passengerService.modifyPassenger(p, id);
        return ResponseEntity.status(200).body(passengerService.listPassenger(id));
    }

    @DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity removePassenger(HttpServletRequest request) {
        Long id;
        if (authService.validateToken(request)) {
            id = Long.parseLong(authService.resolveToken(request).getSubject(), 10);
            if (id != null && passengerService.listPassenger(id) != null) {
                passengerService.removePassenger(id);
                return ResponseEntity.status(200).body("Sikeresen törölted a profilodat!");
            }
        }
        return ResponseEntity.status(403).body("Nem törölhető a profil!");
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String> loginPassenger(@RequestBody LoginPassenger body) {
        if (passengerService.listPassengerByEmail(body.getEmail()) == null)
            return ResponseEntity.status(404).body("Az e-mail cím nincs használatban!");
        else
            return ResponseEntity.status(200).body(passengerService.loginPassenger(body.getEmail(),
                    body.getPlainPassword()));
    }
}

package transporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transporter.authorizations.AuthService;
import transporter.entities.LoginPassenger;
import transporter.entities.Passenger;
import transporter.services.PassengerService;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/passenger")
public class PassengerController {

    @Autowired
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
    public ResponseEntity<Object> savePassenger(@RequestBody Passenger body) {
        if (passengerService.listPassengerByEmail(body.getEmail()) != null)
            return ResponseEntity.status(409).body("Az e-mail cím már használatban van!");
        else {
            Passenger p = new Passenger(body.getName(), body.getPassword(),
                    body.getPhoneNumber(), body.getEmail());
            passengerService.savePassenger(p);
            if (passengerService.listPassengerByEmail(p.getEmail()) != null)
                return ResponseEntity.status(200).body(p.getName() + "sikeresen regisztrált!");
        }
        return ResponseEntity.status(400).body("Hiba lépett fel. Nem sikerült a regisztráció!");
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Object> listPassenger(HttpServletRequest request) {
        if (authService.validateToken(request)) {
            Long id = Long.parseLong(authService.resolveToken(request).getSubject(), 10);
            if (id != null)
                return ResponseEntity.status(200).body(passengerService.listPassenger(id));
            else
                return ResponseEntity.status(401).body("Nincs felhasználó a megadott ID-val!");
        } else {
            return ResponseEntity.status(403).body("Nem kérhetőek le a felhasználó adatai!");
        }
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity listAllPassengers(HttpServletRequest request) {
        if (authService.validateToken(request) &&
                authService.resolveToken(request).getIssuer().equals(environment.getProperty("adminEmail"))) {
            return ResponseEntity.status(200).body(passengerService.listAllPassengers());
        } else {
            return ResponseEntity.status(401).body("Adminisztrátori jogok szükségesek a kéréshez!");
        }
    }


    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Object> modifyPassenger(@RequestBody Passenger body,
                                                     HttpServletRequest request) {
        if (authService.validateToken(request)) {
            Long id = Long.parseLong(authService.resolveToken(request).getSubject(), 10);
            Passenger edited = new Passenger(body.getName(), body.getPassword(), body.getPhoneNumber(), body.getEmail());
            Passenger saved = passengerService.modifyPassenger(edited, id);
            if (saved != null)
                return ResponseEntity.status(200).body(passengerService.listPassenger(id));
            else
                return ResponseEntity.status(400).body("Nem sikerült a felhasználói adatokat frissíteni!");
        } else {
            return ResponseEntity.status(403).body("Nem változtathatóak meg a felhasználó adatai!");
        }
    }

    @DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Object> removePassenger(HttpServletRequest request) {
        if (authService.validateToken(request)) {
            Long id = Long.parseLong(authService.resolveToken(request).getSubject(), 10);
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

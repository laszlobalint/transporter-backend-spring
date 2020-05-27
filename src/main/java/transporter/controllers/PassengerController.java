package transporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transporter.authorizations.AuthService;
import transporter.dto.*;
import transporter.entities.Passenger;
import transporter.services.EmailService;
import transporter.services.PassengerService;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "https://transporter-srb-hun.herokuapp.com/passenger")
public class PassengerController {

    @Autowired
    private final PassengerService passengerService;
    @Autowired
    private AuthService authService;
    @Autowired
    EmailService emailService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Object> savePassenger(@RequestBody Register body) {
        if (passengerService.listPassengerByEmail(body.getEmail()) != null) {
            return ResponseEntity.status(409).body("Az e-mail cím már használatban van!");
        } else {
            Passenger p = new Passenger(body.getName(), body.getPassword(),
                    body.getPhoneNumber(), body.getEmail());
            passengerService.savePassenger(p);
            if (passengerService.listPassengerByEmail(body.getEmail()) != null) {
                return ResponseEntity.status(200).body(new Message(p.getName() + " sikeresen regisztrált!"));
            } else {
                return ResponseEntity.status(400).body("Hiba lépett fel. Nem sikerült regisztrálni!");
            }
        }
    }

    @GetMapping(value = "/{passengerId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Object> listPassenger(@PathVariable Long passengerId, HttpServletRequest request) {
        if (authService.validatePersonalRequestByPassenger(request, passengerId) || authService.validateAdmin(request))
            return ResponseEntity.status(200).body(passengerService.listPassenger(passengerId));
        else
            return ResponseEntity.status(403).body("Nem kérhetőek le a felhasználó adatai!");
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity listAllPassengers(HttpServletRequest request) {
        if (authService.validateAdmin(request))
            return ResponseEntity.status(200).body(passengerService.listAllPassengers());
        else
            return ResponseEntity.status(401).body("Adminisztrátori jogok szükségesek a kéréshez!");
    }

    @PutMapping(value = "/{passengerId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Object> modifyPassenger(@PathVariable Long passengerId, @RequestBody UpdatePassenger body, HttpServletRequest request) {
        if (authService.validatePersonalRequestByPassenger(request, passengerId) || authService.validateAdmin(request)) {
            return ResponseEntity.status(200).body(passengerService.modifyPassenger(body.getName(), body.getPassword(), body.getEmail(), body.getPhoneNumber(), passengerId));
        } else {
            return ResponseEntity.status(403).body("Nem változtathatók meg a felhasználó adatai!");
        }
    }

    @DeleteMapping(value = "/{passengerId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Object> removePassenger(@PathVariable Long passengerId, HttpServletRequest request) {
        if (authService.validatePersonalRequestByPassenger(request, passengerId) || authService.validateAdmin(request)) {
            if (passengerService.listPassenger(passengerId) != null) {
                passengerService.removePassenger(passengerId);
                return ResponseEntity.status(200).body(new Message("Sikeresen törölted a profilodat!"));
            }
        }
        return ResponseEntity.status(403).body("Nem törölhető a profil!");
    }

    @PostMapping(value = "/contact", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity contactAdmin(@RequestBody ContactHelp body) {
        emailService.sendMail(System.getenv("ADMIN_MAIL"), body.getSubject(), "E-mail feladója: " + body.getEmail() + "\n" + body.getMessage());
        return ResponseEntity.status(200).body(new Message("Sikeresen elküldted az e-mailt!"));
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity loginPassenger(@RequestBody Login body) {
        if (passengerService.listPassengerByEmail(body.getEmail()) == null)
            return ResponseEntity.status(404).body("Az e-mail cím nincs használatban!");
        else
            return passengerService.loginPassenger(body.getEmail(),
                    body.getPlainPassword());
    }
}
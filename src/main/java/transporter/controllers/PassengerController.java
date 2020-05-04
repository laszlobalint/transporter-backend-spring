package transporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transporter.authorizations.AuthService;
import transporter.entities.Login;
import transporter.entities.Passenger;
import transporter.entities.Register;
import transporter.services.PassengerService;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;
    @Autowired
    private AuthService authService;

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
                return ResponseEntity.status(200).body(p.getName() + " sikeresen regisztrált!");
            } else {
                return ResponseEntity.status(400).body("Hiba lépett fel. Nem sikerült regisztrálni!");
            }
        }
    }

    @GetMapping(value = "/{passengerId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Object> listPassenger(@PathVariable Long passengerId, HttpServletRequest request) {
        if (authService.validatePersonalRequest(request, passengerId) || authService.validateAdmin(request))
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
    public ResponseEntity<Object> modifyPassenger(@PathVariable Long passengerId, @RequestBody Passenger body, HttpServletRequest request) {
        if (authService.validatePersonalRequest(request, passengerId) || authService.validateAdmin(request)) {
            Passenger edited = new Passenger(body.getName(), body.getPassword(), body.getPhoneNumber(), body.getEmail());
            Passenger saved = passengerService.modifyPassenger(edited, passengerId);
            if (saved != null)
                return ResponseEntity.status(200).body(passengerService.listPassenger(passengerId));
            else
                return ResponseEntity.status(400).body("Nem sikerült a felhasználói adatokat frissíteni!");
        } else {
            return ResponseEntity.status(403).body("Nem változtathatók meg a felhasználó adatai!");
        }
    }

    @DeleteMapping(value = "/{passengerId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Object> removePassenger(@PathVariable Long passengerId, HttpServletRequest request) {
        if (authService.validatePersonalRequest(request, passengerId) || authService.validateAdmin(request)) {
            if (passengerService.listPassenger(passengerId) != null) {
                passengerService.removePassenger(passengerId);
                return ResponseEntity.status(200).body("Sikeresen törölted a profilodat!");
            }
        }
        return ResponseEntity.status(403).body("Nem törölhető a profil!");
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
package transporter.controllers;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import transporter.entities.Passenger;
import transporter.services.PassengerService;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/passenger")
@CrossOrigin(origins = "http://localhost:4200")
public class PassengerController {

    private PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public Model savePassenger(@Valid Passenger passenger, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Hibás adatokat adtál meg a profilodnál!");
        } else {
            passengerService.savePassenger(passenger);
            model.addAttribute("message", "Sikeresen mentetted a profilodat!");
        }
        return model;
    }

    @GetMapping
    public Passenger listPassenger() {
        Long id = null;
        return passengerService.listPassenger(id);
    }

    @GetMapping("/all")
    public List<Passenger> listAllPassengers() { return passengerService.listAllPassengers(); }

    @PutMapping
    public Model modifyPassenger(@ModelAttribute Passenger passenger, Model model) {
        Long id = null;
        passengerService.modifyPassenger(passenger, id);
        model.addAttribute("message", "Sikeresen módosítottad a profilodat!");
        return model;
    }

    @DeleteMapping
    public Model removePassenger(Model model) {
        Long id = null;
        passengerService.removePassenger(id);
        model.addAttribute("message", "Sikeresen törölted a foglalásodat!");
        return model;
    }
}

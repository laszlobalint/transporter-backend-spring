package transporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import transporter.authorizations.AuthService;
import transporter.entities.Booking;
import transporter.services.BookingService;
import transporter.services.PassengerService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private AuthService authService;
    @Autowired
    private Environment environment;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> saveBooking(@RequestBody Booking body, HttpServletRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(400).body(bindingResult.getAllErrors());
        } else {
            if (authService.validateToken(request)) {
                Long id = Long.parseLong(authService.resolveToken(request).getSubject(), 10);
                Booking b = new Booking(LocalDateTime.parse(body.getDepartureTimeString()), body.getLocationHungary(), body.getLocationSerbia());
                b.setPassenger(passengerService.listPassenger(id));
                bookingService.saveBooking(b);
                return ResponseEntity.status(200).body(passengerService.listPassenger(id));
            } else
                return ResponseEntity.status(400).body("Nem sikerült lefoglalni a fuvart!");
        }
    }

    @GetMapping(value = "/{bookingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object listBooking(@PathVariable Long bookingId) {
        return bookingService.listBooking(bookingId);
    }

    @GetMapping(value = "/bookings/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object listAllBookings(HttpServletRequest request) {
        if (authService.validateToken(request) &&
                authService.resolveToken(request).getIssuer().equals(environment.getProperty("adminEmail")))
            return bookingService.listAllBookings();
        else
            return ResponseEntity.status(400).body("Nem sikerült lekérdezni az összes foglalást!");
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Model modifyBooking(@ModelAttribute Booking booking, Model model) {
        Long id = null;
        bookingService.modifyBooking(booking.getLocationSerbia(), booking.getLocationHungary(), id);
        model.addAttribute("message", "Sikeresen módosítottad a foglalásodat!");
        return model;
    }

    @DeleteMapping(value = "/{bookingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object removeBooking(@RequestBody Booking body, @PathVariable Long bookingId, HttpServletRequest request) {
        Long id = Long.parseLong(authService.resolveToken(request).getSubject(), 10);
        Booking b = bookingService.listBooking(bookingId);
        if (b.getPassenger().getId().equals(id)) {
            bookingService.removeBooking(bookingId);
            if (bookingService.listBooking(bookingId) == null)
                return ResponseEntity.status(200).body("Sikeresen törölted a foglalásodat!");
        }
        return ResponseEntity.status(400).body("Nem sikerült törölni a foglalást!");
    }
}
package transporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transporter.authorizations.AuthService;
import transporter.entities.Booking;
import transporter.entities.Transport;
import transporter.services.BookingService;
import transporter.services.PassengerService;
import transporter.services.TransportService;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@RestController
@RequestMapping(value = "/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private TransportService transportService;
    @Autowired
    private AuthService authService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> saveBooking(@RequestBody Booking body, HttpServletRequest request) {
        if (authService.validateToken(request)) {
            Long id = Long.parseLong(authService.resolveToken(request).getSubject(), 10);
            Transport t = transportService.listTransport(body.getTransport().getId());
            Booking b = new Booking(t.getDepartureTime(), body.getLocationSerbia(), body.getLocationHungary());
            b.setPassenger(passengerService.listPassenger(id));
            bookingService.saveBooking(b);
            return ResponseEntity.status(200).body("Sikeresen lefoglaltad a helyet a fuvarra!");
        }
        return ResponseEntity.status(400).body("Nem sikerült lefoglalni a fuvart!");
    }

    @GetMapping(value = "/{bookingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> listBooking(@PathVariable Long bookingId, HttpServletRequest request) {
        if (authService.validateToken(request) && bookingService.listBooking(bookingId) != null)
            return ResponseEntity.status(200).body(bookingService.listBooking(bookingId));
        else
            return ResponseEntity.status(400).body("Nem kérhető le a megadott foglalás!");
    }

    @GetMapping(value = "/bookings/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAllBookings(HttpServletRequest request) {
        if (authService.validateAdmin(request))
            return ResponseEntity.status(200).body(bookingService.listAllBookings());
        else
            return ResponseEntity.status(403).body("Nem lehet lekérdezni az összes foglalást!");
    }

    @PutMapping(value = "/{bookingId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity modifyBooking(@RequestBody Booking body, @PathVariable Long bookingId, HttpServletRequest request) {
        if (authService.validatePersonalRequest(request, bookingId)) {
            bookingService.modifyBooking(body.getLocationSerbia(), body.getLocationHungary(), bookingId);
            if (bookingService.listBooking(bookingId).getLocationHungary() == body.getLocationHungary() &&
                    bookingService.listBooking(bookingId).getLocationSerbia() == body.getLocationSerbia())
                return ResponseEntity.status(200).body("Sikeresen módosítottad a foglalásodat!");
        }
        return ResponseEntity.status(400).body("Nem sikerült módosítani a foglalást!");
    }

    @DeleteMapping(value = "/{bookingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> removeBooking(@PathVariable Long bookingId, HttpServletRequest request) {
        if (authService.validatePersonalRequest(request, bookingId)) {
            bookingService.removeBooking(bookingId);
            if (bookingService.listBooking(bookingId) == null)
                return ResponseEntity.status(200).body("Sikeresen törölted a foglalásodat!");
        }
        return ResponseEntity.status(400).body("Nem sikerült törölni a foglalást!");
    }
}
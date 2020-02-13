package transporter.controllers;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import transporter.entities.Booking;
import transporter.services.BookingService;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/booking")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Model saveBooking(@Valid Booking booking, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Hibás adatokat adtál meg a foglaláshoz!");
        } else {
            bookingService.saveBooking(booking);
            model.addAttribute("message", "Sikeresen mentetted a foglalásodat!");
        }
        return model;
    }

    @GetMapping
    public Booking listBooking(@PathVariable(value = "id") Long id) {
        return bookingService.listBooking(id);
    }

    @GetMapping("/all")
    public List<Booking> listAllBookings() {
        return bookingService.listAllBookings();
    }

    @PutMapping
    public Model modifyBooking(@ModelAttribute Booking booking, Model model) {
        Long id = null;
        bookingService.modifyBooking(booking.getLocationSerbia(), booking.getLocationHungary(), id);
        model.addAttribute("message", "Sikeresen módosítottad a foglalásodat!");
        return model;
    }

    @DeleteMapping
    public Model removeBooking(Model model) {
        Long id = null;
        bookingService.removeBooking(id);
        model.addAttribute("message", "Sikeresen törölted a foglalásodat!");
        return model;
    }
}

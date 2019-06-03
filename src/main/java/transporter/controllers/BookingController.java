package transporter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import transporter.entities.Booking;
import transporter.services.BookingService;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Model saveBooking(@Valid Booking booking, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Hibás adatokat adtál meg a foglaláshoz!");
        } else {
            bookingService.saveBooking(booking);
            model.addAttribute("message", "Sikeresen mentetted a foglalásodat!");
        }
        return model;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Booking listBooking() {
        Long id = null;
        return bookingService.listBooking(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Booking> listAllBookings() {
        return bookingService.listAllBookings();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Model modifyBooking(@ModelAttribute Booking booking, Model model) {
        Long id = null;
        bookingService.modifyBooking(booking.getLocationSerbia(), booking.getLocationHungary(), id);
        model.addAttribute("message", "Sikeresen módosítottad a foglalásodat!");
        return model;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Model removeBooking(Model model) {
        Long id = null;
        bookingService.removeBooking(id);
        model.addAttribute("message", "Sikeresen törölted a foglalásodat!");
        return model;
    }
}

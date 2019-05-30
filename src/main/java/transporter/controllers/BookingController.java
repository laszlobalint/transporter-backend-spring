package transporter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import transporter.services.BookingService;

@Controller
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(value = "/booking", method = RequestMethod.POST)
    public ModelAndView saveBooking() {
        return new ModelAndView("", "passenger", null);
    }

    @RequestMapping(value = "/booking", method = RequestMethod.GET)
    public ModelAndView listBooking() {
        return new ModelAndView("", "passenger", null);
    }

    @RequestMapping(value = "/booking/all", method = RequestMethod.GET)
    public ModelAndView listAllBookings() {
        return new ModelAndView("index", "passengers", null);
    }

    @RequestMapping(value = "/booking", method = RequestMethod.PUT)
    public ModelAndView modifyBooking() {
        return new ModelAndView("", "passenger", null);
    }

    @RequestMapping(value = "/booking", method = RequestMethod.DELETE)
    public ModelAndView removeBooking() {
        return new ModelAndView("", "passenger", null);
    }
}

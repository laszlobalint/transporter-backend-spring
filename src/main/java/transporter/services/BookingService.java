package transporter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transporter.dao.BookingDAO;
import transporter.dao.TransportDAO;
import transporter.entities.Booking;
import transporter.entities.Transport;

@Service
public class BookingService {

    private BookingDAO bookingDAO;

    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Autowired
    private TransportDAO transportDAO;

    public  void saveBooking(Booking booking) {
        Transport t = transportDAO.findTransportByDepartureTime(booking.getDepartureTime());
        booking.setTransport(t);
        t.setFreeSeats(t.getFreeSeats() - 1);
        bookingDAO.saveBooking(booking, t);
    }
}

package transporter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transporter.dao.BookingDAO;
import transporter.dao.TransportDAO;
import transporter.entities.Booking;
import transporter.entities.Transport;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private BookingDAO bookingDAO;

    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Autowired
    private TransportDAO transportDAO;

    public void saveBooking(Booking booking) {
        Transport t = transportDAO.findTransportByDepartureTime(booking.getDepartureTime());
        if (t.getFreeSeats() > 0) {
            booking.setTransport(t);
            bookingDAO.saveBooking(booking, t);
            t.setFreeSeats(t.getFreeSeats() - 1);
            transportDAO.saveTransport(t);
        }
    }

    public Booking listBooking(Long id) {
        return bookingDAO.listBooking(id);
    }

    public List<Booking> listAllBookings() {
        return new ArrayList<>( bookingDAO.listAllBookings());
    }

    public void modifyBooking(Booking.LocationSerbia ls, Booking.LocationHungary lh, Long id) {
        Booking booking = bookingDAO.listBooking(id);
        booking.setLocationSerbia(ls);
        booking.setLocationHungary(lh);
        bookingDAO.modifyBooking(booking);
    }

    public void removeBooking(Long id) {
        Booking booking = bookingDAO.listBooking(id);
        Transport t = transportDAO.findTransportByDepartureTime(booking.getDepartureTime());
        bookingDAO.removeBooking(booking);
        t.setFreeSeats(t.getFreeSeats() + 1);
        transportDAO.saveTransport(t);
    }
}

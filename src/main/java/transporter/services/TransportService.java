package transporter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transporter.dao.BookingDAO;
import transporter.dao.TransportDAO;
import transporter.entities.Booking;
import transporter.entities.Transport;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportService {

    private TransportDAO transportDAO;

    public TransportService(TransportDAO transportDAO) {
        this.transportDAO = transportDAO;
    }

    @Autowired
    private BookingDAO bookingDAO;
    @Autowired
    private BookingService bookingService;

    public void saveTransport(Transport transport) {
        transportDAO.saveTransport(transport);
    }

    public Transport listTransport(Long id) {
        return transportDAO.listTransport(id);
    }

    public List<Transport> listAllTransport() {
        return transportDAO.listAllTransports()
                .stream()
                .filter(t -> t.getDepartureTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public void modifyTransport(int freeSeats, Long id) {
        Transport transport = transportDAO.listTransport(id);
        if (transport.getFreeSeats() != 0) transport.setFreeSeats(freeSeats);
        transportDAO.modifyTransport(transport);
    }

    public void removeTransport(Long id) {
        Transport transport = transportDAO.listTransport(id);
        List<Booking> removableBookings = bookingDAO.findBookingsByTransportId(id);
        if (removableBookings.size() > 0) {
            for (Booking b : removableBookings) {
                bookingService.removeBooking(b.getId());
            }
        }
        transportDAO.removeTransport(transport);
    }
}
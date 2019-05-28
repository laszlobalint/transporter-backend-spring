package transporter.services;

import org.springframework.stereotype.Service;
import transporter.dao.BookingDAO;

@Service
public class BookingService {

    private BookingDAO bookingDAO;

    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }
}

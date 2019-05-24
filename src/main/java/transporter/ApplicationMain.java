package transporter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import transporter.dao.BookingDAO;
import transporter.dao.PassengerDAO;
import transporter.dao.TransportDAO;
import transporter.entities.Booking;
import transporter.entities.Passenger;
import transporter.entities.Transport;
import java.time.LocalDateTime;

public class ApplicationMain {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(Configuration.class)) {

            TransportDAO transportDAO = context.getBean(TransportDAO.class);
            PassengerDAO passengerDAO = context.getBean(PassengerDAO.class);
            BookingDAO bookingDAO = context.getBean(BookingDAO.class);

            Transport transport = new Transport(LocalDateTime.of(2019, 5, 16, 20, 0), null);
            transportDAO.saveTransport(transport);

            Passenger passenger1 = new Passenger("Test Passenger", "+36-70-11111111", "test@test.com", null);
            passengerDAO.savePassenger(passenger1);

            Booking booking1 = new Booking(LocalDateTime.of(2019, 5, 16, 20, 0),
                    Booking.LocationHungary.GRINGOS_BUS_STOP, Booking.LocationSerbia.MARKET_LIDL);
            booking1.setPassenger(passengerDAO.findPassengerByName("Test Passenger"));
            bookingDAO.saveBooking(booking1);

            Passenger passenger2 = new Passenger("John Doe", "+36-70-22222222", "gmail@gmail.com", null);
            passengerDAO.savePassenger(passenger2);

            Booking booking2 = new Booking(LocalDateTime.of(2019, 5, 16, 20, 0),
                    Booking.LocationHungary.BAKERY_BUREK, Booking.LocationSerbia.NEW_CITY_HALL);
            booking2.setPassenger(passengerDAO.findPassengerByName("John Doe"));
            bookingDAO.saveBooking(booking2);
        }
    }
}

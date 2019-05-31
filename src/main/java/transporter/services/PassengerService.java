package transporter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transporter.dao.BookingDAO;
import transporter.dao.PassengerDAO;
import transporter.entities.Booking;
import transporter.entities.Passenger;
import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerService {

    private PassengerDAO passengerDAO;

    public PassengerService(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }

    @Autowired
    private BookingDAO bookingDAO;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PassengerService passengerService;

    public void savePassenger(Passenger passenger) {
        passengerDAO.savePassenger(passenger);
    }

    public Passenger listPassenger(Long id) {
        return passengerDAO.listPassenger(id);
    }

    public List<Passenger> listAllPassengers() {
        return new ArrayList<>(passengerDAO.listAllPassengers());
    }

    public void modifyPassenger(Passenger p, Long id) {
        Passenger passenger = passengerDAO.listPassenger(id);
        if (p.getName() != null) passenger.setName(p.getName());
        if (p.getEmail() != null) passenger.setEmail(p.getEmail());
        if (p.getPhoneNumber() != null) passenger.setPhoneNumber(p.getPhoneNumber());
        if (p.getPicture() != null) passenger.setPicture(p.getPicture());
        passengerDAO.modifyPassenger(passenger);
    }

    public void removePassenger(Long id) {
        List<Booking> removableBookings = bookingDAO.findBookingsByPassengerId(id);
        if (removableBookings.size() > 0) {
            for (Booking b : removableBookings) {
                bookingService.removeBooking(b.getId());
            }
        }
        passengerDAO.removePassenger(id);
    }
}

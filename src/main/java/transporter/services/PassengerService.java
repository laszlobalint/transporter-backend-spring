package transporter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import transporter.auth.AuthService;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BookingDAO bookingDAO;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private AuthService authService;

    public void savePassenger(Passenger passenger) {
        passenger.setPassword(passwordEncoder.encode(passenger.getPassword()));
        passengerDAO.savePassenger(passenger);
    }

    public Passenger listPassenger(Long id) {
        return passengerDAO.listPassenger(id);
    }

    public Passenger listPassengerByEmail(String email) {
        return passengerDAO.findPassengerByEmail(email);
    }

    public List<Passenger> listAllPassengers() {
        return new ArrayList<>(passengerDAO.listAllPassengers());
    }

    public void modifyPassenger(Passenger p, Long id) {
        Passenger passenger = passengerDAO.listPassenger(id);
        if (p.getName() != null) passenger.setName(p.getName());
        if (p.getPassword() != null) passenger.setPassword(passwordEncoder.encode(p.getPassword()));
        if (p.getPhoneNumber() != null) passenger.setPhoneNumber(p.getPhoneNumber());
        if (p.getEmail() != null) passenger.setEmail(p.getEmail());
        if (p.getPicture() != null) passenger.setPicture(p.getPicture());
        passengerDAO.modifyPassenger(passenger);
    }

    public void removePassenger(Long id) {
        List<Booking> removableBookings = bookingDAO.findBookingsByPassengerId(id);
        if (removableBookings.size() != 0) {
            removableBookings.forEach((b) -> {
                bookingService.removeBooking(b.getId());
            });
        }
        passengerDAO.removePassenger(id);
    }

    public String loginPassenger(String email, String plainPassword) {
        String encodedPassword = passengerDAO.findEncodedPasswordForPassengerByEmail(email);
        boolean isMatching = passwordEncoder.matches(plainPassword, encodedPassword);
        if (isMatching) {
            Passenger passenger = passengerDAO.findPassengerByEmail(email);
            return authService.createJWT(passenger);
        } else {
            return "Wrong user credentials!";
        }
    }
}

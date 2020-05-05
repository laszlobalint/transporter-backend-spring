package transporter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import transporter.authorizations.AuthService;
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

    public Passenger modifyPassenger(Passenger edited, Long id) {
        Passenger passenger = passengerDAO.listPassenger(id);
        if (edited.getName() != null && edited.getName().length() > 2) passenger.setName(edited.getName());
        if (edited.getPassword() != null && edited.getPassword().length() > 2)
            passenger.setPassword(passwordEncoder.encode(edited.getPassword()));
        if (edited.getPhoneNumber() != null && edited.getPhoneNumber().length() > 2)
            passenger.setPhoneNumber(edited.getPhoneNumber());
        if (edited.getEmail() != null && edited.getEmail().length() > 2) passenger.setEmail(edited.getEmail());
        return passengerDAO.modifyPassenger(passenger);
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

    public ResponseEntity loginPassenger(String email, String plainPassword) {
        String encodedPassword = passengerDAO.findEncodedPasswordForPassengerByEmail(email);
        boolean isMatching = passwordEncoder.matches(plainPassword, encodedPassword);
        if (isMatching) {
            Passenger passenger = passengerDAO.findPassengerByEmail(email);
            if (!passenger.isActivated()) return passengerDAO.activatePassengerProfile(passenger);
            else return ResponseEntity.status(200).body(authService.createJWT(passenger));
        } else {
            return ResponseEntity.status(422).body("Helytelen e-mail címet vagy jelszót adtál meg!");
        }
    }
}
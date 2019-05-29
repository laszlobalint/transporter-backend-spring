package transporter.services;

import org.springframework.stereotype.Service;
import transporter.dao.PassengerDAO;
import transporter.entities.Passenger;
import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerService {

    private PassengerDAO passengerDAO;

    public PassengerService(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }

    public List<Passenger> listPassengers() {
        return new ArrayList<>(passengerDAO.listAllPassengers());
    }
}

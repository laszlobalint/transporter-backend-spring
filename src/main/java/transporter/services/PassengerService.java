package transporter.services;

import org.springframework.stereotype.Service;
import transporter.dao.PassengerDAO;

@Service
public class PassengerService {

    private PassengerDAO passengerDAO;

    public PassengerService(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }
}

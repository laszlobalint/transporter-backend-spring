package transporter.services;

import org.springframework.stereotype.Service;
import transporter.dao.TransportDAO;

@Service
public class TransportService {

    private TransportDAO transportDAO;

    public TransportService(TransportDAO transportDAO) {
        this.transportDAO = transportDAO;
    }
}

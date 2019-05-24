package transporter.dao;

import org.springframework.stereotype.Repository;
import transporter.entities.Transport;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class TransportDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveTransport(Transport transport) {
        entityManager.merge(transport);
        entityManager.flush();
    }

    public Transport findTransportById(Long id) {
        return entityManager.find(Transport.class, id);
    }
}

package transporter.dao;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import transporter.entities.Transport;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TransportDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveTransport(Transport transport) {
        entityManager.merge(transport);
        entityManager.flush();
    }

    public Transport listTransport(Long id) {
        return entityManager.find(Transport.class, id);
    }

    public List<Transport> listAllTransports() {
        return entityManager.createQuery("SELECT t FROM Transport t ORDER by t.departureTime", Transport.class)
                .getResultList();
    }

    public Transport findTransportByDepartureTime(LocalDateTime time) {
        return entityManager.createQuery("SELECT t FROM Transport t WHERE t.departureTime = :time", Transport.class)
                .setParameter("time", time)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Transactional
    public void modifyTransport(Transport transport) {
        entityManager.merge(transport);
        entityManager.flush();
    }

    @Transactional
    public void removeTransport(Transport transport) {
        entityManager.remove(entityManager.contains(transport) ? transport : entityManager.merge(transport));
        entityManager.flush();
    }
}

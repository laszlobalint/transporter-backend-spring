package transporter.dao;

import org.springframework.stereotype.Repository;
import transporter.entities.Passenger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PassengerDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void savePassenger(Passenger passenger) {
        entityManager.persist(passenger);
        entityManager.flush();
    }

    public Passenger listPassenger(Long id) {
        return entityManager.find(Passenger.class, id);
    }

    public List<Passenger> listAllPassengers() {
        return entityManager.createQuery("SELECT p FROM Passenger p ORDER by p.name", Passenger.class)
                .getResultList();
    }

    @Transactional
    public void modifyPassenger(Passenger passenger) {
        entityManager.merge(passenger);
        entityManager.flush();
    }

    @Transactional
    public void removePassenger(Long id) {
        entityManager.remove(entityManager.getReference(Passenger.class, id));
        entityManager.flush();
    }
}

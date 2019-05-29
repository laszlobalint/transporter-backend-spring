package transporter.dao;

import org.springframework.stereotype.Repository;
import transporter.entities.Passenger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PassengerDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void savePassenger(Passenger passenger) {
        entityManager.persist(passenger);
    }

    @Transactional
    public void updatePassenger(Passenger p, Long id) {
        Passenger passenger = entityManager.getReference(Passenger.class, id);
        passenger.setName(p.getName());
        passenger.setEmail(p.getEmail());
        passenger.setPhoneNumber(p.getPhoneNumber());
        passenger.setPicture(p.getPicture());
    }

    @Transactional
    public void deletePassengerById(Long id) {
        entityManager.remove(entityManager.getReference(Passenger.class, id));
    }

    public Passenger findPassengerById(Long id) {
        return entityManager.find(Passenger.class, id);
    }

    public Passenger findPassengerByName(String name) {
        return entityManager.createQuery("SELECT p from Passenger p WHERE p.name = :name", Passenger.class)
                .setParameter("name", name).setMaxResults(1).getSingleResult();
    }

    public List<Passenger> listAllPassengers() {
        return entityManager.createQuery("SELECT p FROM Passenger p ORDER by p.name", Passenger.class)
                .getResultList();
    }

    public List<String> listPassengerNames() {
        return entityManager.createQuery("SELECT p from Passenger p ORDER BY p.name", Passenger.class).getResultStream()
            .map(Passenger::getName).collect(Collectors.toList());
    }
}

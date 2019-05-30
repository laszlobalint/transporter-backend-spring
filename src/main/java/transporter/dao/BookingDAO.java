package transporter.dao;

import org.springframework.stereotype.Repository;
import transporter.entities.Booking;
import transporter.entities.Transport;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BookingDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveBooking(Booking booking, Transport transport) {
        entityManager.merge(booking);
        entityManager.merge(transport);
        entityManager.flush();
    }

    public Booking listBooking(Long id) {
        return entityManager.find(Booking.class, id);
    }

    public List<Booking> listAllBookings() {
        return entityManager.createQuery("SELECT b FROM Booking b ORDER by b.departure_time", Booking.class)
                .getResultList();
    }

    public List<Booking> findBookingsByPassengerId(Long id) {
        return entityManager.createQuery("SELECT b FROM Booking b JOIN b.passenger p WHERE p.id = :id", Booking.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Transactional
    public void modifyBooking(Booking booking) {
        entityManager.merge(booking);
        entityManager.flush();
    }

    @Transactional
    public void removeBooking(Booking booking) {
        entityManager.remove(entityManager.contains(booking) ? booking : entityManager.merge(booking));
        entityManager.flush();
    }
}

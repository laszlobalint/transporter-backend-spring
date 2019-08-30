package transporter.dao;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import transporter.entities.Booking;
import transporter.entities.Transport;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
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
        return entityManager.createQuery("SELECT b FROM Booking b ORDER by b.departureTime", Booking.class)
                .getResultList();
    }

    public List<Booking> findBookingsByPassengerId(Long id) {
        return entityManager.createQuery("SELECT b FROM Booking b JOIN b.passenger p WHERE p.id = :id",
                Booking.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Booking> findBookingsByTransportId(Long id) {
        return entityManager.createQuery("SELECT b FROM Booking b JOIN b.transport t WHERE t.id = :id",
                Booking.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Transactional
    public void modifyBooking(Booking booking) {
        entityManager.merge(booking);
        entityManager.flush();
    }

    @Transactional
    public void removeBooking(Long id) {
        Booking b = listBooking(id);
        entityManager.remove(b);
        entityManager.flush();
    }
}

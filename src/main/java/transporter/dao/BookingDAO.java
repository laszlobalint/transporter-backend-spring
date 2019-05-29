package transporter.dao;

import org.springframework.stereotype.Repository;
import transporter.entities.Booking;
import transporter.entities.Transport;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

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

    @Transactional
    public void cancelBooking(Long id) {
        Booking booking = findBookingById(id);
        Transport transport = findTransportByDepartureTime(booking.getDepartureTime());
        transport.setFreeSeats(transport.getFreeSeats() + 1);
        entityManager.remove(booking);

    }

    public String findPassengerNameByBookingId(Long id) {
        return entityManager.createQuery("SELECT p.name FROM Booking b JOIN b.passenger p WHERE b.id = :id", String.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Booking findBookingById(Long id) {
        return entityManager.find(Booking.class, id);
    }

    public Transport findTransportByDepartureTime(LocalDateTime time) {
        return entityManager.createQuery("SELECT t FROM Transport t WHERE t.departureTime = :time", Transport.class)
                .setParameter("time", time)
                .setMaxResults(1)
                .getSingleResult();
    }
}

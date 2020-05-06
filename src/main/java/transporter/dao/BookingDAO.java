package transporter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import transporter.entities.Booking;
import transporter.entities.Transport;
import transporter.services.EmailService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BookingDAO {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EmailService emailService;

    @Transactional
    public Booking saveBooking(Booking booking, Transport transport) {
        Booking savedBooking = entityManager.merge(booking);
        entityManager.merge(transport);
        emailService.sendMail(booking.getPassenger().getEmail(), "Fuvar foglalása",
                "Sikeresen foglaltál a Transporter alkalmazásban!\n" + booking);
        entityManager.flush();
        return savedBooking;
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
    public void removeBooking(Booking booking) {
        entityManager.remove(entityManager.contains(booking) ? booking : entityManager.merge(booking));
        entityManager.flush();
    }
}
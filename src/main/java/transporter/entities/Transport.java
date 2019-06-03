package transporter.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "transport")
public class Transport {

    public static Map<String, String> driverInfo = Map.of(
            "Sofőr neve", "László Bálint",
            "Gépjármű típusa", "Škoda Superb, 2011",
            "Gépjűrmű színe", "fehér",
            "Rendszám", "PVW-221",
            "Telefonszám (HUN)", "+36-70/6793041",
            "Telefonszám (RS)", "+381-63/7693041",
            "E-mail cím", "laszlobalint1987@gmail.com",
            "Fuvardíj", "300 dinár VAGY 800 forint",
            "Egyéb infók", "klíma, nemdohányzó, csomagszállítás, készpénzfizetés"
    );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure_time", nullable = false)
    @NotNull
    private LocalDateTime departureTime;

    @Column(name = "free_seats", nullable = false)
    private int freeSeats = 4;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "transport")
    private Set<Booking> bookings;

    public Transport() {}

    public Transport(LocalDateTime departureTime, Set<Booking> bookings) {
        this.departureTime = departureTime;
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        if (booking == null) {
            bookings = new HashSet<>();
        } else {
            bookings.add(booking);
            booking.setTransport(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    @Override
    public String toString() {
        return "\nTransport information: " +
                "\nID - " + id +
                "\nDeparture time - " + departureTime +
                "\nFree seats (?/4) - " + freeSeats +
                "\nBookings: " + bookings;
    }
}

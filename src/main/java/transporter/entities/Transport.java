package transporter.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "transport")
public class Transport {

    private final int MAX_SEATS = 4;

    public enum Route {FROM_HUNGARY_TO_SERBIA, FROM_SERBIA_TO_HUNGARY}

    public static Map<String, String> driverInfo = Map.of(
            "Sofőr neve", "László Bálint",
            "Gépjármű típusa", "Škoda Superb, 2011",
            "Gépjármű színe", "fehér",
            "Rendszám", "PVW-221",
            "Telefonszám (HUN)", "+36-70/6793041",
            "Telefonszám (RS)", "+381-63/7693041",
            "E-mail cím", "laszlobalint1987@gmail.com",
            "Fuvardíj", "400 dinár VAGY 1000 forint",
            "Egyéb infók", "klíma, nemdohányzó, csomagszállítás, készpénzfizetés"
    );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Route route;

    @Column(name = "departure_time", nullable = false)
    @NotNull
    private LocalDateTime departureTime;

    @Column(name = "free_seats", nullable = false)
    private int freeSeats = MAX_SEATS;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "transport")
    @JsonIgnore
    private Set<Booking> bookings;

    public Transport() {
    }

    public Transport(Route route, LocalDateTime departureTime, Set<Booking> bookings) {
        this.route = route;
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

    public int getMAX_SEATS() {
        return MAX_SEATS;
    }

    public static Map<String, String> getDriverInfo() {
        return driverInfo;
    }

    public static void setDriverInfo(Map<String, String> driverInfo) {
        Transport.driverInfo = driverInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "\nTransport information: " +
                "\nID - " + id +
                "\nRoute - " + route +
                "\nDeparture time - " + departureTime +
                "\nFree seats (?/4) - " + freeSeats +
                "\nBookings: " + bookings;
    }
}
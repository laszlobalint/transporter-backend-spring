package transporter.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {

    public enum LocationSerbia {
        NEW_CITY_HALL, MARKET_LIDL, POLICE_STATION, MARKET_024, RADANOVAC, PALIC_WATERTOWER,
        RESTAURANT_ABRAHAM
    }

    public enum LocationHungary {SING_SING_MUSIC_HALL, MARKET_SMALL_TESCO, BAKERY_BUREK, GRINGOS_BUS_STOP, ICERINK}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure_time", nullable = false)
    @NotNull
    private LocalDateTime departureTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LocationSerbia locationSerbia;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LocationHungary locationHungary;

    @OneToOne(fetch = FetchType.EAGER)
    private Passenger passenger;

    @ManyToOne(fetch = FetchType.EAGER)
    private Transport transport;

    @Transient
    private String departureTimeString;

    public Booking() {
    }

    public Booking(String departureTime, LocationSerbia pickUp, LocationHungary dropOff) {
        this.departureTime = LocalDateTime.parse(departureTime);
        this.locationSerbia = pickUp;
        this.locationHungary = dropOff;
    }

    public Booking(LocalDateTime departureTime, LocationHungary pickUp, LocationSerbia dropOff) {
        this.departureTime = departureTime;
        this.locationHungary = pickUp;
        this.locationSerbia = dropOff;
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

    public String getDepartureTimeString() {
        return departureTimeString;
    }

    public void setDepartureTimeString(String departureTimeString) {
        this.departureTimeString = departureTimeString;
    }

    public LocationSerbia getLocationSerbia() {
        return locationSerbia;
    }

    public void setLocationSerbia(LocationSerbia locationSerbia) {
        this.locationSerbia = locationSerbia;
    }

    public LocationHungary getLocationHungary() {
        return locationHungary;
    }

    public void setLocationHungary(LocationHungary locationHungary) {
        this.locationHungary = locationHungary;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    @Override
    public String toString() {
        return "\nBooking: " +
                "\nID - " + id +
                "\nDeparture time - " + departureTime +
                "\nLocation (RS) - " + locationSerbia +
                "\nLocation (HU) - " + locationHungary +
                "\nPassenger - " + passenger +
                "\nTransport - " + transport;
    }
}
package transporter.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "booking")
public class Booking {

    public enum LocationSerbia { NEW_CITY_HALL, MARKET_LIDL, POLICE_STATION, MARKET_024, RADANOVAC, PALIC_WATERTOWER, RESTAURANT_ABRAHAM }
    public enum LocationHungary { SING_SING_MUSIC_HALL, MARKET_SMALL_TESCO, BAKERY_BUREK, GRINGOS_BUS_STOP, ICERINK }

    public static Map<LocationSerbia, String> serbiaLocationAddresses = Map.of(
            LocationSerbia.NEW_CITY_HALL, "Szabadka, Mars téri városi piac mögött található Sing Sing szórakozóhely előtti parkoló",
            LocationSerbia.MARKET_LIDL, "Szabadka, Szegedi út és Pap Pál utca sarka, Lidl áruház előtti buszmegálló",
            LocationSerbia.POLICE_STATION, "Szabadka, Szegedi út és Bože Šarčević utca sarka, rendőrkapitányság előtti buszmegálló",
            LocationSerbia.MARKET_024, "Szabadka, Szegedi út és Partizán bázisok utca sarka, 024 Market és Solid pálya előtti buszmegálló",
            LocationSerbia.RADANOVAC, "Nagyradanovác, Szegedi út és Testvériség-egység körút sarka, nagyradanováci buszmegálló",
            LocationSerbia.PALIC_WATERTOWER, "Palics, Horgosi út, vasúti átjáró környéke, víztorony előtti buszmegálló",
            LocationSerbia.RESTAURANT_ABRAHAM, "Palics, Horgosi út és Ludasi utca sarka, Ábrahám vendéglő előtti parkoló");
    public static Map<LocationHungary, String> hungaryLocationAddresses = Map.of(
            LocationHungary.SING_SING_MUSIC_HALL, "Szeged, Makszim Gorkij utca és Đure Đaković sarka, Új Városháza előtti buszmegálló",
            LocationHungary.MARKET_SMALL_TESCO, "Szeged, Dugonics téri TESCO Expressz ('Kis Tesco') előtti autóparkoló",
            LocationHungary.BAKERY_BUREK, "Szeged, Petőfi Sándor sugárút és Nemes Takács utca sarkán lévő Burek Pékség előtt",
            LocationHungary.GRINGOS_BUS_STOP, "Szeged, Petőfi Sándor sugárút és Rákóczi utca sarkán lévő Gringos étterem közelében lévő buszmegálló",
            LocationHungary.ICERINK, "Szeged, Szabadkai úton lévő Városi Műjégpálya és Shell töltöállomás előtti buszmegálló");

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

    public Booking() {}

    public Booking(LocalDateTime departureTime, LocationSerbia pickUp, LocationHungary dropOff) {
        this.departureTime = departureTime;
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

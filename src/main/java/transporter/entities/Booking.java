package transporter.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Entity
@Table(name = "booking")
public class Booking {

    public enum LocationSerbia {
        NEW_CITY_HALL, MARKET_LIDL, POLICE_STATION, MARKET_024, RADANOVAC, PALIC_WATERTOWER,
        RESTAURANT_ABRAHAM
    }

    public enum LocationHungary {MC_DONALDS_DRIVE_THROUGH, SING_SING_MUSIC_HALL, MARKET_SMALL_TESCO, BAKERY_BUREK, GRINGOS_BUS_STOP, ICERINK}

    @Transient
    private Map<LocationSerbia, String> locationSerbiaStringMap = Map.of(
            LocationSerbia.NEW_CITY_HALL, "Szabadka, Makszim Gorkij utca és Đure Đaković sarka, Új Városháza előtti buszmegálló",
            LocationSerbia.MARKET_LIDL, "Szabadka, Szegedi út és Pap Pál utca sarka, Lidl áruház előtti buszmegálló",
            LocationSerbia.POLICE_STATION, "Szabadka, Szegedi út és Bože Šarčević utca sarka, rendőrkapitányság előtti buszmegálló",
            LocationSerbia.MARKET_024, "Szabadka, Szegedi út és Partizán bázisok utca sarka, 024 Market és Solid pálya előtti buszmegálló",
            LocationSerbia.RADANOVAC, "Nagyradanovác, Szegedi út és Testvériség-egység körút sarka, nagyradanováci buszmegálló",
            LocationSerbia.PALIC_WATERTOWER, "Palics, Horgosi út, vasúti átjáró környéke, víztorony előtti buszmegálló",
            LocationSerbia.RESTAURANT_ABRAHAM, "Palics, Horgosi út és Ludasi utca sarka, Ábrahám vendéglő előtti parkoló"
    );

    @Transient
    private Map<LocationHungary, String> locationHungaryStringMap = Map.of(
            LocationHungary.MC_DONALDS_DRIVE_THROUGH, "Szeged, Rókusi körúti autós McDonald's gyorsétterem előtti buszmegálló",
            LocationHungary.SING_SING_MUSIC_HALL, "Szeged, Mars téri városi piac mögött található Sing Sing szórakozóhely előtti parkoló",
            LocationHungary.MARKET_SMALL_TESCO, "Szeged, Dugonics téri TESCO Expressz (\"Kis Tesco\") előtti autóparkoló",
            LocationHungary.BAKERY_BUREK, "Szeged, Petőfi Sándor sugárút és Nemes Takács utca sarkán lévő Burek Pékség előtt",
            LocationHungary.GRINGOS_BUS_STOP, "Szeged, Petőfi Sándor sugárút és Rákóczi utca sarkán lévő Gringos étterem közelében lévő buszmegálló",
            LocationHungary.ICERINK, "Szeged, Szabadkai úton lévő Városi Műjégpálya és Shell töltöállomás előtti buszmegálló"
    );

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

    public Booking() {
    }

    public Booking(LocalDateTime departureTime, LocationSerbia locationSerbia, LocationHungary locationHungary) {
        this.departureTime = departureTime;
        this.locationSerbia = locationSerbia;
        this.locationHungary = locationHungary;
    }

    public Map<LocationSerbia, String> getLocationSerbiaStringMap() {
        return locationSerbiaStringMap;
    }

    public void setLocationSerbiaStringMap(Map<LocationSerbia, String> locationSerbiaStringMap) {
        this.locationSerbiaStringMap = locationSerbiaStringMap;
    }

    public Map<LocationHungary, String> getLocationHungaryStringMap() {
        return locationHungaryStringMap;
    }

    public void setLocationHungaryStringMap(Map<LocationHungary, String> locationHungaryStringMap) {
        this.locationHungaryStringMap = locationHungaryStringMap;
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
        return "\nFoglalási infók: " +
                "\nIndulási idő - " + departureTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm")) +
                "\nMegadott hely (RS) - " + locationSerbiaStringMap.get(locationSerbia) +
                "\nMegadott hely (HU) - " + locationHungaryStringMap.get(locationHungary) +
                "\n" + passenger +
                "\n" + transport + "\n";
    }
}
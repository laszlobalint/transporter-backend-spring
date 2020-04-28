package transporter.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotEmpty
    @JsonIgnore
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @NotEmpty
    @Column(name = "phone_number", length = 100, nullable = false)
    private String phoneNumber;

    @NotEmpty
    @Email
    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @Column(name = "is_activated", nullable = false)
    private boolean isActivated;

    @Column(name = "booking_count", nullable = false)
    private int bookingCount;

    public Passenger() {
    }

    public Passenger(String name, String password, String phoneNumber, String email) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isActivated = false;
        this.bookingCount = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(boolean activated) {
        isActivated = activated;
    }

    public int getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(int bookingCount) {
        this.bookingCount = bookingCount;
    }

    @Override
    public String toString() {
        return "\nPassenger: " +
                "\nID - " + id +
                "\nName - " + name + '\'' +
                "\nPhone number - " + phoneNumber + '\'' +
                "\nEmail address - " + email + '\'' +
                "\nActivated - " + isActivated +
                "\nBooking count - " + bookingCount;
    }
}
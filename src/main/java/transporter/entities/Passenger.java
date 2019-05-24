package transporter.entities;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "phone_number", length = 100, nullable = false)
    private String phoneNumber;

    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @Lob
    @Column(name = "picture", nullable = true)
    private byte[] picture;

    @Column(name = "is_activated", nullable = false)
    private boolean isActivated;

    @Column(name = "booking_count", nullable = false)
    private int bookingCount;

    public Passenger() {}

    public Passenger(String name, String phoneNumber, String email, byte[] picture) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.picture = picture;
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
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
                "\nPicture data - " + Arrays.toString(picture) +
                "\nActivated - " + isActivated +
                "\nBooking count - " + bookingCount;
    }
}

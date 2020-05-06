package transporter.dto;

public class DeleteBooking {

    public Long transportId;

    public Long bookingId;

    public DeleteBooking(Long transportId, Long bookingId) {
        this.transportId = transportId;
        this.bookingId = bookingId;
    }

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}

package transporter.authorizations;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import transporter.entities.Booking;
import transporter.entities.Passenger;
import transporter.services.BookingService;
import transporter.services.PassengerService;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class AuthService {

    @Autowired
    private PassengerService passengerService;
    @Autowired
    private BookingService bookingService;

    public String createJWT(Passenger passenger) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(System.getenv("TRANSPORTER_SECRET_KEY"));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(passenger.getId().toString())
                .setIssuer(passenger.getEmail())
                .signWith(signatureAlgorithm, signingKey);
        Date exp = new Date(System.currentTimeMillis() + 604800000L);
        builder.setExpiration(exp);
        return builder.compact();
    }

    private Claims decodeJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(System.getenv("TRANSPORTER_SECRET_KEY")))
                .parseClaimsJws(jwt).getBody();
    }

    public Claims resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer "))
            return decodeJWT(bearerToken.substring(7));
        else return null;
    }

    public boolean validateToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            Jws<Claims> claims = Jwts.parser().setSigningKey(System.getenv("TRANSPORTER_SECRET_KEY"))
                    .parseClaimsJws(bearerToken.substring(7));
            return !claims.getBody().getExpiration().before(new Date());
        }
        return false;
    }

    public boolean validateAdmin(HttpServletRequest req) {
        return validateToken(req) && resolveToken(req).getIssuer().equals(System.getenv("ADMIN_MAIL"));
    }

    public boolean validatePersonalRequestByBooking(HttpServletRequest req, Long bookingId) {
        Long id = Long.parseLong(resolveToken(req).getSubject(), 10);
        Booking b = bookingService.listBooking(bookingId);
        return validateToken(req) && b.getPassenger().getId().equals(id);
    }

    public boolean validatePersonalRequestByPassenger(HttpServletRequest req, Long passengerId) {
        Long id = Long.parseLong(resolveToken(req).getSubject(), 10);
        Passenger p = passengerService.listPassenger(passengerId);
        return validateToken(req) && p.getId().equals(id);
    }
}
package transporter.authorizations;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import transporter.entities.Passenger;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class AuthService {

    @Autowired
    private Environment environment;

    public String createJWT(Passenger passenger) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long ttlMillis = 604800000L;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(environment.getProperty("secretKey"));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject(passenger.getId().toString())
                .setIssuer(passenger.getEmail())
                .signWith(signatureAlgorithm, signingKey);
        long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
        return builder.compact();
    }

    public Claims decodeJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(environment.getProperty("secretKey")))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public String getPassengerName(String token) {
        return Jwts.parser().setSigningKey(environment.getProperty("secretKey")).parseClaimsJws(token)
                .getBody().getSubject();
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
            Jws<Claims> claims = Jwts.parser().setSigningKey(environment.getProperty("secretKey"))
                    .parseClaimsJws(bearerToken.substring(7));
            return !claims.getBody().getExpiration().before(new Date());
        }
        return false;
    }
}
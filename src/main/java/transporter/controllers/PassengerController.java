package transporter.controllers;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import transporter.entities.Passenger;
import transporter.services.PassengerService;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/passenger")
@CrossOrigin(origins = "http://localhost:4200")
public class PassengerController {

    private PassengerService passengerService;

    @Autowired
    private Environment environment;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public Model savePassenger(@Valid Passenger passenger, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Hibás adatokat adtál meg a profilodnál!");
        } else {
            passengerService.savePassenger(passenger);
            model.addAttribute("message", "Sikeresen mentetted a profilodat!");
        }
        return model;
    }

    @GetMapping
    public Passenger listPassenger(Long id) {
        return passengerService.listPassenger(id);
    }

    @GetMapping("/all")
    public List<Passenger> listAllPassengers() { return passengerService.listAllPassengers(); }

    @PutMapping
    public Model modifyPassenger(@ModelAttribute Passenger passenger, Model model) {
        Long id = null;
        passengerService.modifyPassenger(passenger, id);
        model.addAttribute("message", "Sikeresen módosítottad a profilodat!");
        return model;
    }

    @DeleteMapping
    public Model removePassenger(Model model) {
        Long id = null;
        passengerService.removePassenger(id);
        model.addAttribute("message", "Sikeresen törölted a foglalásodat!");
        return model;
    }


    // TOKEN FUNCTIONALITY - UNDER IMPLEMENTATION
    @GetMapping("/token")
    public boolean getToken() {
        String token = createJWT(listPassenger(1L));
        // return "ID of the user: " + decodeJWT(token).getSubject();
        return validateToken(token);
    }

    private String createJWT(Passenger passenger) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long ttlMillis = 604800000L;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(environment.getProperty("secretKey"));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject(passenger.getId().toString())
                .setIssuer(passenger.getName())
                .signWith(signatureAlgorithm, signingKey);

        long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);

        return builder.compact();
    }

    private Claims decodeJWT(String jwt) {

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(environment.getProperty("secretKey")))
                .parseClaimsJws(jwt).getBody();
        System.out.println("Token ID: " + claims.getId());
        System.out.println("User ID: " + claims.getSubject());
        System.out.println("Name: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());

        return claims;
    }

    private String getPassengerName(String token) {

        return Jwts.parser().setSigningKey(environment.getProperty("secretKey")).parseClaimsJws(token).getBody().getSubject();
    }

    private String resolveToken(HttpServletRequest req) {

        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {

            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }

    private boolean validateToken(String token) {
            Jws<Claims> claims = Jwts.parser().setSigningKey(environment.getProperty("secretKey")).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        }
    }

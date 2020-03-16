package transporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import transporter.services.BookingService;
import transporter.services.PassengerService;
import transporter.services.TransportService;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@ComponentScan(basePackageClasses = WebConfiguration.class)
public class WebConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private TransportService transportService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public void init() {
        /*Transport transport1 = new Transport(Transport.Route.FROM_HUNGARY_TO_SERBIA, LocalDateTime.of(2019, 5, 16, 20, 0), null);
        transportService.saveTransport(transport1);
        Transport transport2 = new Transport(Transport.Route.FROM_SERBIA_TO_HUNGARY, LocalDateTime.of(2019, 3, 5, 10, 30), null);
        transportService.saveTransport(transport2);
        Passenger passenger1 = new Passenger("Test Passenger", "test", "+36-70-11111111", "test@test.com");
        passengerService.savePassenger(passenger1);
        Booking booking1 = new Booking(LocalDateTime.of(2019, 5, 16, 20, 0),
                Booking.LocationHungary.GRINGOS_BUS_STOP, Booking.LocationSerbia.MARKET_LIDL);
        booking1.setPassenger(passengerService.listPassenger(1L));
        bookingService.saveBooking(booking1);
        Passenger passenger2 = new Passenger("John Doe", "john", "+36-70-22222222", "gmail@gmail.com");
        passengerService.savePassenger(passenger2);
        Booking booking2 = new Booking(LocalDateTime.of(2019, 5, 16, 20, 0),
                Booking.LocationHungary.BAKERY_BUREK, Booking.LocationSerbia.NEW_CITY_HALL);
        booking2.setPassenger(passengerService.listPassenger(2L));
        bookingService.saveBooking(booking2);*/
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        return resolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
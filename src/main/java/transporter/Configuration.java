package transporter;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import transporter.dao.BookingDAO;
import transporter.dao.PassengerDAO;
import transporter.dao.TransportDAO;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackageClasses = Configuration.class)
@PropertySource("classpath:/application.properties")
@EnableTransactionManagement
public class Configuration {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        try {
            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setUrl(environment.getProperty("jdbc.url"));
            dataSource.setUser(environment.getProperty("jdbc.username"));
            dataSource.setPassword(environment.getProperty("jdbc.password"));
            return dataSource;
        }
        catch (SQLException se) {
            throw new IllegalStateException("Could not create data source!", se);
        }
    }

    @Bean
    public Properties hibernateProperties(){
        final Properties properties = new Properties();
        properties.put( "hibernate.dialect", "org.hibernate.dialect.MariaDB10Dialect" );
        properties.put( "hibernate.connection.driver_class", "org.mariadb.jdbc.Driver" );
        properties.put( "hibernate.hbm2ddl.auto", "update" );

        return properties;
    }

//    @Bean
//    public Flyway flyway() {
//        Flyway flyway = new Flyway();
//        flyway.setDataSource(dataSource());
//        flyway.migrate();
//        return flyway;
//    }

    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter =
                new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Properties hibernateProperties) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(hibernateProperties);
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("transporter.entities");
        entityManagerFactoryBean.setPersistenceUnitName("transporter");
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean;
    }

    @Bean
    public BookingDAO bookingDAO() {
        return new BookingDAO();
    }

    @Bean
    public PassengerDAO passengerDAO() {
        return new PassengerDAO();
    }

    @Bean
    public TransportDAO transportDAO() {
        return new TransportDAO();
    }
}

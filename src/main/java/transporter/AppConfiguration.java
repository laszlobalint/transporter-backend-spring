package transporter;

import org.flywaydb.core.Flyway;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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

@Configuration
@ComponentScan(basePackageClasses = AppConfiguration.class)
@PropertySource("classpath:/application.properties")
@EnableJpaRepositories("transporter.dao")
@EntityScan({"transporter.entities", "transporter.dto"})
@EnableTransactionManagement
public class AppConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        try {
            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setUrl(System.getenv("JDBC_URL"));
            dataSource.setUser(System.getenv("JDBC_USERNAME"));
            dataSource.setPassword(System.getenv("JDBC_PASSWORD"));
            return dataSource;
        } catch (SQLException se) {
            throw new IllegalStateException("Could not create data source!", se);
        }
    }

    @Bean
    public Properties hibernateProperties() {
        final Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.connection.driver_class", environment.getProperty("hibernate.driver"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.strategy"));
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter =
                new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        return hibernateJpaVendorAdapter;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource());
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations("classpath:db/migration");
        return flyway;
    }

    @Bean
    @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       Properties hibernateProperties) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(hibernateProperties);
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan(environment.getProperty("packagesToScan"));
        entityManagerFactoryBean.setPersistenceUnitName(environment.getProperty("persistenceUnitName"));
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
package transporter;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
@EnableJpaRepositories
@EnableTransactionManagement
public class AppConfiguration {

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
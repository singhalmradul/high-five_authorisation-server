package io.github.singhalmradul.authorizationserver.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import io.github.singhalmradul.authorizationserver.utilities.YamlPropertySourceFactory;
import lombok.AllArgsConstructor;

@Configuration
@PropertySource(value = "classpath:db.yaml", factory = YamlPropertySourceFactory.class)
@EnableJpaRepositories(
    basePackages = "io.github.singhalmradul.authorizationserver.repositories.shared",
    entityManagerFactoryRef = "sharedEntityManagerFactory",
    transactionManagerRef = "sharedTransactionManager"
)
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SharedDatabaseConfiguration {

    Environment env;

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "shared")
    DataSource sharedDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean sharedEntityManagerFactory() {
        var entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(sharedDataSource());
        entityManager.setPackagesToScan("io.github.singhalmradul.authorizationserver.model.shared");

        var vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);

        var properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("shared.dialect"));
        entityManager.setJpaProperties(properties);
        return entityManager;
    }

    @Bean
    JpaTransactionManager sharedTransactionManager() {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(sharedEntityManagerFactory().getObject());

        return transactionManager;
    }
}

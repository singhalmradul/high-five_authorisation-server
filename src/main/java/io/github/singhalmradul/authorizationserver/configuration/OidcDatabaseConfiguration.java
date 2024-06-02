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
    basePackages = "io.github.singhalmradul.authorizationserver.repositories.oidc",
    entityManagerFactoryRef = "oidcEntityManagerFactory",
    transactionManagerRef = "oidcTransactionManager"
)
@AllArgsConstructor(onConstructor_ = @Autowired)
public class OidcDatabaseConfiguration {

    Environment env;

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "oidc")
    DataSource oidcDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    LocalContainerEntityManagerFactoryBean oidcEntityManagerFactory() {
        var entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(oidcDataSource());
        entityManager.setPackagesToScan("io.github.singhalmradul.authorizationserver.model.oidc");

        var vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);

        var properties = new Properties();

        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("oidc.dialect"));
        entityManager.setJpaProperties(properties);
        return entityManager;
    }

    @Bean
    JpaTransactionManager oidcTransactionManager() {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(oidcEntityManagerFactory().getObject());

        return transactionManager;
    }
}

package com.example.ultimatemathcompanion;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Objects;
import java.util.logging.Logger;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class UltimateMathCompanionApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(UltimateMathCompanionApplication.class, args);
    }

    private final Environment env;

    public UltimateMathCompanionApplication(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource securityDataSource() {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        try {
            dataSource.setDriverClass(env.getProperty("spring.datasource.driver"));
        } catch (PropertyVetoException err) {
            throw new RuntimeException(err);
        }

        dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUser(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        dataSource.setInitialPoolSize(propertyToInt("connection.pool.initialPoolSize"));
        dataSource.setMinPoolSize(propertyToInt("connection.pool.minPoolSize"));
        dataSource.setMaxPoolSize(propertyToInt("connection.pool.maxPoolSize"));
        dataSource.setMaxIdleTime(propertyToInt("connection.pool.maxIdleTime"));

        return dataSource;
    }

    private int propertyToInt(String str) {
        return Integer.parseInt(Objects.requireNonNull(env.getProperty(str)));
    }

}

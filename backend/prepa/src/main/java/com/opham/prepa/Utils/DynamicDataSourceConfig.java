package com.opham.prepa.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DynamicDataSourceConfig {

    private final Environment environment;
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public DynamicDataSourceConfig(Environment environment, JdbcTemplate jdbcTemplate) {
        this.environment = environment;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateDataSourceProperties(String username, String password) {
        // Créer une nouvelle instance de DataSource avec les nouvelles informations d'identification
        DriverManagerDataSource newDataSource = new DriverManagerDataSource();
        newDataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        newDataSource.setUrl(environment.getProperty("spring.datasource.url"));
        newDataSource.setUsername(username);
        newDataSource.setPassword(password);

        // Mettre à jour le bean JdbcTemplate avec la nouvelle DataSource
        jdbcTemplate.setDataSource(newDataSource);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

//    private DataSource createDataSource() {
//        String url = environment.getProperty("spring.datasource.url");
//        String driverClassName = environment.getProperty("spring. .driver-class-name");
//        String username = environment.getProperty("spring.datasource.username");
//        String password = environment.getProperty("spring.datasource.password");
//
//        return createDataSource(url, driverClassName, username, password);
//    }
//
//    private DataSource createDataSource(String url, String driverClassName, String username, String password) {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        return dataSource;
//    }
}

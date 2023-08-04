package com.opham.prepa.Utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

    public DriverManagerDataSource getDataSource(String username, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.sybase.jdbc4.jdbc.SybDriver");
        dataSource.setUrl("jdbc:sybase:Tds:192.168.130.221:2025/Equagestion");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}




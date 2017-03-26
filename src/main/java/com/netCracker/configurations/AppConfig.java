package com.netCracker.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class AppConfig {
    @Bean
    public InternalResourceViewResolver viewer(){
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/views/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    @Bean
    public DataSource getDBConnection() throws NamingException, SQLException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        return (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/company");
    }
}

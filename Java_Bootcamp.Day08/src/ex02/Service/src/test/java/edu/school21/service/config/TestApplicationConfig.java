package edu.school21.service.config;

import edu.school21.service.repositories.UsersRepository;
import edu.school21.service.repositories.UsersRepositoryJdbcImpl;
import edu.school21.service.repositories.UsersRepositoryJdbcTemplateImpl;
import edu.school21.service.services.UsersService;
import edu.school21.service.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@ComponentScan("edu.school21.service.services")
public class TestApplicationConfig {
    @Bean
    public EmbeddedDatabase EmbeddedDatabaseDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .setScriptEncoding("UTF-8")
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
    }

    @Bean
    public UsersRepository usersRepositoryJdbc() {
        return new UsersRepositoryJdbcImpl(EmbeddedDatabaseDataSource());
    }

    @Bean
    public UsersService usersService() {
        return new UsersServiceImpl(usersRepositoryJdbc());
    }
}
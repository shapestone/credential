package com.xlenc.party.credential;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.net.UnknownHostException;

/**
 * User: Michael Williams
 * Date: 8/20/12
 * Time: 11:53 PM
 */
@Configuration
@ComponentScan(basePackages = "com.xlenc.party.credential.authentication.rest")
public class CredentialConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties(){
        final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.setIgnoreResourceNotFound(true);
        final ClassPathResource classPathResource = new ClassPathResource("credential.properties");
        final FileSystemResource fileSystemResource = new FileSystemResource("/opt/tomcat/config/credential.properties");
        // final Resource[] resources = new ClassPathResource[ ] {classPathResource};
        final Resource[] resources = new Resource[ ] {classPathResource, fileSystemResource};
        configurer.setLocations(resources);
        return configurer;
    }

    private String database;

    @Bean
    public Mongo mongo() throws UnknownHostException {
        return new Mongo("localhost");
    }

    @Bean
    public Morphia morphia() {
        return new Morphia();
    }

    @Bean(name = "credentialPersistence")
    public CredentialPersistence credentialPersistence() throws UnknownHostException {
        final BasicDAO<CredentialData, Object> credentialBasicDAO;
        credentialBasicDAO = new BasicDAO<CredentialData, Object>(CredentialData.class, mongo(), morphia(), database);
        return new CredentialPersistenceImpl(credentialBasicDAO);
    }

    @Bean(name = "credentialService")
    public CredentialService credentialService() {
        return new CredentialServiceImpl();
    }

    @Value("${party.credential.database}")
    public void setDatabase(String database) {
        this.database = database;
    }

}

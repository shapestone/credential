package com.xlenc.party.credential;

import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.xlenc.party.credential.authentication.rest.CredentialResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * User: Michael Williams
 * Date: 11/14/13
 * Time: 1:15 AM
 */
public class CredentialServer extends Service<CredentialServiceConfiguration> {

    @Override
    public void initialize(Bootstrap<CredentialServiceConfiguration> bootstrap) {
    }

    @Override
    public void run(CredentialServiceConfiguration configuration, Environment environment) throws Exception {
        final MongoDatabaseConfiguration mongoDatabaseConfiguration = configuration.getMongoDatabaseConfiguration();
        final String host = mongoDatabaseConfiguration.getHost();
        final int port = mongoDatabaseConfiguration.getPort();
        final Mongo mongo = new MongoClient(host, port);
        final Morphia morphia = new Morphia();
        final String databaseName = mongoDatabaseConfiguration.getDatabaseName();

        wireCredentialService(environment, mongo, morphia, databaseName);
        wireHealthChecks(environment, mongo);
    }

    private void wireCredentialService(Environment environment, Mongo mongo, Morphia morphia, String databaseName) {
        final CredentialPersistence credentialPersistence = new CredentialPersistenceImpl(mongo, morphia, databaseName);
        final CredentialService credentialService = new CredentialServiceImpl(credentialPersistence);
        final CredentialResource credentialResource = new CredentialResource(credentialService);
        environment.addResource(credentialResource);
    }

    private void wireHealthChecks(Environment environment, Mongo mongo) {
        environment.addHealthCheck(new MongoHealthCheck(mongo));
    }

    public static void main(String[] args) throws Exception {
        new CredentialServer().run(args);
    }

}

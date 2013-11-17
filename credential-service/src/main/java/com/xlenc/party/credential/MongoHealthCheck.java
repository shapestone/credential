package com.xlenc.party.credential;

import com.mongodb.Mongo;
import com.yammer.metrics.core.HealthCheck;

/**
 * User: Michael Williams
 * Date: 11/14/13
 * Time: 1:18 AM
 */
public class MongoHealthCheck extends HealthCheck {

    private final Mongo mongo;

    public MongoHealthCheck(Mongo mongo) {
        super("MongoHealthCheck");
        this.mongo = mongo;
    }

    @Override
    protected HealthCheck.Result check() throws Exception {
        mongo.getDatabaseNames();
        return HealthCheck.Result.healthy();
    }

}

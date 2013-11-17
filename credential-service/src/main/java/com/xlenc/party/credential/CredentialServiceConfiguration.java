package com.xlenc.party.credential;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * User: Michael Williams
 * Date: 11/14/13
 * Time: 1:17 AM
 */
@EqualsAndHashCode(callSuper = false)
public @Data class CredentialServiceConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("mongoDatabase")
    private MongoDatabaseConfiguration mongoDatabaseConfiguration = new MongoDatabaseConfiguration();

}

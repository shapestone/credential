package com.xlenc.party.credential.authentication.rest;

import com.xlenc.party.credential.CredentialData;
import com.xlenc.party.credential.CredentialService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.status;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * User: Michael Williams
 * Date: 10/19/12
 * Time: 10:00 AM
 */
@Component
@Path("/credentials")
public class CredentialResource {

    private static final Logger LOGGER = getLogger(CredentialResource.class);

    @Autowired
    private CredentialService credentialService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCredential(final CredentialData credential) {
        CredentialData addedCredential = credentialService.add(credential);
        return Response.ok(addedCredential).build();
    }

    @POST
    @Path("/authenticate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(final CredentialData credential) {

        boolean isAuthenticated = false;
        String message = "Authentication successful.";

        try {
            isAuthenticated = credentialService.authenticate(credential);

        } catch (Exception e) {

            LOGGER.error("Exception Caught: ", e);
            message = e.getMessage();

        }


        final Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        stringObjectMap.put("authenticated", isAuthenticated);
        stringObjectMap.put("message", message);

        final Response build;
        if (isAuthenticated) {
            build = status(OK).entity(stringObjectMap).build();
        } else {
            build = status(BAD_REQUEST).entity(stringObjectMap).build();
        }

        return build;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCredential(final CredentialData credential) {
        CredentialData addedCredential = credentialService.changePassword(credential);
        return Response.ok(addedCredential).build();
    }

}

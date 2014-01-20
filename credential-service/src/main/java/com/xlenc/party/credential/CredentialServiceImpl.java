package com.xlenc.party.credential;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * User: Michael Williams
 * Date: 10/24/12
 * Time: 1:46 AM
 */
public class CredentialServiceImpl implements CredentialService {

    private static final Logger LOG = getLogger(CredentialServiceImpl.class);

    private CredentialPersistence credentialPersistence;

    public CredentialServiceImpl(CredentialPersistence credentialPersistence) {
        this.credentialPersistence = credentialPersistence;
    }

    @Override
    public boolean authenticate(CredentialData credential) {

        LOG.debug("Credential: {}", credential.toString());

        notNull(credential, "Parameter \"credential\" cannot be null");

        final String username = credential.getUsername();
        notNull(username, "Username cannot be null.");
        notEmpty(username, "Username cannot be empty.");

        final String password = credential.getPassword();
        notNull(password, "Password cannot be null.");
        notEmpty(password, "Username cannot be empty.");

        final CredentialData newCredential = credentialPersistence.findByUsername(username);

        if (newCredential == null) { return false; }

        final String hashedPassword = newCredential.getHashedPassword();

        return hashedPassword != null && BCrypt.checkpw(password, hashedPassword);
    }

    @Override
    public CredentialData add(CredentialData credential) {

        LOG.debug("Credential: {}", credential.toString());

        notNull(credential, "Parameter \"credential\" cannot be null");

        final String username = credential.getUsername();
        LOG.debug("username: {}", username);
        notNull(username, "Username cannot be null.");
        notEmpty(username, "Username cannot be empty.");

        final String password = credential.getPassword();
        LOG.debug("password: {}", password);
        notNull(password, "Password cannot be null.");
        notEmpty(password, "Username cannot be empty.");

        CredentialData newCredential = null;
        try {
            final String hashPwd = BCrypt.hashpw(password, BCrypt.gensalt());
            credential.setHashedPassword(hashPwd);

            newCredential = credentialPersistence.addCredential(credential);
        } catch (Exception e) {
            LOG.error("Exception Caught: ", e);
        } finally {
            if (newCredential != null) {
                newCredential.setHashedPassword(null);
                newCredential.setPassword(null);
            }
        }

        return newCredential;
    }

    @Override
    public int updateCredential(CredentialData credential) {

        LOG.debug("Credential: {}", credential.toString());

        notNull(credential, "Parameter \"credential\" cannot be null");

        final String username = credential.getUsername();
        LOG.debug("username: {}", username);
        notNull(username, "Username cannot be null.");
        notEmpty(username, "Username cannot be empty.");

        final String password = credential.getPassword();
        LOG.debug("password: {}", password);
        notNull(password, "Password cannot be null.");
        notEmpty(password, "Username cannot be empty.");

        Integer newCredential = 0;
        try {
            final String hashPwd = BCrypt.hashpw(password, BCrypt.gensalt());
            credential.setHashedPassword(hashPwd);

            newCredential = credentialPersistence.updateCredential(credential);
        } catch (Exception e) {
            LOG.error("Exception Caught: ", e);
        }

        return newCredential;
    }

    @Override
    public CredentialData findByUsername(String username) {
        notNull(username, "Parameter \"username\" cannot be null");
        notEmpty(username, "Parameter \"username\" cannot be empty");
        return credentialPersistence.findByUsername(username);
    }

    @Override
    public CredentialData findById(String id) {
        notNull(id, "Parameter \"id\" cannot be null");
        notEmpty(id, "Parameter \"id\" cannot be empty");
        return credentialPersistence.findById(id);
    }

    @Override
    public CredentialData findByExternalKeyValue(String name, String id) {
        final CredentialData credentialData;
        credentialData = credentialPersistence.findByExternalKeyValue(name, id);
        // The hashed password should never be returned.
        credentialData.setHashedPassword("");
        return credentialData;
    }

    @Override
    public int delete(String id) {
        notNull(id, "Parameter \"id\" cannot be null");
        notEmpty(id, "Parameter \"id\" cannot be empty");
        return credentialPersistence.delete(id);
    }

}

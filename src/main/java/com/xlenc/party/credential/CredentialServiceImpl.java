package com.xlenc.party.credential;

import com.xlenc.party.credential.CredentialData;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.Validate.notEmpty;
import static org.apache.commons.lang.Validate.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * User: Michael Williams
 * Date: 10/24/12
 * Time: 1:46 AM
 */
@Service("credentialService")
public class CredentialServiceImpl implements CredentialService {

    private static final Logger LOGGER = getLogger(CredentialServiceImpl.class);

    @Autowired
    private CredentialPersistence credentialPersistence;

    @Override
    public boolean authenticate(CredentialData credential) {
        notNull(credential, "Parameter \"credential\" cannot be null");

        final String username = credential.getUsername();
        notNull(username, "Username cannot be null.");
        notEmpty(username, "Username cannot be empty.");

        final String password = credential.getPassword();
        notNull(password, "Password cannot be null.");
        notEmpty(password, "Username cannot be empty.");

        final CredentialData newCredential = credentialPersistence.findByUsername(username);

        if (newCredential == null) { return false; }

        final String readPassword = newCredential.getPassword();

        return readPassword != null && BCrypt.checkpw(password, readPassword);

    }

    @Override
    public CredentialData add(CredentialData credential) {
        notNull(credential, "Parameter \"credential\" cannot be null");

        final String username = credential.getUsername();
        notNull(username, "Username cannot be null.");
        notEmpty(username, "Username cannot be empty.");

        final String password = credential.getPassword();
        notNull(password, "Password cannot be null.");
        notEmpty(password, "Username cannot be empty.");

        CredentialData newCredential = null;
        try {
            final String hashpwd = BCrypt.hashpw(password, BCrypt.gensalt());
            credential.setHashedPasswrod(hashpwd);

            newCredential = credentialPersistence.add(credential);
        } catch (Exception e) {
            LOGGER.error("Exception Caught: ", e);
        } finally {
            if (newCredential != null) {
                newCredential.setHashedPasswrod(null);
                newCredential.setPassword(null);
            }
        }

        return newCredential;
    }

    public int updateById(CredentialData credential) {
        notNull(credential, "Parameter \"credential\" cannot be null");

        final String username = credential.getUsername();
        notNull(username, "Username cannot be null.");
        notEmpty(username, "Username cannot be empty.");

        final String password = credential.getPassword();
        notNull(password, "Password cannot be null.");
        notEmpty(password, "Username cannot be empty.");

        throw new UnsupportedOperationException();

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
    public CredentialData changePassword(CredentialData credential) {
        return add(credential);
    }

    @Override
    public int delete(String id) {
        notNull(id, "Parameter \"id\" cannot be null");
        notEmpty(id, "Parameter \"id\" cannot be empty");
        return credentialPersistence.delete(id);
    }

}

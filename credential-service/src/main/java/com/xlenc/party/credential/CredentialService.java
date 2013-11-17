package com.xlenc.party.credential;

/**
 * User: Michael Williams
 * Date: 10/24/12
 * Time: 1:46 AM
 */
public interface CredentialService {

    boolean authenticate(CredentialData credential);

    CredentialData add(CredentialData credential);

    CredentialData findByUsername(String username);

    CredentialData findById(String id);

    int delete(String id);

    CredentialData changePassword(CredentialData credential);
}

package com.xlenc.party.credential;


/**
 * User: Michael Williams
 * Date: 7/7/12
 * Time: 10:17 PM
 */
public interface CredentialPersistence {

    CredentialData addCredential(CredentialData credential);

    CredentialData findByUsername(String username);

    CredentialData findById(String id);

    int delete(String id);

    CredentialData findByExternalKeyValue(String name, String id);

    int updateCredential(CredentialData credential);

}

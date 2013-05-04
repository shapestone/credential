package com.xlenc.party.credential;

import com.xlenc.party.credential.CredentialData;

/**
 * User: Michael Williams
 * Date: 7/7/12
 * Time: 10:17 PM
 */
public interface CredentialPersistence {

    CredentialData add(CredentialData credential);

    CredentialData findByUsername(String username);

    CredentialData findById(String id);

    int delete(String id);

    int updateById(CredentialData credential);
}

package com.xlenc.party.credential;

import com.xlenc.party.credential.CredentialService;
import com.xlenc.party.credential.CredentialData;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * User: Michael Williams
 * Date: 10/26/12
 * Time: 1:55 AM
 */
public class CredentialServiceIT {

    private String id;
    private String username;

    private CredentialService credentialService;

    @Test(enabled = false)
    public void testAddParty() {
        username = "mwilliams";
        final CredentialData credential = new CredentialData(username, "mwilliams");
        final CredentialData add = credentialService.add(credential);
        id = add.getId();
        assertNotNull(id);
        assertEquals(credential.getUsername(), credential.getUsername());
        assertEquals(credential.getPassword(), credential.getPassword());
    }

    @Test(enabled = false)
    public void testFindByUsername() {
        final CredentialData add = credentialService.findByUsername(username);
        id = add.getId();
        assertNotNull(id);
        assertEquals(username, add.getUsername());

    }

}

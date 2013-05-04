package com.xlenc.party.credential;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * User: Michael Williams
 * Date: 10/25/12
 * Time: 12:18 AM
 */
public class CredentialServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialServiceTest.class);

    private CredentialService credentialService;
    private CredentialPersistence credentialPersistence;

    @BeforeClass
    public void setUp() {
        try {
            this.credentialPersistence = mock(CredentialPersistence.class);
            this.credentialService = new CredentialServiceImpl();
            Class<? extends CredentialService> aClass = this.credentialService.getClass();
            Field credentialPersistenceField = aClass.getDeclaredField("credentialPersistence");
            credentialPersistenceField.setAccessible(true);
            credentialPersistenceField.set(this.credentialService, credentialPersistence);
        } catch (NoSuchFieldException e) {
            LOGGER.error("Exception Caught: ", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Exception Caught: ", e);
        }
    }

    // Add

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddWithNullCredential() {

        credentialService.add(null);

        fail("Cannot create a credential with a null null.");

    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddCredentialWithNullUsername() {

        credentialService.add(new CredentialData(null, "mwilliams"));

        fail("Cannot create a credential with a null password.");

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddCredentialWithNullPassword() {

        credentialService.add(new CredentialData("mwilliams", null));

        fail("Cannot create a credential with a null password.");

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddCredentialWithEmptyUsername() {

        credentialService.add(new CredentialData("", ""));

        fail("Cannot create a credential with a empty password.");

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddCredentialWithEmptyPassword() {

        credentialService.add(new CredentialData("", ""));

        fail("Cannot create a credential with a empty password.");

    }

    @Test
    public void testAddCredential() {
        final CredentialData credential = new CredentialData("mwilliams", "mwilliams");

        when(credentialPersistence.add(any(CredentialData.class))).thenReturn(credential);

        final CredentialData add = credentialService.add(credential);

        assertEquals(credential.getUsername(), add.getUsername());
        assertEquals(credential.getPassword(), add.getPassword());
    }

    // Authenticate

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAuthenticateWithNullCredential() {

        credentialService.authenticate(null);

        fail("Cannot authenticate a credential with a null password.");

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAuthenticateCredentialWithNullUsername() {

        credentialService.authenticate(new CredentialData(null, "mwilliams"));

        fail("Cannot authenticate a credential with a null password.");

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAuthenticateCredentialWithNullPassword() {

        credentialService.authenticate(new CredentialData("mwilliams", null));

        fail("Cannot authenticate a credential with a null password.");

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAuthenticateCredentialWithEmptyUsername() {

        credentialService.authenticate(new CredentialData("", ""));

        fail("Cannot authenticate a credential with a empty password.");

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAuthenticateCredentialWithEmptyPassword() {

        credentialService.authenticate(new CredentialData("", ""));

        fail("Cannot authenticate a credential with a empty password.");

    }

    @Test
    public void testAuthenticate() {
        final String name = "mwilliams";

        final CredentialData credential = new CredentialData();
        credential.setUsername(name);
        credential.setPassword(BCrypt.hashpw(name, BCrypt.gensalt()));

        when(credentialPersistence.findByUsername(anyString())).thenReturn(credential);

        final CredentialData cred = new CredentialData();
        cred.setUsername(name); cred.setPassword(name);
        assertTrue(credentialService.authenticate(cred));
    }

    // Find By Username

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByUsernameWithNull() {

        credentialService.findByUsername(null);

        fail("Cannot \"findByUsername\" with null");
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByUsernameWithEmpty() {

        credentialService.findByUsername("");

        fail("Cannot \"findByUsername\" with null");
    }

    @Test
    public void testFindByUsername() {

        final String username = "myusername";
        final CredentialData credential = new CredentialData(username);

        when(credentialPersistence.findByUsername(anyString())).thenReturn(credential);

        CredentialData readCredential = credentialService.findByUsername(username);

        assertEquals(username, readCredential.getUsername());

    }

    // Find By Id

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByIdWithNull() {

        credentialService.findById(null);

        fail("Cannot \"findById\" with null");
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByIdWithEmpty() {

        credentialService.findById("");

        fail("Cannot \"findById\" with null");
    }

    @Test
    public void testFindById() {

        final String id = "50810211744e9ba605a4d257";

        final CredentialData credential = new CredentialData();
        credential.setId(id);

        when(credentialPersistence.findById(anyString())).thenReturn(credential);

        CredentialData readCredential = credentialService.findById(id);

        assertEquals(id, readCredential.getId());

    }

    // Delete

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteWithNull() {

        credentialService.delete(null);

        fail("Cannot \"delete\" with null");
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteWithEmpty() {

        credentialService.delete("");

        fail("Cannot \"delete\" with null");
    }

    @Test
    public void testDelete() {

        final String id = "50810211744e9ba605a4d257";

        final int affected = 1;

        when(credentialPersistence.delete(anyString())).thenReturn(affected);

        final int aff = credentialService.delete(id);

        assertEquals(affected, aff);

    }

}

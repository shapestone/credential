package com.xlenc.party.credential;

import com.google.code.morphia.annotations.*;
import lombok.Data;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

/**
 * User: Michael Williams
 * Date: 7/7/12
 * Time: 9:52 PM
 */
@Entity("credentials")
@ToString
public @Data
class CredentialData {

    @Id
    private String id;
    @Indexed(unique = true)
    @Property("username")
    private String username;
    @Transient
    private String password;
    @JsonIgnore
    @Property("password")
    private String hashedPassword;
    @Property("status")
    private String status;
    @Property("verification_token")
    private String verificationToken;
    @Embedded("external_identifiers")
    private List<ExternalIdentifier> externalIdentifiers;
    @Version
    private Long version;

    public CredentialData() {
    }

    public CredentialData(final String username) {
        this.username = username;
    }

    public CredentialData(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

}

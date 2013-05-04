package com.xlenc.party.credential;

import com.google.code.morphia.annotations.*;
import lombok.Data;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

/**
 * User: Michael Williams
 * Date: 7/7/12
 * Time: 9:52 PM
 */
@Entity("credentials")
public @Data
class CredentialData {

    @Id
    @JsonIgnore
    private ObjectId objectId;
    @Transient
    private String id;
    @Property("username")
    private String username;
    @Transient
    private String password;
    @Property("password")
    private String hashedPasswrod;
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

    public CredentialData(String username) {
        this.username = username;
    }

    public CredentialData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @PrePersist
    private void prePersist() {
        if (id != null) {
            this.objectId = new ObjectId(id);
        }
    }

    @PostLoad
    private void postLoad() {
        if (objectId != null) {
            this.id = objectId.toString();
        }
    }

}

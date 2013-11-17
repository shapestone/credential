package com.xlenc.party.credential;

import com.google.code.morphia.Key;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;

/**
 * User: Michael Williams
 * Date: 7/7/12
 * Time: 10:18 PM
 */
public class CredentialPersistenceImpl extends BasicDAO<CredentialData, Object> implements CredentialPersistence {

    public CredentialPersistenceImpl(Mongo mongo, Morphia morphia, String databaseName) {
        super(mongo, morphia, databaseName);
    }

    public CredentialData add(CredentialData credentialData) {
        credentialData.setId(new ObjectId().toString());
        final Key<CredentialData> newCredential = super.save(credentialData, WriteConcern.SAFE);
        Object id = newCredential.getId();
        credentialData.setId(id.toString());
        return credentialData;
    }

    public CredentialData findByUsername(String username) {
        return super.findOne("username", username);
    }

    public CredentialData findById(String id) {
        return super.findOne(Mapper.ID_KEY, id);
    }

    public int delete(String id) {
        CredentialData credential = new CredentialData();
        credential.setId(id);
        WriteResult delete = super.delete(credential);
        return delete.getN();
    }

}

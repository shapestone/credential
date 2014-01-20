package com.xlenc.party.credential;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.code.morphia.query.UpdateResults;
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

    public CredentialData addCredential(CredentialData credentialData) {
        credentialData.setId(new ObjectId().toString());
        super.save(credentialData, WriteConcern.SAFE);
        return credentialData;
    }

    public CredentialData findByUsername(String username) {
        return super.findOne("username", username);
    }

    public CredentialData findById(String id) {
        return super.findOne(Mapper.ID_KEY, id);
    }

    public int delete(String id) {
        final CredentialData credential = new CredentialData();
        credential.setId(id);
        final WriteResult delete = super.delete(credential);
        return delete.getN();
    }

    public CredentialData findByExternalKeyValue(String name, String id) {
        Query<CredentialData> query = super.createQuery();
        query.and(
            query.criteria("external_identifiers.name").equal(name),
            query.criteria("external_identifiers.id").equal(id)
        );
        return query.get();
    }

    @Override
    public int updateCredential(CredentialData credential) {
        final UpdateOperations<CredentialData> updateOperations = super.createUpdateOperations();
        updateOperations.set("username", credential.getUsername());
        updateOperations.set("hashedPassword", credential.getHashedPassword());
        Query<CredentialData> query = super.createQuery();
        query.and(
            query.criteria("_id").equal(credential.getId()),
            query.criteria("version").equal(credential.getVersion())
        );
        UpdateResults<CredentialData> update = super.update(query, updateOperations);
        return update.getUpdatedCount();
    }

}

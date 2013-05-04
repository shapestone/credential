package com.xlenc.party.credential;

import com.google.code.morphia.Key;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * User: Michael Williams
 * Date: 7/7/12
 * Time: 10:18 PM
 */
public class CredentialPersistenceImpl implements CredentialPersistence {

    private BasicDAO<CredentialData, Object> credentialBasicDAO;

    public CredentialPersistenceImpl(BasicDAO<CredentialData, Object> credentialBasicDAO) {
        this.credentialBasicDAO = credentialBasicDAO;
    }
    public CredentialData add(CredentialData credential) {
        final Key<CredentialData> newCredential = credentialBasicDAO.save((CredentialData)credential, WriteConcern.SAFE);
        Object id = newCredential.getId();
        credential.setId(id.toString());
        return credential;
    }

    public CredentialData findByUsername(String username) {
        return credentialBasicDAO.findOne("username", username);
    }

    public CredentialData findById(String id) {
        return credentialBasicDAO.findOne( "_id", id);
    }

    @Override
    public int delete(String id) {
        CredentialData credential = new CredentialData();
        credential.setId(id);
        WriteResult delete = credentialBasicDAO.delete(credential);
        return delete.getN();
    }

    @Override
    public int updateById(CredentialData credential) {
        Query<CredentialData> updateQuery = credentialBasicDAO.createQuery().field(Mapper.ID_KEY).equal(credential.getId());
        UpdateOperations<CredentialData> updateOperations = credentialBasicDAO.createUpdateOperations();

//        return changePassword(updateQuery, );
        throw new UnsupportedOperationException();
    }

}

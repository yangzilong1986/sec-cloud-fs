package org.avasquez.seccloudfs.processing.db.repos.impl;

import com.mongodb.MongoException;

import org.avasquez.seccloudfs.db.impl.JongoRepository;
import org.avasquez.seccloudfs.exception.DbException;
import org.avasquez.seccloudfs.processing.db.model.EncryptionKey;
import org.avasquez.seccloudfs.processing.db.repos.EncryptionKeyRepository;
import org.jongo.Jongo;

/**
 * Jongo repository for {@link org.avasquez.seccloudfs.processing.db.model.EncryptionKey}.
 *
 * @author avasquez
 */
public class JongoEncryptionKeyRepository extends JongoRepository<EncryptionKey> implements EncryptionKeyRepository {

    public static final String ENC_KEY_COLLECTION_NAME = "encryptionKeys";
    public static final String ENC_KEY_COLLECTION_INDEX_KEYS = "{dataId: 1}";
    public static final String FIND_BY_DATA_ID_QUERY = "{dataId: #}";

    public JongoEncryptionKeyRepository(Jongo jongo) {
        super(ENC_KEY_COLLECTION_NAME, jongo);

        collection.ensureIndex(ENC_KEY_COLLECTION_INDEX_KEYS);
    }

    @Override
    public Class<EncryptionKey> getPojoClass() {
        return EncryptionKey.class;
    }

    @Override
    public EncryptionKey findByDataId(final String dataId) throws DbException {
        try {
            return collection.findOne(FIND_BY_DATA_ID_QUERY, dataId).as(EncryptionKey.class);
        } catch (MongoException e) {
            throw new DbException("[" + collection.getName() + "] Find by data ID '" + dataId + "' failed", e);
        }
    }

    @Override
    public void deleteByDataId(final String dataId) throws DbException {
        try {
            collection.remove(FIND_BY_DATA_ID_QUERY, dataId);
        } catch (MongoException e) {
            throw new DbException("[" + collection.getName() + "] Find by data ID '" + dataId + "' failed", e);
        }
    }

}

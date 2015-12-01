package org.wrl.samples.mongo.dao.impl;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.wrl.samples.mongo.dao.IMongoDBDao;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-12-01 16:13
 */
public class MongoDBDaoImpl implements IMongoDBDao {
    private MongoClient mongoClient;

    @Override
    public MongoDatabase getDatebase(String dbName) {

        return mongoClient.getDatabase(dbName);
    }

    @Override
    public <TDocument> MongoCollection<TDocument> getCollection(String dbName, String collectionName, Class<TDocument> documentClass) {
        return getDatebase(dbName).getCollection(collectionName, documentClass);
    }
}

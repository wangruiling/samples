package org.wrl.samples.mongo.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-12-01 15:58
 */
public interface IMongoDBDao {
    /**
     * 获取指定的mongodb数据库
     * @param dbName
     * @return
     */
    MongoDatabase getDatebase(String dbName);

    /**
     * 获取指定mongodb数据库的collection集合
     * @param dbName 数据库名
     * @param collectionName 数据库集合
     * @return
     */
    <TDocument> MongoCollection<TDocument> getCollection(String dbName, String collectionName, Class<TDocument> documentClass);
}

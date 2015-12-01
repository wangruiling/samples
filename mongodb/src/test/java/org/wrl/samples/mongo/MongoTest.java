package org.wrl.samples.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Sorts.descending;

/**
 * Created with IntelliJ IDEA.
 * Mongo原生Java驱动测试类
 * http://mongodb.github.io/mongo-java-driver/3.0/driver/getting-started/
 * @author: wangrl
 * @Date: 2015-11-27 13:17
 */
public class MongoTest {
    protected static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    protected static MongoClient mongoClient;
    private MongoDatabase database;

    @BeforeClass
    public static void setUpClass() {
        mongoClient = getMongoClient2();
    }

    @Before
    public void setUp() {
        database = getDataBase("mydb");
    }

    @AfterClass
    public static void setDownClass() {
        mongoClient.close();
    }

    @Test
    public void testListDatabases() {
        //You can get a list of the available databases
        for (String name: mongoClient.listDatabaseNames()) {
            System.out.println(name);
        }
    }

    @Test
    public void testListCollections() {
        //You can get a list of the available collections in a database
        for (String name: database.listCollectionNames()) {
            System.out.println(name);
        }
    }

    @Test
    public void testCreateCollection() {
        //Using the createCollection method, you can also create a collection explicitly in order to to customize its configuration.
        // For example, to create a capped collection sized to 1 megabyte:
        database.createCollection("test", new CreateCollectionOptions().capped(true).sizeInBytes(0x100000));
    }


    @Test
    public void testGetCollection() {
        // get a handle to the "test" collection
        MongoCollection<Document> collection = database.getCollection("test");
        logger.info("collection:{}", collection.count());
    }

    /**
     * MongoDB supports secondary indexes. To create an index, you just specify the field or combination of fields, and for each field specify the direction of the index for that field.
     * For 1 ascending or -1 for descending
     */
    @Test
    public void testCreateIndex() {
        MongoCollection<Document> collection = database.getCollection("test");

        //The following creates an ascending index on the i field:
        // create an ascending index on the "i" field
        collection.createIndex(new Document("i", 1));
    }

    /**
     * MongoDB also provides text indexes to support text search of string content. Text indexes can include any field whose value is a string or an array of string elements.
     * To create a text index specify the string literal “text” in the index document
     */
    @Test
    public void testCreateTextIndex() {
        MongoCollection<Document> collection = database.getCollection("test");

        // create a text index on the "content" field
        collection.createIndex(new Document("content", "text"));

        // Insert some documents
        collection.insertOne(new Document("_id", 0).append("content", "textual content"));
        collection.insertOne(new Document("_id", 1).append("content", "additional content"));
        collection.insertOne(new Document("_id", 2).append("content", "irrelevant content"));

        // Find using the text index
        long matchCount = collection.count(Filters.text("textual content -irrelevant"));
        System.out.println("Text search matches: " + matchCount);

        // Find using the $language operator
        Bson textSearch = Filters.text("textual content -irrelevant", "english");
        matchCount = collection.count(textSearch);
        System.out.println("Text search matches (english): " + matchCount);

        // Find the highest scoring match
        Document projection = new Document("score", new Document("$meta", "textScore"));
        Document myDoc = collection.find(textSearch).projection(projection).first();
        System.out.println("Highest scoring document: " + myDoc.toJson());
    }

    @Test
    public void testGetIndexes() {
        MongoCollection<Document> collection = database.getCollection("test");

        //The following lists the indexes on the collection test
        for (final Document index : collection.listIndexes()) {
            System.out.println(index.toJson());
        }
    }

    @Test
    public void testRunCommand() {
        Document buildInfo = database.runCommand(new Document("buildInfo", 1));
        System.out.println(buildInfo);
    }

    @Test
    public void testInsert() {
        // make a document and insert it
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));

        MongoCollection<Document> collection = database.getCollection("test");
        collection.insertOne(doc);

        // get it (since it's the only one in there since we dropped the rest earlier on)
        Document myDoc = collection.find().first();
        logger.info("collection:{}", collection.count());
        System.out.println(myDoc.toJson());
    }

    @Test
    public void testInsertMultiple() {
        // now, lets add lots of little documents to the collection so we can explore queries and cursors
        List<Document> documents = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document("i", i));
        }

        MongoCollection<Document> collection = database.getCollection("test");
        collection.insertMany(documents);
        System.out.println("total # of documents after inserting 100 small ones (should be 101) " + collection.count());
    }

    @Test
    public void testFindFirst() {
        MongoCollection<Document> collection = database.getCollection("test");

        // find first
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
    }

    /**
     * Find All Documents in a Collection
     */
    @Test
    public void testFindAll() {
        MongoCollection<Document> collection = database.getCollection("test");

        // lets get all the documents in the collection and print them out
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        //不建议使用下面这种方式(Although the following idiom is permissible, its use is discouraged as the application can leak a cursor if the loop terminates early)
        for (Document cur : collection.find()) {
            System.out.println(cur.toJson());
        }
    }

    @Test
    public void testQueryFilter() {
        MongoCollection<Document> collection = database.getCollection("test");

        // now use a query to get 1 document out
        Document myDoc = collection.find(eq("i", 71)).first();
        System.out.println(myDoc.toJson());

        System.out.println("-------------------------------------------------------");

        // now use a range query to get a larger subset
        try (MongoCursor<Document> cursor = collection.find(gt("i", 50)).iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }

        System.out.println("-------------------------------------------------------");

        // range query with multiple constraints
        try (MongoCursor<Document> cursor = collection.find(and(gt("i", 50), lte("i", 60))).iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }
    }

    @Test
    public void testQueryFilters() {
        MongoCollection<Document> collection = database.getCollection("test");

        // now use a range query to get a larger subset
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };

        //We can use the query to get a set of documents from our collection. For example, if we wanted to get all documents where "i" > 50, we could write
        collection.find(gt("i", 50)).forEach(printBlock);

        //We could also get a range, say 50 < i <= 80:
        collection.find(and(gt("i", 50), lte("i", 80))).forEach(printBlock);
    }

    @Test
    public void testSorting() {
        MongoCollection<Document> collection = database.getCollection("test");

        // Sorting
        Document myDoc = collection.find(exists("i")).sort(descending("i")).first();
        System.out.println(myDoc.toJson());
    }

    @Test
    public void testProjection() {
        MongoCollection<Document> collection = database.getCollection("test");

        // Sometimes we don’t need all the data contained in a document, the Projections helpers help build the projection parameter for the find operation. Below we’ll sort the collection, exclude the _id field and output the first matching document
        Document myDoc = collection.find().projection(excludeId()).first();
        System.out.println(myDoc.toJson());
    }

    @Test
    public void testUpdate() {
        MongoCollection<Document> collection = database.getCollection("test");

        // Update One
        collection.updateOne(eq("i", 10), new Document("$set", new Document("i", 110)));

        // Update Many
        UpdateResult updateResult = collection.updateMany(lt("i", 100), new Document("$inc", new Document("i", 100)));
        System.out.println(updateResult.getModifiedCount());
    }

    @Test
    public void testDelete() {
        MongoCollection<Document> collection = database.getCollection("test");

        // Delete One
        collection.deleteOne(eq("i", 110));

        // Delete Many
        DeleteResult deleteResult = collection.deleteMany(gte("i", 100));
        System.out.println(deleteResult.getDeletedCount());
    }

    @Test
    public void testDeleteAll() {
        MongoCollection<Document> collection = database.getCollection("test");

        // Delete All
        DeleteResult deleteResult = collection.deleteMany(all("_id"));
        System.out.println(deleteResult.getDeletedCount());
    }

    @Test
    public void testDropCollection() {
        MongoCollection<Document> collection = database.getCollection("test");
        //从数据库中清楚test集合
        collection.drop();
    }

    @Test
    public void testDropDatabase() {
        //删除数据库
        database.drop();
    }

    @Test
    public void testBulk() {
        MongoCollection<Document> collection = database.getCollection("test");

        List<WriteModel<Document>> writes = new ArrayList<>();
        writes.add(new InsertOneModel<>(new Document("_id", 4)));
        writes.add(new InsertOneModel<>(new Document("_id", 5)));
        writes.add(new InsertOneModel<>(new Document("_id", 6)));
        writes.add(new UpdateOneModel<>(new Document("_id", 1), new Document("$set", new Document("x", 2))));
        writes.add(new DeleteOneModel<>(new Document("_id", 2)));
        writes.add(new ReplaceOneModel<>(new Document("_id", 3), new Document("_id", 3).append("x", 4)));

        // 1. Ordered bulk operation - order is guarenteed
        collection.bulkWrite(writes);

        collection.drop();

        // 2. Unordered bulk operation - no guarantee of order of operation
        collection.bulkWrite(writes, new BulkWriteOptions().ordered(false));//collection.find().forEach(printBlock);
    }



    private MongoDatabase getDataBase(String db) {
        return getMongoClient2().getDatabase(db);
    }

    private static MongoClient getMongoClient() {
        return new MongoClient();
    }

    private static MongoClient getMongoClient2() {
        MongoClientOptions.builder();
        return new MongoClient("localhost");
    }

    private static MongoClient getMongoClient3() {
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017,localhost:27018,localhost:27019");
        return new MongoClient(connectionString);
    }

    private static MongoClient getMongoClientReplica() {
        return new MongoClient(
                Arrays.asList(new ServerAddress("localhost", 27017),
                        new ServerAddress("localhost", 27018),
                        new ServerAddress("localhost", 27019)));
    }
}

package com.enlightendev.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class UpdateRemoveTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoCollection<Document> collection = createCollection();

        List<String> names = Arrays.asList("alice", "bobby", "cathy", "david", "ethan");
        for (String name : names) {
            collection.insertOne(new Document("_id", name));
        }

        // see scratch method

        printCollection(collection);
    }

    // these are all the statement I used throughout the lecture.
    private static void scratch(DBCollection collection) {
        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject("age", 24));

        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject("$set", new BasicDBObject("age", 24)));

        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject(new BasicDBObject("gender", "F")));

        collection.update(new BasicDBObject("_id", "frank"),
                new BasicDBObject("$set", new BasicDBObject("age", 24)), true, false);

        collection.update(new BasicDBObject(),
                new BasicDBObject("$set", new BasicDBObject("title", "Dr")), false, true);

        collection.remove(new BasicDBObject("_id", "frank"));
    }

    private static MongoCollection<Document> createCollection() throws UnknownHostException {
        MongoClient client = new MongoClient();
        MongoDatabase courseDB = client.getDatabase("course");
        MongoCollection<Document> collection = courseDB.getCollection("UpdateRemoveTest");
        collection.drop();
        return collection;
    }

    private static void printCollection(final MongoCollection<Document> collection) {

        MongoCursor<Document> cursor =
                collection.find().sort(new Document("_id", 1)).iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

    }
}

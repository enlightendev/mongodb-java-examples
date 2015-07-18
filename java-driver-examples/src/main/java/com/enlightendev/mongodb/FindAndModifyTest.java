package com.enlightendev.mongodb;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;

import java.net.UnknownHostException;

public class FindAndModifyTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoCollection<Document> collection = createCollection();
        collection.drop();

        final String counterId = "abc";
        int first;
        int numNeeded;

        numNeeded = 2;
        first = getRange(counterId, numNeeded, collection);
        System.out.println("Range: " + first + "-" + (first + numNeeded - 1));

        numNeeded = 3;
        first = getRange(counterId, numNeeded, collection);
        System.out.println("Range: " + first + "-" + (first + numNeeded - 1));

        numNeeded = 10;
        first = getRange(counterId, numNeeded, collection);
        System.out.println("Range: " + first + "-" + (first + numNeeded - 1));

        System.out.println();

        printCollection(collection);
    }

    private static int getRange(String id, int range, MongoCollection<Document> collection) {

        Document doc = collection.findOneAndUpdate(
                new Document("_id", id),
                new Document("$inc", new Document("counter", range)),
                new FindOneAndUpdateOptions());

        return (Integer) doc.get("counter") - range + 1;
   }

    private static MongoCollection<Document> createCollection() throws UnknownHostException {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        return db.getCollection("FindModifyTest");
    }

    private static void printCollection(final MongoCollection<Document> collection) {
        FindIterable<Document> cursor = collection.find().sort(new Document("_id", 1));

        cursor.forEach(new Block<Document>() {

            public void apply(final Document document) {
                System.out.println(document);
            }
        });
    }
}

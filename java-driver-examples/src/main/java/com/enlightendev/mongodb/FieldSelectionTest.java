package com.enlightendev.mongodb;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;

public class FieldSelectionTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("fieldSelectionTest");
        collection.drop();
        Random rand = new Random();

        // insert 10 documents with two random integers
        for (int i = 0; i < 10; i++) {
            collection.insertOne(
                    new Document("x", rand.nextInt(2))
                            .append("y", rand.nextInt(100))
                            .append("z", rand.nextInt(1000)));
        }

        MongoCursor<Document> cursor = collection.find(
                and(
                        eq("x",0),
                        and(
                                gt("y",10), lt("y",70)
                        )
                )).iterator();

        try {
            while (cursor.hasNext()) {
                //DBObject cur = cursor.next();
                String docJson = cursor.next().toJson();
                System.out.println(docJson);
            }
        } finally {
            cursor.close();
        }
    }
}

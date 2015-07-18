package com.enlightendev.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;

public class DotNotationTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("DotNotationTest");
        collection.drop();

        Random rand = new Random();

        // insert 10 lines with random start and end points
        for (int i = 0; i < 10; i++) {
            collection.insertOne(
                    new Document("_id", i)
                            .append("start",
                                    new Document("x", rand.nextInt(90) + 10).append("y", rand.nextInt(90) + 10)
                            )
                            .append("end",
                                    new Document("x", rand.nextInt(90) + 10).append("y", rand.nextInt(90) + 10)
                            )
            );
        }

        MongoCursor<Document> cursor = collection.find(gt("start.x", 50)).iterator();

        try {
            while (cursor.hasNext()) {
                String doc = cursor.next().toJson();
                System.out.println(doc);
            }
        } finally {
            cursor.close();
        }
    }
}

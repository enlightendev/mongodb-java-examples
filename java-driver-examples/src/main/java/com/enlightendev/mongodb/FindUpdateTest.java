package com.enlightendev.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.gt;

/**
 *
 */
public class FindUpdateTest {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("people");
        collection.drop();

        // insert 10 documents with a random integer as the value of field "x"
        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document("x", new Random().nextInt(100)));
        }

        System.out.println("Find one:");
        Document one = collection.find().first();
        System.out.println(one);

        System.out.println("\nFind all: ");
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
          while (cursor.hasNext()) {
              String doc = cursor.next().toJson();
              System.out.println(doc);
          }
        } finally {
            cursor.close();
        }

        /**
         * here we search using filters
         * and then update each returned document using findOneAndUpdate
         * update operators: http://docs.mongodb.org/manual/reference/operator/update/
         */
        List<Document> results = collection.find(gt("x", 95)).into(new ArrayList<Document>());
        for (Document cur : results) {

            System.out.println("X>95=:" + cur.get("x") + " id: " + cur.get("_id"));

            Document doc = collection.findOneAndUpdate(
                    cur,
                    new Document("$set", new Document("x", 9999))
            );

        }

        System.out.println("\nCount:");
        long count = collection.count();
        System.out.println(count);
    }
}
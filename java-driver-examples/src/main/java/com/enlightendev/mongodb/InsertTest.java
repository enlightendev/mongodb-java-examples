package com.enlightendev.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;

public class InsertTest {
    public static void main(String[] args) throws UnknownHostException {

        MongoClient client = new MongoClient();
        MongoDatabase courseDB = client.getDatabase("course");
        MongoCollection<Document> collection = courseDB.getCollection("insertTest");

        collection.drop();

        Document doc = new Document();
        doc.put("userName", "jyemin");
        doc.put("birthDate", new Date(234832423));
        doc.put("programmer", true);
        doc.put("age", 8);
        doc.put("languages", Arrays.asList("Java", "C++"));
        doc.put("address", new Document("street", "20 Main")
                .append("town", "Westfield")
                .append("zip", "56789"));

        collection.insertOne(doc);

        Document doc2 = new Document().append("x", 1);

        collection.insertOne(doc2);

    }
}
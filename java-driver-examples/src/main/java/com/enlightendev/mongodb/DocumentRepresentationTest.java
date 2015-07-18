package com.enlightendev.mongodb;

import org.bson.Document;

import java.util.Arrays;
import java.util.Date;

public class DocumentRepresentationTest {
    public static void main(String[] args) {

        Document doc = new Document();
        doc.put("userName", "jyemin");
        doc.put("birthDate", new Date(234832423));
        doc.put("programmer", true);
        doc.put("age", 8);
        doc.put("languages", Arrays.asList("Java", "C++"));
        doc.put("address", new Document("street", "20 Main")
                .append("town", "Westfield")
                .append("zip", "56789"));
    }
}

package app;

import java.util.Arrays;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

public class Connect {
	public static void main(String[] args) {
        
        String uri = "mongodb+srv://hoanglong19516141:1234@longcluster.sfmcvev.mongodb.net/?retryWrites=true&w=majority";
        
        MongoClient mongoClient = MongoClients.create(uri );
		
		MongoDatabase database = mongoClient.getDatabase("BikeStores");
		
		MongoCollection<Document> customerDoc = database.getCollection("customers");
        
		Bson match = Aggregates.match(Filters.eq("first_name", "Debra"));
		Bson count = Aggregates.count("n");
		
		AggregateIterable<Document> its = customerDoc.aggregate(Arrays.asList(match, count));
		its.forEach(it -> System.out.println(it));

    }
}







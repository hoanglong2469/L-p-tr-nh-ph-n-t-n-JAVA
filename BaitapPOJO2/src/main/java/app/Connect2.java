package app;


import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

public class Connect2 {
	public static void main(String[] args) {


		String uri = "mongodb+srv://dbAdmin:abc123456@khanhcluster.qxnzc5d.mongodb.net/?retryWrites=true&w=majority"  ;
		MongoClient mongoClient = MongoClients.create(uri );
		
		MongoDatabase database = mongoClient.getDatabase("sample_mflix");
		
		MongoCollection<Document> commentDoc = database.getCollection("comments");
		
//		db.comments.aggregate([ { $match: { name: 'Lisa Rasmussen' } },{$count:'n'}])
		
//		Document match = new Document("$match", new Document("name", "Lisa Rasmussen"));
//		Document count = new Document("$count", "n");
		
//		Document match = Document.parse("{ $match: { name: \"Lisa Rasmussen\" } }");
//		Document count = Document.parse("{$count:'n'}");
		
		Bson match = Aggregates.match(Filters.eq("name", "Lisa Rasmussen"));
		Bson count = Aggregates.count("n");
		
		AggregateIterable<Document> its = commentDoc.aggregate(Arrays.asList(match, count));
		its.forEach(it -> System.out.println(it));
	}
}







package util;

import java.util.concurrent.TimeUnit;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class AtlasMongoClient {
	private static AtlasMongoClient instane = null;
	
	private MongoClient mongoClient;
	
	private AtlasMongoClient() {
//		String uri = "your uri";
//		ConnectionString connectionString = new ConnectionString(uri );
//		MongoClientSettings clientSettings = 
//				MongoClientSettings
//				.builder()
//				.applicationName("Exercise")
//				.applyConnectionString(connectionString)
//				.applyToConnectionPoolSettings(builder -> builder.maxSize(0))
//				.applyToSocketSettings(builder -> builder.connectTimeout(5, TimeUnit.SECONDS))
//				.build();
		
		mongoClient = MongoClients.create();
	}
	
	public static synchronized AtlasMongoClient getInstane() {
		if(instane == null)
			instane = new AtlasMongoClient();
		return instane;
	}
	
	public MongoClient getMongoClient() {
		return mongoClient;
	}
	
}

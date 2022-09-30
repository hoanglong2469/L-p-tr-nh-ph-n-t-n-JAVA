package app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import entity.Customer;
import service.CustomerService;
import service.impl.CustomerServiceImpl;
import util.AtlasMongoClient;

public class App {
	public static void main(String[] args) {
		
		MongoClient mongoClient = AtlasMongoClient.getInstane().getMongoClient();
		
		mongoClient.listDatabaseNames()
		.forEach(dbName -> System.out.println(dbName));
	
		
	}
}

package dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;

import entity.Customer;
import util.AtlasMongoClient;

public class CustomerDao{

	private MongoCollection<Customer> customerCol;
	
	public CustomerDao() {
		
		PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(pojoCodecProvider));
		
		
		customerCol = AtlasMongoClient
				.getInstane()
				.getMongoClient()
				.getDatabase("BikeStores")
				.getCollection("customers", Customer.class).withCodecRegistry(codecRegistry) ;
	}
	
	//Them Customer
	public boolean addCustomer(Customer cus) {
		InsertOneResult result = customerCol.insertOne(cus);
		return result.getInsertedId() != null;
	}

}

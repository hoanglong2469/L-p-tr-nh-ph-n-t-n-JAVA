package app;

import com.mongodb.client.MongoClient;

import dao.ZipDao;
import util.AtlasMongoClient;

public class App {
	public static void main(String[] args) {
		
		MongoClient mongoClient = AtlasMongoClient.getInstane().getMongoClient();
		
		mongoClient.listDatabaseNames()
		.forEach(dbName -> System.out.println(dbName));
		
		ZipDao zipDao = new ZipDao();
		
		System.out.println(zipDao.getZipByCity("PALMER").size());
		
		zipDao.getZipByCity("PALMER").forEach(doc -> System.out.println(doc));
		
	}
}

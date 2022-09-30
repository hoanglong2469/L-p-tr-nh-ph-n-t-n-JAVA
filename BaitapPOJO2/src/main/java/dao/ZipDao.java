package dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;

import entity.Zip;
import util.AtlasMongoClient;

public class ZipDao {
	private MongoCollection<Zip> zipCol;
	
	public ZipDao() {
		
		PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(pojoCodecProvider));
		
		
		zipCol = AtlasMongoClient
				.getInstane()
				.getMongoClient()
				.getDatabase("sample_training")
				.getCollection("zips", Zip.class).withCodecRegistry(codecRegistry) ;
	}
	
	
//	Tìm các document có city là PALMER
	public List<Zip> getZipByCity(String city) {
		
		return zipCol.find(eq("city", city)).into(new ArrayList<>());
	}
	
	public Zip getZip(String id) {
		
		return zipCol.find(Filters.eq("_id", new ObjectId(id))).first();
	}
	
//	Tìm các document có dân số >100000
//	db.zips.find({pop:{$gt:100000}})
	public List<Zip> getZipByPop(int pop) {
		
		Document filter = Document.parse("{pop:{$gt:"+ pop +"}}");
		
//		Document filter = new Document()
//				.append("pop", new Document().append("$gt", pop));
				
		return zipCol.find(filter).into(new ArrayList<>());
		
//		return zipCol
//				.find(Filters.gt("pop", pop))
//				.into(new ArrayList<>());
	}
	
	public boolean addZip(Zip doc) {
		InsertOneResult result = zipCol.insertOne(doc);
		return result.getInsertedId() != null;
	}
	
}

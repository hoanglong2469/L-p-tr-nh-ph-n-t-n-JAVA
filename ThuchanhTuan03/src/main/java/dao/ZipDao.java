package dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import util.AtlasMongoClient;

public class ZipDao {
	private MongoCollection<Document> zipCol;

	public ZipDao() {
		zipCol = AtlasMongoClient
				.getInstane()
				.getMongoClient()
				.getDatabase("sample_training")
				.getCollection("zips");
	}


	//	Tìm các document có city là PALMER
	public List<Document> getZipByCity(String city) {



		return zipCol.find(eq("city", city)).into(new ArrayList<>());
	}

	public Document getZip(String id) {

		return zipCol.find(Filters.eq("_id", new ObjectId(id))).first();
	}

	//	Tìm các document có dân số >100000
	//	db.zips.find({pop:{$gt:100000}})
	public List<Document> getZipByPop(int pop) {

		Document filter = Document.parse("{pop:{$gt:"+ pop +"}}");

		//		Document filter = new Document()
		//				.append("pop", new Document().append("$gt", pop));

		return zipCol.find(filter).into(new ArrayList<>());

		//		return zipCol
		//				.find(Filters.gt("pop", pop))
		//				.into(new ArrayList<>());
	}

	//	db.zips.aggregate([{$match:{"city" : "CRANE HILL"}},{$project:{pop:1, _id:0}}])
	//	Tìm dân số của thành phố FISHERS ISLAND

	public int getPopByCity(String city) {
		Document match = Document.parse("{$match:{\"city\" : \""+city+"\"}}");

		Document project = Document.parse("{$project:{pop:1, _id:0}}");
		Document firstDoc = zipCol.aggregate(Arrays.asList(match, project )).first();

		return firstDoc.getInteger("pop");
	}
	
//	db.zips.aggregate([{$match:{"state" : "NY"}},{$group:{_id:null, total:{$sum:'$pop'}}},{$project:{_id:0}}])
	public int getPopByState(String state) {
		Bson match = Aggregates.match(Filters.eq("state", state));
		Bson group = Aggregates.group(Filters.eq("_id",null), Accumulators.sum("total", "$pop"));
		Bson project =Aggregates.project(Projections.include("total"));
		 Document aggregate = zipCol.aggregate(Arrays.asList(match, group, project)).first();
		
		 return aggregate.getInteger("total");
		
	}
	
	

}

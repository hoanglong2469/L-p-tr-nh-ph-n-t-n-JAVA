package bus.impl;

import org.bson.Document;

import entity.Location;
import entity.Zip;

public class Mapper {

	public static Zip fromZip(Document doc) { //decode
		
		Document locDoc = (Document) doc.get("loc");
		Location loc = new Location(locDoc.getDouble("y"), locDoc.getDouble("x"));	
		
		return new Zip(doc.getObjectId("_id").toHexString(), 
				doc.getString("city"), 
				doc.getString("zip"), 
				loc, 
				doc.getInteger("pop"), 
				doc.getString("state"));
	}
	
}

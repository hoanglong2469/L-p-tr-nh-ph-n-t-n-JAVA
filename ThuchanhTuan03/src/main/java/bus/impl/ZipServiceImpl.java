package bus.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

import bus.ZipService;
import dao.ZipDao;
import entity.Zip;

public class ZipServiceImpl implements ZipService{
	
	private ZipDao zipDao;
	
	public ZipServiceImpl() {
		zipDao = new ZipDao();
	}

	@Override
	public List<Zip> getZipByCity(String city) {
		if(city != null && !city.trim().equals("")) {
			List<Document> docs = zipDao.getZipByCity(city);

			return docs.stream().map(doc -> {
				return Mapper.fromZip(doc);
			}).collect(Collectors.toList());
		}
		
		return null;
	}

	@Override
	public Zip getZip(String id) {
		
//		if(id != null && !id.trim().equals("") && ObjectId.isValid(id)) {
			Document doc = zipDao.getZip(id);
			
			Zip zip = Mapper.fromZip(doc);
			return zip;
//		}
//		
//		return null;
	}

	@Override
	public int getPopByCity(String city) {
		if(city != null && !city.equals(""))
			return zipDao.getPopByCity(city);
		
		return 0;
	}
}

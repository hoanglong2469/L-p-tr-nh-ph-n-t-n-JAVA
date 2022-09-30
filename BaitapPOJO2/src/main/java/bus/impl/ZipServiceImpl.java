package bus.impl;

import java.util.List;

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
			List<Zip> docs = zipDao.getZipByCity(city);

			return docs;
		}

		return null;
	}

	@Override
	public Zip getZip(String id) {

		Zip zip = zipDao.getZip(id);
		return zip;
	}

	@Override
	public boolean addZip(Zip zip) {
		return zipDao.addZip(zip);
	}
}




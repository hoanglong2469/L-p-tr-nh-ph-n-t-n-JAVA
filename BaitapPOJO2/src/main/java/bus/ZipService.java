package bus;

import java.util.List;

import entity.Zip;

public interface ZipService {
	public List<Zip> getZipByCity(String city);
	public Zip getZip(String id);
	public boolean addZip(Zip doc) ;
}

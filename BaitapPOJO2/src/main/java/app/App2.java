package app;

import bus.ZipService;
import bus.impl.ZipServiceImpl;
import entity.Location;
import entity.Zip;

public class App2 {
	public static void main(String[] args) {
		Zip zip = new Zip("asda", "sdf", "sdf", new Location(345, 23432), 1000, "LA");
		ZipService zipService = new ZipServiceImpl();
		System.out.println(zipService.addZip(zip));
	}
}

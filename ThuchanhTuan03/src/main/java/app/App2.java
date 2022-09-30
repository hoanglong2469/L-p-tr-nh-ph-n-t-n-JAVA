package app;

import bus.ZipService;
import bus.impl.ZipServiceImpl;

public class App2 {
	public static void main(String[] args) {
		ZipService service = new ZipServiceImpl();
		int pop = service.getPopByCity("CRANE HILL");
		System.out.println(pop);
	}
}

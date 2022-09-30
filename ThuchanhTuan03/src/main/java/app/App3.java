package app;

import dao.ZipDao;

public class App3 {
	public static void main(String[] args) {
		ZipDao zipDao = new ZipDao();
		long pop = zipDao.getPopByState("NY");
		System.out.println(pop);
	}
}

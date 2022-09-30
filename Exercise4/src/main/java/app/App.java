package app;

import java.util.Arrays;
import java.util.Date;

import dao.CustomerDao;
import entity.Address;
import entity.Customer;
import entity.Phone;

public class App {
	public static void main(String[] args) {
		Customer customer = new Customer("1111113", "An", "Nguyen", "an@gmail.com", 
				new Date(), new Address("hcm", "sadasd", "NG Van Bao", 10000), 
				Arrays.asList(new Phone("Fax", "023435345"), new Phone("Mobil", "234435453"))
				);
		
		CustomerDao customerDao = new CustomerDao();
//		customerDao.addCustomer(customer);
		
		//Cách 1
		customerDao.getCustomersPhonesGTE2().forEach(cus -> System.out.println(cus));
		System.out.println("====");
		//Cách 2
		customerDao.getCustomersPhones_GTE2().forEach(cus -> System.out.println(cus));
	}
}

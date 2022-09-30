package app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import entity.Customer;
import service.CustomerService;
import service.impl.CustomerServiceImpl;

public class App2 {
	public static void main(String[] args) {
	
		Date date =null;
		
		Customer cus= new Customer("19516141","huynh","long", "huynhvohoanglong@gmail.com",date);
		CustomerService customerService=new CustomerServiceImpl();
		System.out.println(customerService.addCustomer(cus));
	}
}

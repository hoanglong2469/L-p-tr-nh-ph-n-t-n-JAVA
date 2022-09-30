package service.impl;

import dao.CustomerDao;
import entity.Customer;
import service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

	private CustomerDao customerDao;
	
	public CustomerServiceImpl() {
		customerDao = new CustomerDao();
	}
	
	@Override
	public boolean addCustomer(Customer cus) {
		// TODO Auto-generated method stub
		return customerDao.addCustomer(cus);
	}

}

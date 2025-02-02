package com.ibrahim.bakkalApp.service;

import com.ibrahim.bakkalApp.entity.Customer;
import com.ibrahim.bakkalApp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id).orElse(null);
	}

	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}
}
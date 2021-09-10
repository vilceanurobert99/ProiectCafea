package com.coffe.services.implementation;

import com.coffe.entity.Customer;
import com.coffe.exception.CustomerNotFoundException;
import com.coffe.repository.CustomerRepository;
import com.coffe.services.declaration.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findCustomerByFirstNameAndLastName(String firstName, String lastName) throws CustomerNotFoundException {
        return customerRepository.findCustomerByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found by the given name!"));
    }
}

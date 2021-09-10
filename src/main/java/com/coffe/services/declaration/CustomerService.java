package com.coffe.services.declaration;

import com.coffe.entity.Customer;
import com.coffe.exception.CustomerNotFoundException;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Customer findCustomerByFirstNameAndLastName(String firstName, String lastName) throws CustomerNotFoundException;
}

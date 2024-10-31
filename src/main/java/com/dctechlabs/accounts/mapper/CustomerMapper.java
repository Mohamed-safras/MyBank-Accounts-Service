package com.dctechlabs.accounts.mapper;

import com.dctechlabs.accounts.request.CustomerRequest;
import com.dctechlabs.accounts.entity.Customer;

public class CustomerMapper {
    public static CustomerRequest mapToCustomerDto(Customer customer, CustomerRequest customerRequest){
        customerRequest.setCustomerEmail(customer.getCustomerEmail());
        customerRequest.setCustomerName(customer.getCustomerName());
        customerRequest.setMobileNumber(customer.getMobileNumber());
        return customerRequest;
    }

    public static Customer mapToCustomer(CustomerRequest customerRequest, Customer customer){
        customer.setCustomerEmail(customerRequest.getCustomerEmail());
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setMobileNumber(customerRequest.getMobileNumber());
        return customer;
    }
}

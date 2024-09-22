package com.dctechlabs.accounts.mapper;

import com.dctechlabs.accounts.dto.CustomerDto;
import com.dctechlabs.accounts.entity.Customer;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto){
        customerDto.setCustomerEmail(customer.getCustomerEmail());
        customerDto.setCustomerName(customer.getCustomerName());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto,Customer customer){
        customer.setCustomerEmail(customerDto.getCustomerEmail());
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}

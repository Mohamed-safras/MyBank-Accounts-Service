package com.dctechlabs.accounts.service.impl;

import com.dctechlabs.accounts.constants.AccountsConstants;
import com.dctechlabs.accounts.dto.CustomerDto;
import com.dctechlabs.accounts.entity.Accounts;
import com.dctechlabs.accounts.entity.Customer;
import com.dctechlabs.accounts.exception.CustomerAlreadyExistsException;
import com.dctechlabs.accounts.mapper.CustomerMapper;
import com.dctechlabs.accounts.repository.AccountsRepository;
import com.dctechlabs.accounts.repository.CustomerRepository;
import com.dctechlabs.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * @param customerDto - CustomerDto object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
       Customer customer = CustomerMapper.mapToCustomer(customerDto,new Customer());
       Optional<Customer> optionalCustomer= customerRepository.findByMobileNumber(customerDto.getMobileNumber());
       if(optionalCustomer.isPresent()){
           throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "
                   + customerDto.getMobileNumber());
       }
       customer.setCreatedAt(LocalDateTime.now());
       customer.setCreatedBy(customerDto.getCustomerName());
       Customer savedCustomer = customerRepository.save(customer);
       accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(customer.getCreatedAt());
        newAccount.setCreatedBy("anonymous");
        return newAccount;
    }
}

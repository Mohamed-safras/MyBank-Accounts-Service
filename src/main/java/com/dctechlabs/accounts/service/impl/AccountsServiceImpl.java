package com.dctechlabs.accounts.service.impl;

import com.dctechlabs.accounts.entity.Account;
import com.dctechlabs.accounts.utils.constants.AccountsConstants;
import com.dctechlabs.accounts.request.CustomerRequest;
import com.dctechlabs.accounts.entity.Customer;
import com.dctechlabs.accounts.exception.ResourceNotFoundException;
import com.dctechlabs.accounts.exception.CustomerAlreadyExistsException;
import com.dctechlabs.accounts.mapper.AccountsMapper;
import com.dctechlabs.accounts.mapper.CustomerMapper;
import com.dctechlabs.accounts.repository.AccountsRepository;
import com.dctechlabs.accounts.repository.CustomerRepository;
import com.dctechlabs.accounts.request.AccountsRequest;
import com.dctechlabs.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    /**
     * @param mobileNumber - pass mobile number as parameter
     */
    @Override
    public CustomerRequest fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Account account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString()));

        CustomerRequest customerRequest = CustomerMapper.mapToCustomerDto(customer, new CustomerRequest());

        customerRequest.setAccounts(AccountsMapper.mapToAccountsDto(account, new AccountsRequest()));

        return customerRequest;

    }


    /**
     * @param customerRequest - CustomerDto object
     */
    @Override
    public Account createAccount(CustomerRequest customerRequest) {
        Customer customer = CustomerMapper.mapToCustomer(customerRequest, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerRequest.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "
                    + customerRequest.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);

        return accountsRepository.save(createNewAccount(savedCustomer));

    }

    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 100000000000L + new Random().nextLong(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    /**
     * @param customerRequest
     */
    @Override
    public boolean updateAccount(CustomerRequest customerRequest) {
        boolean isUpdated = false;

        AccountsRequest accountsRequest = customerRequest.getAccounts();

        if (accountsRequest != null) {
            Account account = accountsRepository.findById(accountsRequest.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account", "account number", Long.toString(accountsRequest.getAccountNumber())));
            AccountsMapper.mapToAccounts(accountsRequest, account);
            accountsRepository.save(account);

            Long customerId = account.getCustomerId();

            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer", "customer id", Long.toString(customerId)));

            CustomerMapper.mapToCustomer(customerRequest, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber
     * @return boolean
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {


        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString()));

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());


        return true;
    }

}

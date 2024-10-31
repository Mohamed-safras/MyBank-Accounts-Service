package com.dctechlabs.accounts.service;

import com.dctechlabs.accounts.entity.Account;
import com.dctechlabs.accounts.request.CustomerRequest;

public interface IAccountsService {

    /**
     *
     * @param customerRequest - CustomerDto object
     */
    Account createAccount(CustomerRequest customerRequest);

    /**
     *
     * @param mobileNumber - fetchAccount takes mobile number as a parameter
     * @return - it returns CustomerDto object
     */
    CustomerRequest fetchAccount(String mobileNumber);

    /**
     *
     * @param customerRequest - updateAccount takes CustomerDto object as a parameter
     * @return it returns boolean value
     */
    boolean updateAccount(CustomerRequest customerRequest);

    /**
     *
     * @param mobileNumber - deleteAccount takes mobile number as a parameter
     * @return it returns boolean value
     */
    boolean deleteAccount(String mobileNumber);

}

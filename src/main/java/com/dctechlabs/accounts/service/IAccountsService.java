package com.dctechlabs.accounts.service;

import com.dctechlabs.accounts.dto.CustomerDto;
import com.dctechlabs.accounts.entity.Accounts;

public interface IAccountsService {

    /**
     *
     * @param customerDto - CustomerDto object
     */
    Accounts createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - fetchAccount takes mobile number as a parameter
     * @return - it returns CustomerDto object
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     *
     * @param customerDto - updateAccount takes CustomerDto object as a parameter
     * @return it returns boolean value
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - deleteAccount takes mobile number as a parameter
     * @return it returns boolean value
     */
    boolean deleteAccount(String mobileNumber);

}

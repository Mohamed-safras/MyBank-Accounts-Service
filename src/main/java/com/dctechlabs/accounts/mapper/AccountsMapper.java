package com.dctechlabs.accounts.mapper;

import com.dctechlabs.accounts.entity.Account;
import com.dctechlabs.accounts.request.AccountsRequest;

public class AccountsMapper {
    public static AccountsRequest mapToAccountsDto(Account account, AccountsRequest accountsRequest) {
        accountsRequest.setAccountNumber(account.getAccountNumber());
        accountsRequest.setAccountType(account.getAccountType());
        accountsRequest.setBranchAddress(account.getBranchAddress());
        return accountsRequest;
    }

    public static Account mapToAccounts(AccountsRequest accountsRequest, Account account){
        account.setAccountNumber(accountsRequest.getAccountNumber());
        account.setAccountType(accountsRequest.getAccountType());
        account.setBranchAddress(accountsRequest.getBranchAddress());
        return account;
    }
}

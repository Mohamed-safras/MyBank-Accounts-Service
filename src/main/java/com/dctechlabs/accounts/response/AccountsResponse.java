package com.dctechlabs.accounts.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsResponse {
    private String accountType;
    private long accountNumber;
    private String branchAddress;
}

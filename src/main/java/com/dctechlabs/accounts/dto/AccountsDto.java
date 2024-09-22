package com.dctechlabs.accounts.dto;

import jakarta.persistence.Column;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDto {

    private long accountNumber;

    private String accountType;

    private String branchAddress;
}

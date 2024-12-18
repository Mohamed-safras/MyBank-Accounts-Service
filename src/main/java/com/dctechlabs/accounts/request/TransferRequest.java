package com.dctechlabs.accounts.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    private String toAccount;
    private double amount;
    private String scheduledTime;
}

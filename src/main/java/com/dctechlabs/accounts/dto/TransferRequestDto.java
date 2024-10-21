package com.dctechlabs.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDto {
    private String toAccount;
    private double amount;
    private String scheduledTime;
}

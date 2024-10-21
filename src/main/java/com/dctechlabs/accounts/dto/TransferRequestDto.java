package com.dctechlabs.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDto {
    private String toAccount;
    private double amount;
    private String scheduledTime;
}

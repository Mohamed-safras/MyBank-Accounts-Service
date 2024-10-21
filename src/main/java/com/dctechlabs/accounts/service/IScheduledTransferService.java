package com.dctechlabs.accounts.service;


import com.dctechlabs.accounts.dto.TransferRequestDto;
import org.springframework.http.ResponseEntity;

public interface IScheduledTransferService {
    public void scheduleTransfer(TransferRequestDto transferRequestDto);
}

package com.dctechlabs.accounts.service;


import com.dctechlabs.accounts.dto.TransferRequestDto;

public interface IScheduledTransferService {
    void scheduleTransfer(TransferRequestDto transferRequestDto);
}

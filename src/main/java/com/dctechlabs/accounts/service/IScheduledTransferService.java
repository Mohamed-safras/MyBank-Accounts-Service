package com.dctechlabs.accounts.service;


import com.dctechlabs.accounts.request.TransferRequest;

public interface IScheduledTransferService {
    void scheduleTransfer(TransferRequest transferRequest);
}

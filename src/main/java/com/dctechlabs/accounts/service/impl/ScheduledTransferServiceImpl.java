package com.dctechlabs.accounts.service.impl;

import com.dctechlabs.accounts.dto.TransferRequestDto;
import com.dctechlabs.accounts.entity.Transfer;
import com.dctechlabs.accounts.repository.TransferRepository;
import com.dctechlabs.accounts.service.IScheduledTransferService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ScheduledTransferServiceImpl implements IScheduledTransferService {
    private final Logger logger = LoggerFactory.getLogger(ScheduledTransferServiceImpl.class);

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public void scheduleTransfer(TransferRequestDto transferRequestDto) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        LocalDateTime scheduledTime = LocalDateTime.parse(transferRequestDto.getScheduledTime(), formatter);

        logger.info(scheduledTime.format(formatter));
        // Validation for future time
        if (scheduledTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid scheduled time");
        }


        Transfer transfer = new Transfer();
        transfer.setAmount(transferRequestDto.getAmount());
        transfer.setFromAccountNumber("12345678");
        transfer.setToAccountNumber("987654321");
        transfer.setScheduledTime(scheduledTime);


       Transfer updatedTransfer = transferRepository.save(transfer);
       logger.info("Transfer " + updatedTransfer + " scheduled at " + scheduledTime);

    }

    @Scheduled(fixedRate = 30 * 1000)// Checks every second
    private void executeScheduledTransfers() {
        transferRepository.findAll().forEach(transfer -> {
            if(LocalDateTime.now().isAfter(transfer.getScheduledTime())) {
                transferMoney();
                logger.info("Transfer scheduled at {}", transfer.getScheduledTime());
            }

        });

    }

    private void transferMoney(){
        System.out.println("Transferred");
    }
}

package com.dctechlabs.accounts.controller;

import com.dctechlabs.accounts.constants.AccountsConstants;
import com.dctechlabs.accounts.dto.ResponseDto;
import com.dctechlabs.accounts.dto.TransferRequestDto;
import com.dctechlabs.accounts.entity.Transfer;
import com.dctechlabs.accounts.service.IScheduledTransferService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(
        name = "Rest API for transfer money"
)
@RestController
@RequestMapping(path = "/api/transfers",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class TransferController {

    private IScheduledTransferService scheduledTransferService;

    @PostMapping("/schedule")
    public ResponseEntity<ResponseDto> scheduleTransfer(@RequestBody TransferRequestDto transferRequestDto){

        scheduledTransferService.scheduleTransfer(transferRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_201,"Money transferred successfully"));
    }
}

package com.dctechlabs.accounts.controller;

import com.dctechlabs.accounts.utils.constants.AccountsConstants;
import com.dctechlabs.accounts.response.ApiResponse;
import com.dctechlabs.accounts.request.TransferRequest;
import com.dctechlabs.accounts.service.IScheduledTransferService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Rest API for transfer money"
)
@RestController
@RequestMapping(path = "/api/transfers",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class TransferController {

    private IScheduledTransferService scheduledTransferService;

    @PostMapping("/schedule")
    public ResponseEntity<ApiResponse<?>> scheduleTransfer(@RequestBody TransferRequest transferRequest){

        scheduledTransferService.scheduleTransfer(transferRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(AccountsConstants.STATUS_201,"Money transferred successfully",null));
    }
}

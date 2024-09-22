package com.dctechlabs.accounts.controller;

import com.dctechlabs.accounts.constants.AccountsConstants;
import com.dctechlabs.accounts.dto.AccountsDto;
import com.dctechlabs.accounts.dto.CustomerDto;
import com.dctechlabs.accounts.dto.ResponseDto;
import com.dctechlabs.accounts.exception.CustomerAlreadyExistsException;
import com.dctechlabs.accounts.service.IAccountsService;
import com.dctechlabs.accounts.service.impl.AccountsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountsController {

    private AccountsServiceImpl iAccountsService;

    @PostMapping("/create")
   public ResponseEntity<ResponseDto> createAccount(@Validated @RequestBody CustomerDto customerDto){
            iAccountsService.createAccount(customerDto);
            return ResponseEntity.
                    status(HttpStatus.CREATED).
                    body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
   }
}

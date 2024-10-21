package com.dctechlabs.accounts.controller;

import com.dctechlabs.accounts.kafka.sender.KafkaMessageSender;
import com.dctechlabs.accounts.constants.AccountsConstants;
import com.dctechlabs.accounts.dto.CustomerDto;
import com.dctechlabs.accounts.dto.ErrorResponseDto;
import com.dctechlabs.accounts.dto.ResponseDto;
import com.dctechlabs.accounts.entity.Accounts;
import com.dctechlabs.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CURD REST APIs for Accounts in MyBank",
        description = "CURD REST APIs in MyBank to CREATE,UPDATE,FETCH,and DELETE account details"
)
@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

    private final IAccountsService iAccountsService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;
    @Autowired
    private KafkaMessageSender kafkaMessageSender;


    public AccountsController(IAccountsService iAccountsService) {
        this.iAccountsService = iAccountsService;
    }

    @Operation(summary = "Create Account REST API",description = "REST API to create new Customer & Account inside MyBank")
    @ApiResponse(responseCode = "201",description = "HTTP Status CREATED")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        Accounts savedAccount = iAccountsService.createAccount(customerDto);
        kafkaMessageSender.sendMessage("accounts", 0, savedAccount.getAccountNumber() + "",savedAccount);
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }

    @Operation(summary = "FETCH Account REST API",description = "REST API to fetch Customer & Account inside MyBank")
    @ApiResponse(responseCode = "200",description = "HTTP Status OK")
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam(value = "mobileNumber")
                                                               @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number. It should contain 10-15 digits and can start with a '+' for the country code.")
                                                               String mobileNumber){

        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(summary = "UPDATE Account REST API",description = "REST API to UPDATE Customer & Account inside MyBank")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            )
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto){
      boolean isUpdated = iAccountsService.updateAccount(customerDto);
      if(isUpdated){
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
      }else {
          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
      }
    }

    @Operation(summary = "DELETE Account REST API",description = "REST API to DELETE Customer & Account inside MyBank")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam(value = "mobileNumber")
                                                         @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number. It should contain 10-15 digits and can start with a '+' for the country code.")
                                                         String mobileNumber){
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> buildInfo(){
       return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> javaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }
}

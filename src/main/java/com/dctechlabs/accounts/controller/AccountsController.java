package com.dctechlabs.accounts.controller;

import com.dctechlabs.accounts.config.kafka.sender.KafkaMessageSender;
import com.dctechlabs.accounts.utils.constants.AccountsConstants;
import com.dctechlabs.accounts.request.CustomerRequest;
import com.dctechlabs.accounts.response.AccountsResponse;
import com.dctechlabs.accounts.response.ErrorResponse;
import com.dctechlabs.accounts.response.ApiResponse;
import com.dctechlabs.accounts.entity.Account;
import com.dctechlabs.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
        name = "CURD REST APIs for Account in MyBank",
        description = "CURD REST APIs in MyBank to CREATE,UPDATE,FETCH,and DELETE account details"
)
@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

    private final IAccountsService iAccountsService;

//    @Value("${build.version}")
//    private String buildVersion;

    @Value("${accounts.contactInfo.name}")
    private String contactName;

    private final Environment environment;

    private final KafkaMessageSender kafkaMessageSender;

    @Autowired
    public AccountsController(IAccountsService iAccountsService,final KafkaMessageSender kafkaMessageSender,final Environment environment) {
        this.iAccountsService = iAccountsService;
        this.kafkaMessageSender = kafkaMessageSender;
        this.environment = environment;
    }

    @Operation(summary = "Create Account REST API",description = "REST API to create new Customer & Account inside MyBank")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201",description = "HTTP Status CREATED")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AccountsResponse>> createAccount(@Valid @RequestBody CustomerRequest customerRequest){
        Account savedAccount = iAccountsService.createAccount(customerRequest);
        kafkaMessageSender.sendMessage("accounts", 0, savedAccount.getAccountNumber() + "",savedAccount);
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(new ApiResponse<>(
                        AccountsConstants.STATUS_201,
                        AccountsConstants.MESSAGE_201,
                        new AccountsResponse(
                                savedAccount.getAccountType(),
                                savedAccount.getAccountNumber(),
                                savedAccount.getBranchAddress()
                        ))
                );
    }

    @Operation(summary = "FETCH Account REST API",description = "REST API to fetch Customer & Account inside MyBank")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "HTTP Status OK")
    @GetMapping("/fetch")
    public ResponseEntity<CustomerRequest> fetchAccountDetails(@RequestParam(value = "mobileNumber")
                                                               @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number. It should contain 10-15 digits and can start with a '+' for the country code.")
                                                               String mobileNumber){

        CustomerRequest customerRequest = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerRequest);
    }

    @Operation(summary = "UPDATE Account REST API",description = "REST API to UPDATE Customer & Account inside MyBank")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateAccount(@Valid @RequestBody CustomerRequest customerRequest){
      boolean isUpdated = iAccountsService.updateAccount(customerRequest);
      if(isUpdated){
          return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200,null));
      }else {
          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponse<>(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE,null));
      }
    }

    @Operation(summary = "DELETE Account REST API",description = "REST API to DELETE Customer & Account inside MyBank")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> deleteAccount(@RequestParam(value = "mobileNumber")
                                                         @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number. It should contain 10-15 digits and can start with a '+' for the country code.")
                                                         String mobileNumber){
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200,null));
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponse<>(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE,null));
        }
    }

//    @GetMapping("/build-info")
//    public ResponseEntity<String> buildInfo(){
//       return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
//    }

    @GetMapping("/java-version")
    public ResponseEntity<String> javaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<String> contactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(contactName);
    }
}

package com.dctechlabs.accounts.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerRequest {

    @Schema(
            name = "Customer Name",
            description = "Name of the Customer",
            example = "Mohamed Safras"
    )
    @NotEmpty(message = "customer name can not be null or empty")
    @Size(min=5,max=30,message =  "The length of the customer name should be between 5 and 30")
    private String customerName;

    @Schema(
            name = "Customer Email",
            description = "Email of the Customer",
            example = "mohamed.safras@acentura.com"
    )
    @NotEmpty(message = "Email can not be null or empty")
    @Email(message = "Email should be valid")
    private String customerEmail;

    @Schema(
            name = "Customer Mobile Number",
            description = "Mobile Number of the Customer",
            example = "+94757470688 / 0757470688"
    )
    @NotEmpty(message = "customer mobile number can not be null or empty")
    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Invalid phone number. It should contain 10-15 digits and can start with a '+' for the country code."
    )
    private String mobileNumber;

    @Schema(
            name = "Account",
            description = "Account details of the Customer"
    )
    private AccountsRequest accounts;
}

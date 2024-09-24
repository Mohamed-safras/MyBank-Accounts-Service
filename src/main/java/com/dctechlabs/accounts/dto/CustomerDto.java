package com.dctechlabs.accounts.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotEmpty(message = "customer name can not be null or empty")
    @Size(min=5,max=30,message =  "The length of the customer name should be between 5 and 30")
    private String customerName;

    @NotEmpty(message = "Email can not be null or empty")
    @Email(message = "Email should be valid")
    private String customerEmail;

    @NotEmpty(message = "customer mobile number can not be null or empty")
    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Invalid phone number. It should contain 10-15 digits and can start with a '+' for the country code."
    )
    private String mobileNumber;

    AccountsDto accounts;
}

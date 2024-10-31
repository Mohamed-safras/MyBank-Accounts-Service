package com.dctechlabs.accounts.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Account",
        description = "Schema to hold Account information"
)
public class AccountsRequest {
    @Schema(
            name = "Account Number",
            description = "Account Number of the Customer",
            example = "123456789123"
    )
    @NotEmpty(message = "Account number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})",message = "Account number must be 12 digits")
    private long accountNumber;

    @Schema(
            name = "Account Type",
            description = "Type of the Account",
            example = "Saving | Business | Fixed"
    )
    @NotEmpty(message = "Account type can not be null or empty")
    private String accountType;

    @Schema(
            name = "Branch Address",
            description = "Branch Address of the Account",
            example = "Colombo-06,Western Province,Sri Lanka"
    )
    @NotEmpty(message = "Branch address can not be null or empty")
    private String branchAddress;
}

package com.dctechlabs.accounts.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccountType {
    @JsonProperty("Savings")
    SAVINGS,
    @JsonProperty("Current")
    CURRENT,
    @JsonProperty("Business")
    BUSINESS
}

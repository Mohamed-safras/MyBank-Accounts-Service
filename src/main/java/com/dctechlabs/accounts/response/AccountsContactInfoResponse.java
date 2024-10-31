package com.dctechlabs.accounts.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@ConfigurationProperties(prefix = "")
public class AccountsContactInfoResponse {
    private String message;
    private Map<String,String> contactDetails;
    private List<String> onCallSupport;
}

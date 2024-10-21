package com.dctechlabs.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
public class ResponseDto {
    @Schema(
            name = "Status Code",
            description = "Status code in the response"
    )
    private String statusCode;

    @Schema(
            name = "Status Text",
            description = "Status text in the response"
    )
    private String statusText;
}

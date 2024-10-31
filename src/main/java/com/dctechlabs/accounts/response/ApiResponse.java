package com.dctechlabs.accounts.response;

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
public class ApiResponse<T> {
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

    @Schema(
            name = "Data",
            description = "Payload of the response containing specific data"
    )
    private T data;
}

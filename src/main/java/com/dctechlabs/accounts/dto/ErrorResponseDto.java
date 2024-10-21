package com.dctechlabs.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Error Response",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {
    @Schema(
            name = "Api Path invoked by client",
            description = "Api path of the error"
    )
    private String apiPath;

    @Schema(
            name = "Error Code",
            description = "Error Code of the error"
    )
    private HttpStatus errorCode;

    @Schema(
            name = "Error Message",
            description = "Error Message of the error"
    )
    private String errorMessage;

    @Schema(
            name = "Error Time",
            description = "Error Time of the error"
    )
    private LocalDateTime errorTime;
}

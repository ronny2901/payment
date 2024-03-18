package com.newidea.payment.documentation;

import com.newidea.payment.api.dto.ChargeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;
import java.net.URI;

import static com.newidea.payment.documentation.ProcessCharge.VALID_CHARGE_REQUEST;


@Operation(
        summary = "Process Charge",
        description = "Process a charge",
        requestBody = @RequestBody(
                content = @Content(
                        schema = @Schema(
                                implementation = ChargeDto.class
                        ),
                        examples = {
                                @ExampleObject(
                                        name = "Example Request Object",
                                        value = VALID_CHARGE_REQUEST
                                )
                        }
                ),
                required = true,
                description = "Request"
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        content = @Content(schema = @Schema(hidden = true)),
                        headers = {
                                @Header(
                                        name = "Location",
                                        schema = @Schema(
                                                name = "id",
                                                implementation = URI.class
                                        )
                                )
                        }
                )
        }
)
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProcessCharge {

    String VALID_CHARGE_REQUEST = """
                {
                    "sellerId": 1,
                    "payments": [
                     {
                       "paymentId": 1,
                       "paymentStatus": "PENDENTE",
                       "amountPaid": 1
                     },
                     {
                       "paymentId": 2,
                       "paymentStatus": "PENDENTE",
                       "amountPaid": 20
                     },
                     {
                       "paymentId": 3,
                       "paymentStatus": "PENDENTE",
                       "amountPaid": 50
                     }
                   ]
                 }
            """;
}

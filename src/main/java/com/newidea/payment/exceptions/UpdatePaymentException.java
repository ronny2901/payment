package com.newidea.payment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UpdatePaymentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UpdatePaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdatePaymentException(String message) {
        super(message);
    }
}
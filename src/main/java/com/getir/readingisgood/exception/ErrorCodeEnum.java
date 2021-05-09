package com.getir.readingisgood.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    INTERNAL_SERVER_ERROR(1000, "Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR),
    FIELD_VALIDATION_ERROR(1001, "Field validation error.", HttpStatus.BAD_REQUEST),
    CONTENT_NOT_FOUND_ERROR(1002, "Content not found.", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_FOUND(1003, "Customer not found", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(1003, "Product not found", HttpStatus.BAD_REQUEST),
    PRODUCT_ID_MUST_NOT_BLANK(1004, "Product id must not blank", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_AVAIBLE(1005, "Product not avaible", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1006, "User not found", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1006, "Order not found", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus httpStatus;
}

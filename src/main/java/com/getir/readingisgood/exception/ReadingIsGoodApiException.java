package com.getir.readingisgood.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReadingIsGoodApiException extends RuntimeException {

    private final ErrorCodeEnum errorCode;

    public ReadingIsGoodApiException(ErrorCodeEnum errorCode) {
        super();
        this.errorCode = errorCode;
    }

}

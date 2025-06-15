package com.sku.loom.global.exception.base;

import com.sku.loom.global.exception.constant.ErrorDetail;

public class CustomException extends ApplicationException {

    public CustomException(ErrorDetail errorDetail) {
        super(errorDetail);
    }
}
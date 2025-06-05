package com.sku.loom.global.exception.token;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class InvalidTokenException extends ApplicationException {
    public InvalidTokenException() {
        super(ErrorDetail.INVALID_TOKEN);
    }
}

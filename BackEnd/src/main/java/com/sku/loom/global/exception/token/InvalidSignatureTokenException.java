package com.sku.loom.global.exception.token;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class InvalidSignatureTokenException extends ApplicationException {
    public InvalidSignatureTokenException() {
        super(ErrorDetail.INVALID_SIGNATURE);
    }
}
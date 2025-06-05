package com.sku.loom.global.exception.token;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class EmptyTokenException extends ApplicationException {
    public EmptyTokenException() {
        super(ErrorDetail.EMPTY_TOKEN);
    }
}
package com.sku.loom.global.exception.token;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class UnsupportedTokenException extends ApplicationException {
    public UnsupportedTokenException() {
        super(ErrorDetail.UNSUPPORTED_TOKEN);
    }
}

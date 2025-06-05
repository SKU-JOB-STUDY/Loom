package com.sku.loom.global.exception.token;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class ExpiredTokenException extends ApplicationException {
    public ExpiredTokenException() {
        super(ErrorDetail.EXPIRED_TOKEN);
    }
}

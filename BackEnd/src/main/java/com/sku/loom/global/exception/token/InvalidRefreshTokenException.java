package com.sku.loom.global.exception.token;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class InvalidRefreshTokenException extends ApplicationException {
    public InvalidRefreshTokenException() {
        super(ErrorDetail.INVALID_REFRESH_TOKEN);
    }
}

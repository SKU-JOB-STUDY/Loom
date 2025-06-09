package com.sku.loom.global.exception.user;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class NotFoundUserException extends ApplicationException {
    public NotFoundUserException() {
        super(ErrorDetail.NOT_FOUND_USER);
    }
}
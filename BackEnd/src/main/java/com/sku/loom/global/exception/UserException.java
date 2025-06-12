// com.sku.loom.global.exception.UserException
package com.sku.loom.global.exception;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class UserException extends ApplicationException {

    public UserException(ErrorDetail errorDetail) {
        super(errorDetail);
    }
}

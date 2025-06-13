// com.sku.loom.global.exception.user.UserException
package com.sku.loom.global.exception.user;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class UserException extends ApplicationException {

    public UserException(ErrorDetail errorDetail) {
        super(errorDetail);
    }
}

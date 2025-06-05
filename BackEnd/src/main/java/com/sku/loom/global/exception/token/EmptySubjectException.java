package com.sku.loom.global.exception.token;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class EmptySubjectException extends ApplicationException {
    public EmptySubjectException() {
        super(ErrorDetail.EMPTY_SUBJECT);
    }
}
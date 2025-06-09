package com.sku.loom.global.exception.workspace;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class NotFoundWorkspaceException extends ApplicationException {
    public NotFoundWorkspaceException() {
        super(ErrorDetail.NOT_FOUND_WORKSPACE);
    }
}
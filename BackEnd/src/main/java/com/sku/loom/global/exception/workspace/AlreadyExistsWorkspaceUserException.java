package com.sku.loom.global.exception.workspace;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class AlreadyExistsWorkspaceUserException extends ApplicationException {
    public AlreadyExistsWorkspaceUserException() {
        super(ErrorDetail.ALREADY_EXISTS_WORKSPACE_USER);
    }
}
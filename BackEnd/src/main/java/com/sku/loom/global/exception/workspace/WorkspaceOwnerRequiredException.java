package com.sku.loom.global.exception.workspace;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class WorkspaceOwnerRequiredException extends ApplicationException {
    public WorkspaceOwnerRequiredException() {
        super(ErrorDetail.WORKSPACE_OWNER_REQUIRED);
    }
}

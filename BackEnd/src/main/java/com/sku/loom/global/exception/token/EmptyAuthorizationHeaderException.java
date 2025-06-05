package com.sku.loom.global.exception.token;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class EmptyAuthorizationHeaderException extends ApplicationException {
  public EmptyAuthorizationHeaderException() {
    super(ErrorDetail.EMPTY_AUTHORIZATION_HEADER);
  }
}

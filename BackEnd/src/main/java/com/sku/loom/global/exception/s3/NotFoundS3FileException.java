package com.sku.loom.global.exception.s3;

import com.sku.loom.global.exception.base.ApplicationException;
import com.sku.loom.global.exception.constant.ErrorDetail;

public class NotFoundS3FileException extends ApplicationException {
  public NotFoundS3FileException() {
    super(ErrorDetail.NOT_FOUND_S3_FILE);
  }
}

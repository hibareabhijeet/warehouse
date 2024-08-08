package com.tech.warehouse.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.playground.warehouse.utils.ResponseConstants;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseEntity<T> {
  protected Integer responseCode;

  protected String responseMessage;

  private T responseData;

  public void setInvalidRequestParamResponse() {
    this.responseCode = ResponseConstants.ResponseCode.INVALID_REQUEST_PARAMETERS.value();
    this.responseMessage = ResponseConstants.ResponseMessage.INVALID_REQUEST_PARAMETERS.value();
  }

  public void setRecordNotFoundResponse() {
    this.responseCode = ResponseConstants.ResponseCode.RECORD_NOT_FOUND.value();
    this.responseMessage = ResponseConstants.ResponseMessage.RECORD_NOT_FOUND.value();
  }
}

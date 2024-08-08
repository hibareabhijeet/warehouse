package com.playground.warehouse.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WarehouseResponse<T> {
  protected Integer responseCode;

  protected String responseMessage;

  private List<T> responseData;

  public void setInvalidRequestParamResponse() {
    this.responseCode = ResponseConstants.ResponseCode.INVALID_REQUEST_PARAMETERS.value();
    this.responseMessage = ResponseConstants.ResponseMessage.INVALID_REQUEST_PARAMETERS.value();
  }

  public void setImportFileResponse() {
    this.responseCode = ResponseConstants.ResponseCode.RECORD_IMPORTED.value();
    this.responseMessage = ResponseConstants.ResponseMessage.RECORD_IMPORTED.value();
  }

  public void setRecordFoundResponse(List<T> responsObj) {
    this.responseCode = ResponseConstants.ResponseCode.RECORD_FOUND.value();
    this.responseMessage = ResponseConstants.ResponseMessage.RECORD_FOUND.value();
    this.responseData = responsObj;
  }

  public void setRecordNotFoundResponse() {
    this.responseCode = ResponseConstants.ResponseCode.RECORD_NOT_FOUND.value();
    this.responseMessage = ResponseConstants.ResponseMessage.RECORD_NOT_FOUND.value();
  }

  public void setExceptionResponse(String message) {
    this.responseCode = ResponseConstants.ResponseCode.EXCEPTION.value();
    this.responseMessage = message;
  }
}

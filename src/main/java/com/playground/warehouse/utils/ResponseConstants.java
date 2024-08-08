package com.playground.warehouse.utils;

public class ResponseConstants {
  public enum ResponseCode {
    RECORD_FOUND(200),
    RECORD_NOT_FOUND(204),

    INVALID_REQUEST_PARAMETERS(400),
    EXCEPTION(401),
    RECORD_IMPORTED(200);

    private final Integer code;

    private ResponseCode(Integer code) {
      this.code = code;
    }

    public Integer value() {
      return code;
    }
  }

  public enum ResponseMessage {
    RECORD_FOUND("Record found successfully"),
    RECORD_NOT_FOUND("Record not found"),
    INVALID_REQUEST_PARAMETERS("Invalid request parameters"),
    RECORD_IMPORTED("Record successfully imported");

    private final String message;

    private ResponseMessage(String message) {
      this.message = message;
    }

    public String value() {
      return message;
    }
  }
}

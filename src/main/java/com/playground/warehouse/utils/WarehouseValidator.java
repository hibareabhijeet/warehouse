package com.playground.warehouse.utils;

import org.springframework.web.multipart.MultipartFile;

public class WarehouseValidator {
  private WarehouseValidator() {
    throw new IllegalStateException("InventoryValidation class");
  }

  public static boolean isImportFileInvalid(MultipartFile file) {
    return (file.isEmpty() || (!file.getContentType().equalsIgnoreCase("application/json")));
  }
}

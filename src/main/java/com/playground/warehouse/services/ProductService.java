package com.tech.warehouse.services;

import com.playground.warehouse.utils.WarehouseResponse;
import com.tech.warehouse.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

  ResponseEntity<WarehouseResponse<Product>> getAvailableProductWithQuantity();

  ResponseEntity<WarehouseResponse<Product>> sellProductByName(String productName);

  ResponseEntity<WarehouseResponse<Product>> importProductFile(MultipartFile file);

  ResponseEntity<WarehouseResponse<Product>> getAllProducts();
}

package com.tech.warehouse.controller;

import com.playground.warehouse.utils.WarehouseResponse;
import com.tech.warehouse.entities.Article;
import com.tech.warehouse.entities.Product;
import com.tech.warehouse.services.InventoryService;
import com.tech.warehouse.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class WarehouseController {

  @Autowired private ProductService productService;
  @Autowired private InventoryService inventoryService;

  /**
   * Get all articles
   *
   * @return ResponseEntity of articles
   */
  @GetMapping("/articles")
  public ResponseEntity<WarehouseResponse<Article>> getAllArticles() {
    log.info("In getAllArticles controller");
    return inventoryService.getAllArticles();
  }

  /**
   * import articles from inventory json file
   *
   * @param file
   * @return
   */
  @PostMapping("/import/inventoryfile")
  public ResponseEntity<WarehouseResponse<Article>> importInventoryFile(
      @RequestParam("file") MultipartFile file) {
    log.info("In importInventoryFile controller");
    return inventoryService.importInventoryFile(file);
  }

  /**
   * Get all products
   *
   * @return ResponseEntity of Products
   */
  @GetMapping("/products")
  public ResponseEntity<WarehouseResponse<Product>> getAllProducts() {
    log.info("In getAllProducts controller");
    return productService.getAllProducts();
  }

  /**
   * Import products from product json file
   *
   * @param file
   * @return ResponseEntity
   */
  @PostMapping("/import/productfile")
  public ResponseEntity<WarehouseResponse<Product>> importProductFile(
      @RequestParam("file") MultipartFile file) {
    log.info("In importProductFile controller");
    return productService.importProductFile(file);
  }

  /**
   * Get all products and quantity of each that is an available with the current inventory
   *
   * @return ResponseEntity of available products
   */
  @GetMapping("/available/products")
  public ResponseEntity<WarehouseResponse<Product>> getAvailableProductsWithQuantity() {
    log.info("In getAvailableProductsWithQuantity controller");
    return productService.getAvailableProductWithQuantity();
  }

  /**
   * Sell a product and update the inventory accordingly
   *
   * @param productName
   * @return
   */
  @DeleteMapping("/sell/product")
  public ResponseEntity<WarehouseResponse<Product>> sellProductByName(
      @RequestParam String productName) {
    log.info("In sellProductByName controller");
    return productService.sellProductByName(productName);
  }
}

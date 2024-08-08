package com.tech.warehouse.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.warehouse.utils.WarehouseResponse;
import com.playground.warehouse.utils.WarehouseValidator;
import com.tech.warehouse.dto.ProductDTO;
import com.tech.warehouse.entities.Product;
import com.tech.warehouse.entities.ProductArticles;
import com.tech.warehouse.repository.ProductRepository;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ProductServiceImpl implements com.tech.warehouse.services.ProductService {

  @Autowired private com.tech.warehouse.services.InventoryService inventoryService;
  @Autowired private ProductRepository productRepository;

  /**
   * Retrieve all products and quantity of each that is an available with the current inventory
   *
   * @return ResponseEntity of available Products
   */
  @Override
  public ResponseEntity<WarehouseResponse<Product>> getAvailableProductWithQuantity() {
    WarehouseResponse<Product> availableProductWithQuantityResponse = new WarehouseResponse<>();
    List<Product> productList = productRepository.findAll();

    if (productList.isEmpty()) {
      log.info("Product record not found");
      availableProductWithQuantityResponse.setRecordNotFoundResponse();
      return new ResponseEntity<>(availableProductWithQuantityResponse, HttpStatus.OK);
    }
    productList.stream().forEach(product -> isProductAvailable(product));
    List<Product> availableProductList =
        productList.stream()
            .filter(product -> product.getIsAvailable() != null)
            .filter(product -> product.getIsAvailable())
            .collect(Collectors.toList());
    if (availableProductList == null || availableProductList.isEmpty()) {
      availableProductWithQuantityResponse.setResponseCode(200);
      availableProductWithQuantityResponse.setResponseMessage(
          "ProductArticles of products is not available in inventory, Please visit again");
      return new ResponseEntity<>(availableProductWithQuantityResponse, HttpStatus.OK);
    }
    availableProductList.stream().forEach(product -> calculateProductQuantityInStock(product));
    availableProductWithQuantityResponse.setRecordFoundResponse(availableProductList);
    return new ResponseEntity<>(availableProductWithQuantityResponse, HttpStatus.OK);
  }

  /**
   * Sell a product and update the inventory accordingly
   *
   * @param productName
   * @return ResponseEntity of Sold product details
   */
  @Override
  public ResponseEntity<WarehouseResponse<Product>> sellProductByName(String productName) {
    WarehouseResponse<Product> sellProductByProductNameResponse = new WarehouseResponse<>();
    if (productName == null) {
      sellProductByProductNameResponse.setInvalidRequestParamResponse();
      return new ResponseEntity<>(sellProductByProductNameResponse, HttpStatus.BAD_REQUEST);
    }
    Optional<Product> product = productRepository.findByName(productName).stream().findFirst();

    if (product.isPresent()) {

      if (isProductAvailable(product.get())) {
        updateInventory(product.get());
        productRepository.delete(product.get());
        product.get().setIsAvailable(null);
        sellProductByProductNameResponse.setResponseMessage("Product has been sold");

      } else {
        sellProductByProductNameResponse.setResponseMessage(
            "ProductArticles of this product is not available in inventory, Please visit again");
      }
      sellProductByProductNameResponse.setResponseCode(200);
      sellProductByProductNameResponse.setResponseData(Collections.singletonList(product.get()));
      return new ResponseEntity<>(sellProductByProductNameResponse, HttpStatus.OK);
    }
    sellProductByProductNameResponse.setRecordNotFoundResponse();
    return new ResponseEntity<>(sellProductByProductNameResponse, HttpStatus.OK);
  }

  /**
   * Update inventory with purchased ProductArticles
   *
   * @param product
   */
  private void updateInventory(Product product) {
    product.getContainArticles().stream()
        .forEach(
            productArticle ->
                inventoryService.updateArticleStock(
                    productArticle.getArticleId(), productArticle.getAmountOf()));
  }

  /**
   * Verify productArticles availability with the current inventory
   *
   * @param productArticles
   * @return boolean
   */
  private boolean isProductArticleAvailable(ProductArticles productArticles) {
    return inventoryService.getArticleStock(productArticles.getArticleId())
        >= productArticles.getAmountOf();
  }

  /**
   * Verify and set product availability
   *
   * @param product
   * @return Boolean
   */
  public boolean isProductAvailable(Product product) {
    for (ProductArticles productArticles : product.getContainArticles()) {

      if (!isProductArticleAvailable(productArticles)) {
        return false;
      }
    }
    product.setIsAvailable(true);
    return true;
  }

  /**
   * Calculate and set product quantity with the current inventory
   *
   * @param product
   */
  void calculateProductQuantityInStock(Product product) {
    setProductArticlesStock(product);
    int productQuantity = 0;
    boolean isArticleStockAvailable = true;
    // Repeat loop until ProductArticles quantity available with the current inventory
    do {
      int allArticleCheck = 0;
      int countOfArticle = product.getContainArticles().size();
      for (ProductArticles productArticles : product.getContainArticles()) {
        allArticleCheck++;

        int remainingStock = productArticles.getStock() - productArticles.getAmountOf();
        productArticles.setStock(remainingStock);
        if (remainingStock < 0) {
          isArticleStockAvailable = false;
          break;
        } else if (allArticleCheck == countOfArticle) productQuantity++;
      }
    } while (isArticleStockAvailable);
    product.setQuantityInStock(productQuantity);
    setProductArticlesStock(product);
  }

  /**
   * Set stock of ProductArticles that is available with the current inventory
   *
   * @param product
   */
  void setProductArticlesStock(Product product) {
    product.getContainArticles().stream()
        .forEach(
            productArticles ->
                productArticles.setStock(
                    inventoryService.getArticleStock(productArticles.getArticleId())));
  }

  /**
   * Import article from inventory
   *
   * @param file
   * @return ResponseEntity
   */
  @Override
  public ResponseEntity<WarehouseResponse<Product>> importProductFile(MultipartFile file) {
    WarehouseResponse<Product> importProductFileResponse = new WarehouseResponse<>();
    try {
      if (WarehouseValidator.isImportFileInvalid(file)) {
        log.info("Invalid request");
        importProductFileResponse.setInvalidRequestParamResponse();
        return new ResponseEntity<>(importProductFileResponse, HttpStatus.BAD_REQUEST);
      } else {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO product = objectMapper.readValue(file.getBytes(), ProductDTO.class);

        if (product == null || product.getProducts() == null) {

          importProductFileResponse.setInvalidRequestParamResponse();
          return new ResponseEntity<>(importProductFileResponse, HttpStatus.BAD_REQUEST);
        } else {

          importProductFileResponse.setImportFileResponse();
          importProductFileResponse.setResponseData(
              productRepository.saveAll(product.getProducts()));
        }
      }
    } catch (IOException ioException) {
      log.error("Exception in import inventory file {}", ioException.getMessage());
      importProductFileResponse.setExceptionResponse("Exception occurred while reading file");
      return new ResponseEntity<>(importProductFileResponse, HttpStatus.EXPECTATION_FAILED);
    }
    return new ResponseEntity<>(importProductFileResponse, HttpStatus.OK);
  }

  /**
   * Get all products
   *
   * @return ResponseEntity with products details
   */
  @Override
  public ResponseEntity<WarehouseResponse<Product>> getAllProducts() {
    WarehouseResponse<Product> getAllProductsResponse = new WarehouseResponse<>();
    List<Product> productList = productRepository.findAll();

    if (productList.isEmpty()) {
      log.info("Record not found");
      getAllProductsResponse.setRecordNotFoundResponse();
      return new ResponseEntity<>(getAllProductsResponse, HttpStatus.OK);
    }
    getAllProductsResponse.setRecordFoundResponse(productList);
    return new ResponseEntity<>(getAllProductsResponse, HttpStatus.OK);
  }
}

package com.tech.warehouse.services;

import com.playground.warehouse.utils.WarehouseResponse;
import com.tech.warehouse.entities.Product;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY)
class ProductServiceImplTest {
  @Autowired private ProductService productService;

  @Test
  void importProductFileWithSuccess() throws IOException {
    File file = new File(getClass().getClassLoader().getResource("data/products.json").getFile());
    MockMultipartFile multipartFile =
        new MockMultipartFile(
            "products.json", "products.json", "application/json", new FileInputStream(file));
    ResponseEntity<WarehouseResponse<Product>> allProducts =
        productService.importProductFile(multipartFile);
    Assert.assertTrue(allProducts.getBody().getResponseData().size() >= 1);
    Assert.assertNotNull(allProducts.getBody().getResponseData());
  }

  @Test
  void getAllProductsWithSuccess() {
    ResponseEntity<WarehouseResponse<Product>> allProducts = productService.getAllProducts();
    Assert.assertTrue(allProducts.getBody().getResponseData().size() >= 1);
    Assert.assertNotNull(allProducts.getBody().getResponseData());
  }
}

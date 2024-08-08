package com.tech.warehouse.services;

import com.playground.warehouse.utils.WarehouseResponse;
import com.tech.warehouse.entities.Article;
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
class InventoryServiceImplTest {
  @Autowired private InventoryService inventoryService;

  @Test
  void getAllArticlesWithSuccess() {
    ResponseEntity<WarehouseResponse<Article>> allArticles = inventoryService.getAllArticles();
    Assert.assertTrue(allArticles.getBody().getResponseData().size() >= 1);
    Assert.assertNotNull(allArticles.getBody().getResponseData());
  }

  @Test
  void importInventoryFileWithSuccess() throws IOException {
    File file = new File(getClass().getClassLoader().getResource("data/inventory.json").getFile());
    MockMultipartFile multipartFile =
        new MockMultipartFile(
            "inventory.json", "inventory.json", "application/json", new FileInputStream(file));
    ResponseEntity<WarehouseResponse<Article>> allArticles =
        inventoryService.importInventoryFile(multipartFile);
    Assert.assertTrue(allArticles.getBody().getResponseData().size() >= 1);
    Assert.assertNotNull(allArticles.getBody().getResponseData());
  }
}

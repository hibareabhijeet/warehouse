package com.tech.warehouse.services;

import com.playground.warehouse.utils.WarehouseResponse;
import com.tech.warehouse.entities.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface InventoryService {
  ResponseEntity<WarehouseResponse<Article>> getAllArticles();

  ResponseEntity<WarehouseResponse<Article>> importInventoryFile(MultipartFile file);

  Article getArticleById(String articleId);

  Article createUpdateArticle(Article article);

  Integer getArticleStock(String articleId);

  void updateArticleStock(String articleId, Integer amountOfPurchase);
}

package com.tech.warehouse.dto;

import com.tech.warehouse.entities.Article;
import java.util.List;
import lombok.Data;

@Data
public class InventoryDTO {
  List<Article> inventory;
}

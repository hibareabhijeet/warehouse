package com.tech.warehouse.dto;

import com.tech.warehouse.entities.Product;
import java.util.List;
import lombok.Data;

@Data
public class ProductDTO {
  List<Product> products;
}

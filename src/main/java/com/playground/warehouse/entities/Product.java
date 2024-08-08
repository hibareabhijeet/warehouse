package com.tech.warehouse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
  @SequenceGenerator(name = "id_generator", sequenceName = "product_id_seq", allocationSize = 1)
  @Column(name = "id")
  @JsonIgnore
  Integer productId;

  String name;

  @JsonProperty("contain_articles")
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "prod_id")
  List<ProductArticles> containArticles;

  @Transient private Boolean isAvailable;
  @Transient private Integer quantityInStock;
}

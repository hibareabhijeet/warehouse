package com.tech.warehouse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "product_article_mapping")
public class ProductArticles {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pam_id_generator")
  @SequenceGenerator(
      name = "pam_id_generator",
      sequenceName = "product_article_mapping_id_seq",
      allocationSize = 1)
  @JsonIgnore
  Integer id;

  @Column(name = "art_id")
  @JsonProperty("art_id")
  String articleId;

  @Column(name = "amount_of")
  @JsonProperty("amount_of")
  Integer amountOf;

  @Column(name = "prod_id")
  @JsonIgnore
  Integer productId;

  @Transient Integer stock;
}

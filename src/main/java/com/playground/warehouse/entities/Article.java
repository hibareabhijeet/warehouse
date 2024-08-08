package com.tech.warehouse.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "article")
public class Article {
  @Id
  @Column(name = "art_id")
  @JsonProperty("art_id")
  private String artId;

  private String name;
  private Integer stock;
}

package com.sve.workshops.hexagonal.exercice2.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock")
public class Stock {
    @Id
    public String articleId;

    public Integer quantity;
}

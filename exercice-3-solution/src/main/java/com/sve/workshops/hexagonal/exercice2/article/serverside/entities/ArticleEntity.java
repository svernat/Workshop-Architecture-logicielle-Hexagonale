package com.sve.workshops.hexagonal.exercice2.article.serverside.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    public String id;

    public String name;
    public String description;
    public Double price;
}

package com.sve.workshops.hexagonal.exercice2.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.io.Serializable;

class ArticleInOrderId implements Serializable {
    public String orderId;
    public String articleId;

    public ArticleInOrderId() {
    }

    public ArticleInOrderId(String orderId, String articleId) {
        this.orderId = orderId;
        this.articleId = articleId;
    }
}

@Entity
@Table(name = "article_in_order")
@IdClass(ArticleInOrderId.class)
public class ArticleInOrder {
    @Id
    public String orderId;

    @Id
    public String articleId;

    public Integer quantity;
}

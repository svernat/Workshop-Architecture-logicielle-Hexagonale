package com.sve.workshops.hexagonal.exercice2.article.serverside.dao;

import com.sve.workshops.hexagonal.exercice2.article.serverside.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleDAO extends JpaRepository<ArticleEntity, String> {
}

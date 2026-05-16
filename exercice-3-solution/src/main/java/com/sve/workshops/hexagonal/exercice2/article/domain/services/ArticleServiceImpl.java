package com.sve.workshops.hexagonal.exercice2.article.domain.services;

import com.sve.workshops.hexagonal.exercice2.article.domain.model.Article;
import com.sve.workshops.hexagonal.exercice2.article.domain.api.business.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business service dedicated to article management.
 *
 * <p>This interface defines the main operations used to create,
 * retrieve, update, and delete articles.</p>
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    /**
     * Creates a new article.
     *
     * @param article the article to create
     * @return the created article
     */
    @Override
    public Article create(Article article) {
        return null;
    }

    /**
     * Retrieves all existing articles.
     *
     * @return the list of articles
     */
    @Override
    public List<Article> getAll() {
        return List.of();
    }

    /**
     * Retrieves an article by its identifier.
     *
     * @param id the identifier of the article to retrieve
     * @return the article matching the provided identifier
     */
    @Override
    public Article getById(String id) {
        return null;
    }

    /**
     * Updates an existing article.
     *
     * @param article the article containing the updated information
     * @return the updated article
     */
    @Override
    public Article update(Article article) {
        return null;
    }

    /**
     * Deletes an article by its identifier.
     *
     * @param id the identifier of the article to delete
     */
    @Override
    public void delete(String id) {

    }
}

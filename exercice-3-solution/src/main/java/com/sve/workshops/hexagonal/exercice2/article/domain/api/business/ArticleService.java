package com.sve.workshops.hexagonal.exercice2.article.domain.api.business;

import com.sve.workshops.hexagonal.exercice2.article.domain.model.Article;

import java.util.List;

/**
 * Business service dedicated to article management.
 *
 * <p>This interface defines the main operations used to create,
 * retrieve, update, and delete articles.</p>
 */
public interface ArticleService {

    /**
     * Creates a new article.
     *
     * @param article the article to create
     * @return the created article
     */
    Article create(Article article);

    /**
     * Retrieves all existing articles.
     *
     * @return the list of articles
     */
    List<Article> getAll();

    /**
     * Retrieves an article by its identifier.
     *
     * @param id the identifier of the article to retrieve
     * @return the article matching the provided identifier
     */
    Article getById(String id);

    /**
     * Updates an existing article.
     *
     * @param article the article containing the updated information
     * @return the updated article
     */
    Article update(Article article);

    /**
     * Deletes an article by its identifier.
     *
     * @param id the identifier of the article to delete
     */
    void delete(String id);
}

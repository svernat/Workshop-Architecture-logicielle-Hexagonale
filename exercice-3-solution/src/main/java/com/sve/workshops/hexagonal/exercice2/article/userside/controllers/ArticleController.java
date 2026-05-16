package com.sve.workshops.hexagonal.exercice2.article.userside.controllers;

import com.sve.workshops.hexagonal.exercice2.article.domain.api.business.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public Article create(@RequestBody Article article) {
        article.id = UUID.randomUUID().toString();
        return articleRepository.save(article);
    }

    @GetMapping
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Article getById(@PathVariable String id) {
        return articleRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Article update(@PathVariable String id, @RequestBody Article article) {
        Article existingArticle = articleRepository.findById(id).orElse(null);

        if (existingArticle == null) {
            return null;
        }

        existingArticle.name = article.name;
        existingArticle.description = article.description;
        existingArticle.price = article.price;

        return articleRepository.save(existingArticle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        articleRepository.deleteById(id);
    }
}

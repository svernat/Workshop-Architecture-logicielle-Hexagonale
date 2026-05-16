package com.sve.workshops.hexagonal.exercice2.article;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
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

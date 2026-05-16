package com.sve.workshops.hexagonal.exercice2.order;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles-in-order")
public class ArticleInOrderController {
    private final ArticleInOrderRepository articleInOrderRepository;

    public ArticleInOrderController(ArticleInOrderRepository articleInOrderRepository) {
        this.articleInOrderRepository = articleInOrderRepository;
    }

    @PostMapping
    public ArticleInOrder create(@RequestBody ArticleInOrder articleInOrder) {
        return articleInOrderRepository.save(articleInOrder);
    }

    @GetMapping
    public List<ArticleInOrder> getAll() {
        return articleInOrderRepository.findAll();
    }

    @GetMapping("/{orderId}/{articleId}")
    public ArticleInOrder getById(
            @PathVariable String orderId,
            @PathVariable String articleId
    ) {
        ArticleInOrderId id = new ArticleInOrderId(orderId, articleId);
        return articleInOrderRepository.findById(id).orElse(null);
    }

    @PutMapping("/{orderId}/{articleId}")
    public ArticleInOrder update(
            @PathVariable String orderId,
            @PathVariable String articleId,
            @RequestBody ArticleInOrder articleInOrder
    ) {
        ArticleInOrderId id = new ArticleInOrderId(orderId, articleId);
        ArticleInOrder existingArticleInOrder = articleInOrderRepository.findById(id).orElse(null);

        if (existingArticleInOrder == null) {
            return null;
        }

        existingArticleInOrder.quantity = articleInOrder.quantity;

        return articleInOrderRepository.save(existingArticleInOrder);
    }

    @DeleteMapping("/{orderId}/{articleId}")
    public void delete(
            @PathVariable String orderId,
            @PathVariable String articleId
    ) {
        ArticleInOrderId id = new ArticleInOrderId(orderId, articleId);
        articleInOrderRepository.deleteById(id);
    }
}

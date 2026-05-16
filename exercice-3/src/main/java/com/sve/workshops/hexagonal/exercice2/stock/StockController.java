package com.sve.workshops.hexagonal.exercice2.stock;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final StockRepository stockRepository;

    public StockController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @PostMapping
    public Stock create(@RequestBody Stock stock) {
        return stockRepository.save(stock);
    }

    @GetMapping
    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    @GetMapping("/{articleId}")
    public Stock getByArticleId(@PathVariable String articleId) {
        return stockRepository.findById(articleId).orElse(null);
    }

    @PutMapping("/{articleId}")
    public Stock update(@PathVariable String articleId, @RequestBody Stock stock) {
        Stock existingStock = stockRepository.findById(articleId).orElse(null);

        if (existingStock == null) {
            return null;
        }

        existingStock.quantity = stock.quantity;

        return stockRepository.save(existingStock);
    }

    @DeleteMapping("/{articleId}")
    public void delete(@PathVariable String articleId) {
        stockRepository.deleteById(articleId);
    }
}

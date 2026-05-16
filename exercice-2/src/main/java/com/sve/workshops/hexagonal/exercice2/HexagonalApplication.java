package com.sve.workshops.hexagonal.exercice2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableJpaRepositories(
		considerNestedRepositories = true
)
public class HexagonalApplication {

	static void main(String[] args) {
		SpringApplication.run(HexagonalApplication.class, args);
	}

	// ============================================================
	// ARTICLE
	// ============================================================

	@Entity
	@Table(name = "article")
	public static class Article {
		@Id
		public String id;

		public String name;
		public String description;
		public Double price;
	}

	public interface ArticleRepository extends JpaRepository<Article, String> {
	}

	@RestController
	@RequestMapping("/articles")
	public static class ArticleController {

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

	// ============================================================
	// STOCK
	// ============================================================

	@Entity
	@Table(name = "stock")
	public static class Stock {
		@Id
		public String articleId;

		public Integer quantity;
	}

	public interface StockRepository extends JpaRepository<Stock, String> {
	}

	@RestController
	@RequestMapping("/stocks")
	public static class StockController {

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

	// ============================================================
	// ORDER
	// ============================================================

	@Entity
	@Table(name = "commandes")
	public static class Order {
		@Id
		public String id;

		public String username;
		public String status;
	}

	public interface OrderRepository extends JpaRepository<Order, String> {
	}

	@RestController
	@RequestMapping("/orders")
	public static class OrderController {

		private final OrderRepository orderRepository;

		public OrderController(OrderRepository orderRepository) {
			this.orderRepository = orderRepository;
		}

		@PostMapping
		public Order create(@RequestBody Order order) {
			order.id = UUID.randomUUID().toString();
			return orderRepository.save(order);
		}

		@GetMapping
		public List<Order> getAll() {
			return orderRepository.findAll();
		}

		@GetMapping("/{id}")
		public Order getById(@PathVariable String id) {
			return orderRepository.findById(id).orElse(null);
		}

		@PutMapping("/{id}")
		public Order update(@PathVariable String id, @RequestBody Order order) {
			Order existingOrder = orderRepository.findById(id).orElse(null);

			if (existingOrder == null) {
				return null;
			}

			existingOrder.username = order.username;
			existingOrder.status = order.status;

			return orderRepository.save(existingOrder);
		}

		@DeleteMapping("/{id}")
		public void delete(@PathVariable String id) {
			orderRepository.deleteById(id);
		}
	}

	// ============================================================
	// ARTICLES_IN_ORDER
	// ============================================================

	public static class ArticlesInOrderId implements Serializable {
		public String orderId;
		public String articleId;

		public ArticlesInOrderId() {
		}

		public ArticlesInOrderId(String orderId, String articleId) {
			this.orderId = orderId;
			this.articleId = articleId;
		}
	}

	@Entity
	@Table(name = "articles_in_order")
	@IdClass(ArticlesInOrderId.class)
	public static class ArticlesInOrder {
		@Id
		public String orderId;

		@Id
		public String articleId;

		public Integer quantity;
	}

	public interface ArticlesInOrderRepository extends JpaRepository<ArticlesInOrder, ArticlesInOrderId> {
	}

	@RestController
	@RequestMapping("/articles-in-order")
	public static class ArticlesInOrderController {

		private final ArticlesInOrderRepository articlesInOrderRepository;

		public ArticlesInOrderController(ArticlesInOrderRepository articlesInOrderRepository) {
			this.articlesInOrderRepository = articlesInOrderRepository;
		}

		@PostMapping
		public ArticlesInOrder create(@RequestBody ArticlesInOrder articlesInOrder) {
			return articlesInOrderRepository.save(articlesInOrder);
		}

		@GetMapping
		public List<ArticlesInOrder> getAll() {
			return articlesInOrderRepository.findAll();
		}

		@GetMapping("/{orderId}/{articleId}")
		public ArticlesInOrder getById(
				@PathVariable String orderId,
				@PathVariable String articleId
		) {
			ArticlesInOrderId id = new ArticlesInOrderId(orderId, articleId);
			return articlesInOrderRepository.findById(id).orElse(null);
		}

		@PutMapping("/{orderId}/{articleId}")
		public ArticlesInOrder update(
				@PathVariable String orderId,
				@PathVariable String articleId,
				@RequestBody ArticlesInOrder articlesInOrder
		) {
			ArticlesInOrderId id = new ArticlesInOrderId(orderId, articleId);
			ArticlesInOrder existingArticlesInOrder = articlesInOrderRepository.findById(id).orElse(null);

			if (existingArticlesInOrder == null) {
				return null;
			}

			existingArticlesInOrder.quantity = articlesInOrder.quantity;

			return articlesInOrderRepository.save(existingArticlesInOrder);
		}

		@DeleteMapping("/{orderId}/{articleId}")
		public void delete(
				@PathVariable String orderId,
				@PathVariable String articleId
		) {
			ArticlesInOrderId id = new ArticlesInOrderId(orderId, articleId);
			articlesInOrderRepository.deleteById(id);
		}
	}
}
package com.sve.workshops.hexagonal.exercice2.article;

import com.sve.workshops.hexagonal.exercice2.order.ArticleInOrderRepository;
import com.sve.workshops.hexagonal.exercice2.order.OrderRepository;
import com.sve.workshops.hexagonal.exercice2.stock.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ArticleInOrderRepository articleInOrderRepository;

    @BeforeEach
    void cleanDatabase() {
        articleInOrderRepository.deleteAll();
        stockRepository.deleteAll();
        orderRepository.deleteAll();
        articleRepository.deleteAll();
    }

    @Test
    @DisplayName("""
            Given an article payload
            When POST /articles is called
            Then the article is created and returned with a generated id
            """)
    void shouldCreateArticle() throws Exception {
        // Given
        String payload = """
                {
                  "name": "Clavier mécanique",
                  "description": "Clavier AZERTY rétroéclairé",
                  "price": 89.99
                }
                """;

        // When / Then
        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name").value("Clavier mécanique"))
                .andExpect(jsonPath("$.description").value("Clavier AZERTY rétroéclairé"))
                .andExpect(jsonPath("$.price").value(89.99));
    }

    @Test
    @DisplayName("""
            Given an existing article
            When GET /articles/{id} is called
            Then the article is returned
            """)
    void shouldGetArticleById() throws Exception {
        // Given
        String createdArticle = mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Souris",
                                  "description": "Souris sans fil",
                                  "price": 29.99
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String articleId = extractJsonValue(createdArticle, "id");

        // When / Then
        mockMvc.perform(get("/articles/{id}", articleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(articleId))
                .andExpect(jsonPath("$.name").value("Souris"))
                .andExpect(jsonPath("$.description").value("Souris sans fil"))
                .andExpect(jsonPath("$.price").value(29.99));
    }

    @Test
    @DisplayName("""
            Given multiple existing articles
            When GET /articles is called
            Then all articles are returned
            """)
    void shouldGetAllArticles() throws Exception {
        // Given
        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Écran",
                                  "description": "Écran 27 pouces",
                                  "price": 249.99
                                }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Webcam",
                                  "description": "Webcam HD",
                                  "price": 59.99
                                }
                                """))
                .andExpect(status().isOk());

        // When / Then
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("""
            Given an existing article
            When PUT /articles/{id} is called
            Then the article is updated
            """)
    void shouldUpdateArticle() throws Exception {
        // Given
        String createdArticle = mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Casque",
                                  "description": "Casque audio",
                                  "price": 79.99
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String articleId = extractJsonValue(createdArticle, "id");

        String updatePayload = """
                {
                  "name": "Casque Bluetooth",
                  "description": "Casque audio sans fil",
                  "price": 99.99
                }
                """;

        // When / Then
        mockMvc.perform(put("/articles/{id}", articleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(articleId))
                .andExpect(jsonPath("$.name").value("Casque Bluetooth"))
                .andExpect(jsonPath("$.description").value("Casque audio sans fil"))
                .andExpect(jsonPath("$.price").value(99.99));
    }

    @Test
    @DisplayName("""
            Given an existing article
            When DELETE /articles/{id} is called
            Then the article is deleted
            """)
    void shouldDeleteArticle() throws Exception {
        // Given
        String createdArticle = mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Microphone",
                                  "description": "Microphone USB",
                                  "price": 49.99
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String articleId = extractJsonValue(createdArticle, "id");

        // When
        mockMvc.perform(delete("/articles/{id}", articleId))
                .andExpect(status().isOk());

        // Then
        mockMvc.perform(get("/articles/{id}", articleId))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("""
            Given a stock payload
            When POST /stocks is called
            Then the stock is created
            """)
    void shouldCreateStock() throws Exception {
        // Given
        String payload = """
                {
                  "articleId": "article-001",
                  "quantity": 15
                }
                """;

        // When / Then
        mockMvc.perform(post("/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleId").value("article-001"))
                .andExpect(jsonPath("$.quantity").value(15));
    }

    @Test
    @DisplayName("""
            Given an existing stock
            When GET /stocks/{articleId} is called
            Then the stock is returned
            """)
    void shouldGetStockByArticleId() throws Exception {
        // Given
        mockMvc.perform(post("/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "articleId": "article-002",
                                  "quantity": 20
                                }
                                """))
                .andExpect(status().isOk());

        // When / Then
        mockMvc.perform(get("/stocks/{articleId}", "article-002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleId").value("article-002"))
                .andExpect(jsonPath("$.quantity").value(20));
    }

    @Test
    @DisplayName("""
            Given an existing stock
            When PUT /stocks/{articleId} is called
            Then the stock quantity is updated
            """)
    void shouldUpdateStock() throws Exception {
        // Given
        mockMvc.perform(post("/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "articleId": "article-003",
                                  "quantity": 5
                                }
                                """))
                .andExpect(status().isOk());

        // When / Then
        mockMvc.perform(put("/stocks/{articleId}", "article-003")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "quantity": 12
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleId").value("article-003"))
                .andExpect(jsonPath("$.quantity").value(12));
    }

    @Test
    @DisplayName("""
            Given an order payload
            When POST /orders is called
            Then the order is created and returned with a generated id
            """)
    void shouldCreateOrder() throws Exception {
        // Given
        String payload = """
                {
                  "username": "alice",
                  "status": "CREATED"
                }
                """;

        // When / Then
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.username").value("alice"))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

    @Test
    @DisplayName("""
            Given an existing order
            When PUT /orders/{id} is called
            Then the order is updated
            """)
    void shouldUpdateOrder() throws Exception {
        // Given
        String createdOrder = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "bob",
                                  "status": "CREATED"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String orderId = extractJsonValue(createdOrder, "id");

        // When / Then
        mockMvc.perform(put("/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "bob",
                                  "status": "PAID"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.username").value("bob"))
                .andExpect(jsonPath("$.status").value("PAID"));
    }

    @Test
    @DisplayName("""
            Given an article and an order
            When POST /articles-in-order is called
            Then the article is added to the order
            """)
    void shouldAddArticleInOrder() throws Exception {
        // Given
        String createdArticle = mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Laptop",
                                  "description": "Ordinateur portable",
                                  "price": 999.99
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String articleId = extractJsonValue(createdArticle, "id");

        String createdOrder = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "charlie",
                                  "status": "CREATED"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String orderId = extractJsonValue(createdOrder, "id");

        String payload = """
                {
                  "orderId": "%s",
                  "articleId": "%s",
                  "quantity": 2
                }
                """.formatted(orderId, articleId);

        // When / Then
        mockMvc.perform(post("/articles-in-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(orderId))
                .andExpect(jsonPath("$.articleId").value(articleId))
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    @DisplayName("""
            Given an article already added to an order
            When PUT /articles-in-order/{orderId}/{articleId} is called
            Then the quantity is updated
            """)
    void shouldUpdateArticleQuantityInOrder() throws Exception {
        // Given
        String orderId = "order-001";
        String articleId = "article-004";

        mockMvc.perform(post("/articles-in-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "orderId": "order-001",
                                  "articleId": "article-004",
                                  "quantity": 1
                                }
                                """))
                .andExpect(status().isOk());

        // When / Then
        mockMvc.perform(put("/articles-in-order/{orderId}/{articleId}", orderId, articleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "quantity": 4
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(orderId))
                .andExpect(jsonPath("$.articleId").value(articleId))
                .andExpect(jsonPath("$.quantity").value(4));
    }

    @Test
    @DisplayName("""
            Given an article already added to an order
            When DELETE /articles-in-order/{orderId}/{articleId} is called
            Then the article is removed from the order
            """)
    void shouldDeleteArticleFromOrder() throws Exception {
        // Given
        String orderId = "order-002";
        String articleId = "article-005";

        mockMvc.perform(post("/articles-in-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "orderId": "order-002",
                                  "articleId": "article-005",
                                  "quantity": 3
                                }
                                """))
                .andExpect(status().isOk());

        // When
        mockMvc.perform(delete("/articles-in-order/{orderId}/{articleId}", orderId, articleId))
                .andExpect(status().isOk());

        // Then
        mockMvc.perform(get("/articles-in-order/{orderId}/{articleId}", orderId, articleId))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    private String extractJsonValue(String json, String fieldName) {
        String field = "\"" + fieldName + "\":\"";
        int start = json.indexOf(field) + field.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}

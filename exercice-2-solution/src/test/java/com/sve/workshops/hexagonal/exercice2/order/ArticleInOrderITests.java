package com.sve.workshops.hexagonal.exercice2.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleInOrderITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleInOrderRepository articleInOrderRepository;

    @BeforeEach
    void cleanDatabase() {
        articleInOrderRepository.deleteAll();
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

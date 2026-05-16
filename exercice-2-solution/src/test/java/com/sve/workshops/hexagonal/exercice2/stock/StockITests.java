package com.sve.workshops.hexagonal.exercice2.stock;

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
class StockITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void cleanDatabase() {
        stockRepository.deleteAll();
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
}

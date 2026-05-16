package com.sve.workshops.hexagonal.exercice2.article;

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

    @BeforeEach
    void cleanDatabase() {
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

    private String extractJsonValue(String json, String fieldName) {
        String field = "\"" + fieldName + "\":\"";
        int start = json.indexOf(field) + field.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}

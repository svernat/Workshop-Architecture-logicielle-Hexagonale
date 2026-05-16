package com.sve.workshops.hexagonal.exercice2.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void cleanDatabase() {
        orderRepository.deleteAll();
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

    private String extractJsonValue(String json, String fieldName) {
        String field = "\"" + fieldName + "\":\"";
        int start = json.indexOf(field) + field.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}

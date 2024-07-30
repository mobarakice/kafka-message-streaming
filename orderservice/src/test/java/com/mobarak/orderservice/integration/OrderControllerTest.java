package com.mobarak.orderservice.integration;

import com.mobarak.orderservice.entity.OrderEntity;
import com.mobarak.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createOrder_ShouldCreateOrderAndReturnOrder() throws Exception {
        var order = getOrderEntity();
        given(orderService.createOrder(any(OrderEntity.class))).willReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 100, \"quantity\": 10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.productId").value(order.getProductId()))
                .andExpect(jsonPath("$.quantity").value(order.getQuantity()))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

    @Test
    void findById_OrderId() throws Exception {
        var order = getOrderEntity();
        order.setStatus("processed");
        given(orderService.createOrder(any(OrderEntity.class))).willReturn(order);
        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk());
    }

    private OrderEntity getOrderEntity() {
        var order = new OrderEntity();
        order.setId(1L);
        order.setProductId(100L);
        order.setQuantity(10);
        order.setStatus("CREATED");
        return order;
    }
}
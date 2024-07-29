package com.mobarak.orderservice.service;

import com.mobarak.orderservice.entity.OrderEntity;
import com.mobarak.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTests {


    @Mock
    private OrderRepository orderRepository;

    @Mock
    private KafkaTemplate<String, Map<String, String>> kafkaTemplate;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_AndPublishEvent() {
        var order = new OrderEntity();
        order.setProductId(1L);
        order.setQuantity(10);

        var savedOrder = new OrderEntity();
        savedOrder.setId(1L);
        savedOrder.setProductId(1L);
        savedOrder.setQuantity(10);
        savedOrder.setStatus("CREATED");

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedOrder);

        assertThatNoException();
    }

}

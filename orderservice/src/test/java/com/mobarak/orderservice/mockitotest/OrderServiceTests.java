package com.mobarak.orderservice.mockitotest;

import com.mobarak.orderservice.entity.OrderEntity;
import com.mobarak.orderservice.repository.OrderRepository;
import com.mobarak.orderservice.service.OrderService;
import com.mobarak.orderservice.util.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private KafkaTemplate<String, Map<String, String>> kafkaTemplate;

    @InjectMocks
    private OrderService orderService;
    private Map<String, String> map;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        map = new HashMap<>(Map.ofEntries(Map.entry(Constant.PRODUCT_ID, "1"),
                Map.entry(Constant.QUANTITY, "10"),
                Map.entry(Constant.STATUS, "CREATED")));
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
        var result = orderService.createOrder(order);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("CREATED");

        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(kafkaTemplate, times(1)).send("order.created", map);
    }

    @Test
    void updateOrderStatus_ShouldPublishEventAndUpdateOrder() {
        var order = new OrderEntity();
        order.setId(1L);
        order.setProductId(1L);
        order.setQuantity(10);
        order.setStatus("CREATED");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(order);
        map.put(Constant.STATUS, "PROCESSED");

        orderService.updateOrderStatus(map);

        ArgumentCaptor<OrderEntity> orderArgumentCaptor = ArgumentCaptor.forClass(OrderEntity.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        assertThat(orderArgumentCaptor.getValue().getStatus()).isEqualTo("PROCESSED");
    }

}

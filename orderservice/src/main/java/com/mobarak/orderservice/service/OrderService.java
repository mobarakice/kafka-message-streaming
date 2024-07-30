package com.mobarak.orderservice.service;

import com.mobarak.orderservice.entity.OrderEntity;
import com.mobarak.orderservice.repository.OrderRepository;
import com.mobarak.orderservice.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, Map<String, String>> kafkaTemplate;

    public OrderEntity createOrder(OrderEntity order) {
        order.setStatus("created");
        OrderEntity savedOrder = orderRepository.save(order);
        kafkaTemplate.send(Constant.ORDER_CREATED_TOPIC, prepareKafkaMessage(savedOrder));
        return savedOrder;
    }

    @KafkaListener(topics = Constant.ORDER_PROCESSED_TOPIC)
    public void updateOrderStatus(Map<String, String> orderStatus) {
        orderRepository.findById(Long.parseLong(orderStatus.get(Constant.PRODUCT_ID)))
                .ifPresent(entity -> {
                    entity.setStatus(orderStatus.get(Constant.STATUS));
                    orderRepository.save(entity);
                });
    }

    public OrderEntity findById(long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    private Map<String, String> prepareKafkaMessage(OrderEntity order) {
        return Map.ofEntries(
                Map.entry(Constant.PRODUCT_ID, String.valueOf(order.getProductId())),
                Map.entry(Constant.QUANTITY, String.valueOf(order.getQuantity())),
                Map.entry(Constant.STATUS, order.getStatus())
        );
    }
}
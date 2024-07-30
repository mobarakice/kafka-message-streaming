//package com.mobarak.orderservice.integration;
//
//import com.mobarak.orderservice.entity.OrderEntity;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.ResponseEntity;
//
//import java.net.URI;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class OrderControllerIntegrationTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    void createOrder_ShouldCreateOrderAndReturnOrder() throws Exception {
//        URI uri = new URI("http://localhost:" + port + "/orders");
//        var order = new OrderEntity();
//        order.setProductId(100L);
//        order.setQuantity(10);
//
//        ResponseEntity<OrderEntity> response = restTemplate.postForEntity(uri, order, OrderEntity.class);
//
//        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
//        assertThat(response.getBody()).isNotNull();
//        assertThat(response.getBody().getProductId()).isEqualTo(order.getProductId());
//        assertThat(response.getBody().getQuantity()).isEqualTo(order.getQuantity());
//        assertThat(response.getBody().getStatus()).isEqualTo("CREATED");
//    }
//
//    @Test
//    void findOrderById() throws Exception {
//        URI uri = new URI("http://localhost:" + port + "/orders/1");
//        ResponseEntity<OrderEntity> response = restTemplate.getForEntity(uri, OrderEntity.class);
//
//        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
//    }
//}
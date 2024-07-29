package com.mobarak.inventoryservice.service;

import com.mobarak.inventoryservice.entity.InventoryEntity;
import com.mobarak.inventoryservice.repository.InventoryRepository;
import com.mobarak.inventoryservice.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    private final KafkaTemplate<String, Map<String, String>> kafkaTemplate;


    public void createInventory(InventoryEntity inventoryEntity) {
        Optional.ofNullable(findByProductId(inventoryEntity.getProductId())).ifPresent(
                entity-> inventoryEntity.setId(entity.getId())
        );
        inventoryRepository.save(inventoryEntity);
    }

    public InventoryEntity findByProductId(long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    @KafkaListener(topics = Constant.ORDER_CREATED_TOPIC)
    public void checkInventory(Map<String, String> orderInfo) {
        var productId = Long.parseLong(orderInfo.get(Constant.PRODUCT_ID));
        var quantity = Integer.parseInt(orderInfo.get(Constant.QUANTITY));
        var map = new HashMap<>(orderInfo);
        InventoryEntity inventory = inventoryRepository.findByProductId(productId);
        if (inventory != null && inventory.getQuantity() >= quantity) {
            // Update inventory
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryRepository.save(inventory);
            map.put(Constant.STATUS, "processed");
        } else {
            map.put(Constant.STATUS, "out_of_stock");
        }

        kafkaTemplate.send(Constant.ORDER_PROCESSED_TOPIC, map);
    }

}
package com.mobarak.inventoryservice.repository;

import com.mobarak.inventoryservice.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    InventoryEntity findByProductId(Long productId);
}
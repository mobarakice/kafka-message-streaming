package com.mobarak.inventory.repository;

import com.mobarak.inventory.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    InventoryEntity findByProductId(Long productId);
}
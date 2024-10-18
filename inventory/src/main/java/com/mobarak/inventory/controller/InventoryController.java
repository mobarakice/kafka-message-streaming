package com.mobarak.inventory.controller;

import com.mobarak.inventory.entity.InventoryEntity;
import com.mobarak.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<String> createOrUpdateInventory(@RequestBody InventoryEntity inventoryEntity) {
        try {
            inventoryService.createInventory(inventoryEntity);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryEntity> findByProductId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(Optional
                    .ofNullable(inventoryService.findByProductId(id))
                    .orElseThrow());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found", e);
        }
    }
}
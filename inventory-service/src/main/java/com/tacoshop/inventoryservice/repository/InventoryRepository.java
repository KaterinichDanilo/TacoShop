package com.tacoshop.inventoryservice.repository;

import com.tacoshop.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

    Optional<Inventory> findByTacoName(String tacoName);
}

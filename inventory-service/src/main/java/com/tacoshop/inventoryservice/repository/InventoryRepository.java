package com.tacoshop.inventoryservice.repository;

import com.tacoshop.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    List<Inventory> findByIngredientNameIn(List<String> ingredientNameList);
}

package com.tacoshop.inventoryservice.service;

import com.tacoshop.inventoryservice.dto.InventoryResponse;
import com.tacoshop.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(Map<String, Integer> ingredients) {
        List<InventoryResponse> responses = inventoryRepository.findByIngredientNameIn(ingredients.keySet().stream().toList())
                .stream().map(inventory ->
                        InventoryResponse.builder()
                                .ingredientName(inventory.getIngredientName())
                                .isInStock(inventory.getQuantity() >= ingredients.get(inventory.getIngredientName()))
                                .build()
                ).toList();
        return responses;
    }
}

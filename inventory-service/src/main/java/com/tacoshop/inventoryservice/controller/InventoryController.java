package com.tacoshop.inventoryservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacoshop.inventoryservice.dto.InventoryRequest;
import com.tacoshop.inventoryservice.dto.InventoryResponse;
import com.tacoshop.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/areInStock")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestBody List<InventoryRequest> ingredients) {
        Map<String, Integer> ingredientsMap = ingredients.stream().collect(Collectors
                .toMap(InventoryRequest::getIngredientName, InventoryRequest::getQuantity));

        return inventoryService.isInStock(ingredientsMap);
    }
}

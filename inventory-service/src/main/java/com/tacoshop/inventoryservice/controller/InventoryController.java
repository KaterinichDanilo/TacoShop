package com.tacoshop.inventoryservice.controller;

import com.tacoshop.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/isInStock")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String tacoName) {
        return inventoryService.isInStock(tacoName);
    }
}

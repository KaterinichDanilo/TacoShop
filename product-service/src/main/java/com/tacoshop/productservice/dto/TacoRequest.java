package com.tacoshop.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TacoRequest {
    private String name;
    private Map<String, Integer> ingredients;
    private double price;
}

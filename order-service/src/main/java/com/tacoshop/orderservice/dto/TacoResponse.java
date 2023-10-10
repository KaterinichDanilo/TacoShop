package com.tacoshop.orderservice.dto;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class TacoResponse {
    private String name;
    private Map<String, Integer> ingredients;
    private double price;
}

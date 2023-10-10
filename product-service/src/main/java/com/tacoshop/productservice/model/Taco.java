package com.tacoshop.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(value = "taco")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Taco {
    @Id
    private String name;
    @Indexed(unique = true)
    private Map<String, Integer> ingredients;
    private double price;
}

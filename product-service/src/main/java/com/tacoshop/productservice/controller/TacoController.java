package com.tacoshop.productservice.controller;

import com.tacoshop.productservice.dto.TacoRequest;
import com.tacoshop.productservice.dto.TacoResponse;
import com.tacoshop.productservice.service.TacoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taco")
@RequiredArgsConstructor
public class TacoController {
    private final TacoService tacoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTaco(@RequestBody TacoRequest tacoRequest){
        tacoService.createTaco(tacoRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TacoResponse> getAllTacos() {
        return tacoService.getAllTacos();
    }
}

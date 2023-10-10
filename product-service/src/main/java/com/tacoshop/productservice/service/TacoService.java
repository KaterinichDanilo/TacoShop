package com.tacoshop.productservice.service;

import com.tacoshop.productservice.dto.TacoRequest;
import com.tacoshop.productservice.dto.TacoResponse;
import com.tacoshop.productservice.model.Taco;
import com.tacoshop.productservice.repository.TacoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TacoService {
    private final TacoRepository tacoRepository;

    public void createTaco(TacoRequest tacoRequest) {
        Taco taco = Taco.builder()
                .name(tacoRequest.getName())
                .price(tacoRequest.getPrice())
                .ingredients(tacoRequest.getIngredients())
                .build();

        tacoRepository.save(taco);
        log.info("Taco {} was saved!", taco.getName());
    }

    public List<TacoResponse> getAllTacos() {
        List<Taco> tacoList = tacoRepository.findAll();
        return tacoList.stream().map(this::mapToTacoResponse).collect(Collectors.toList());
    }

    public List<TacoResponse> getAllTacosByNameIn(List<String> tacoNamesList) {
        List<Taco> tacoList = tacoRepository.findAllByNameIn(tacoNamesList);
        return tacoList.stream().map(this::mapToTacoResponse).collect(Collectors.toList());
    }

    private TacoResponse mapToTacoResponse(Taco taco) {
        return TacoResponse.builder()
                .name(taco.getName())
                .price(taco.getPrice())
                .ingredients(taco.getIngredients())
                .build();
    }
}

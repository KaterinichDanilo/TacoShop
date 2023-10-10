package com.tacoshop.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacoshop.orderservice.dto.*;
import com.tacoshop.orderservice.exception.OrderNotFoundException;
import com.tacoshop.orderservice.model.Order;
import com.tacoshop.orderservice.model.OrderItem;
import com.tacoshop.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final String inventoryServiceUrl = "http://inventory-service/api/inventory";
    private final String productServiceUrl = "http://product-service/api/taco";

    public void createOrder(OrderRequest orderRequest) {
        List<String> tacoNamesList = orderRequest.getOrderItemDtoList()
                .stream()
                .map(OrderItemDto::getTacoName)
                .collect(Collectors.toList());
        System.out.println("Order service: tacoNamesList was created");

        TacoResponse[] tacoResponseArray = webClientBuilder.build().get()
                .uri(productServiceUrl + "/allByNames",
                        uriBuilder -> uriBuilder.queryParam("tacosNamesList", tacoNamesList).build())
                .retrieve()
                .bodyToMono(TacoResponse[].class)
                .block();

        System.out.println("Order service: get tacoResponseList. Size: " + tacoResponseArray.length);
        for (TacoResponse tacoResponse:tacoResponseArray) {
            System.out.println(tacoResponse);
        }

        Map<String, Integer> ingredientsQuantityInOrder = getAllIngredientsForOrder(orderRequest, Arrays.stream(tacoResponseArray).toList());
        List<InventoryRequest> inventoryRequestList = ingredientsQuantityInOrder.entrySet().stream()
                .map(entry -> new InventoryRequest(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().post()
                .uri(inventoryServiceUrl + "/areInStock")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(inventoryRequestList))
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean areIngredientsEnough = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

        if (areIngredientsEnough) {
            Order order = Order.builder()
                    .orderItems(orderRequest.getOrderItemDtoList().stream()
                            .map(this::mapToDto).toList())
                    .build();
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("There are no ingredients in the store!");
        }

    }

    private OrderItem mapToDto(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPricePerOne(orderItemDto.getPricePerOne());
        orderItem.setTacoName(orderItemDto.getTacoName());

        return orderItem;
    }

    @SneakyThrows
    public Order getOrderById(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return order;
    }

    private Map<String, Integer> getAllIngredientsForOrder(OrderRequest order, List<TacoResponse> tacoResponseList) {
        HashMap<String, Integer> ingredientsQuantityInOrder = new HashMap<>();

        for (OrderItemDto orderItem:order.getOrderItemDtoList()) {
            String tacoName = orderItem.getTacoName();
            int quantity = orderItem.getQuantity();
            TacoResponse tacoResponse = tacoResponseList.stream()
                    .filter(taco -> taco.getName().equals(tacoName))
                    .findFirst().orElse(null);
            if (tacoResponse != null) {
                for (String ingredient:tacoResponse.getIngredients().keySet()) {
                    int ingrQuantity = tacoResponse.getIngredients().get(ingredient) * quantity;
                    if (ingredientsQuantityInOrder.keySet().contains(ingredient)) {
                        ingredientsQuantityInOrder.put(ingredient, ingredientsQuantityInOrder.get(ingredient) + ingrQuantity);
                    } else {
                        ingredientsQuantityInOrder.put(ingredient, ingrQuantity);
                    }
                }
            }
        }

        return ingredientsQuantityInOrder;
    }
}

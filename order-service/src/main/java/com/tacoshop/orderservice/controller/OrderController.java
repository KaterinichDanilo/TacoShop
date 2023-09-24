package com.tacoshop.orderservice.controller;

import com.tacoshop.orderservice.dto.OrderRequest;
import com.tacoshop.orderservice.model.Order;
import com.tacoshop.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    private void createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
    }

    @GetMapping("/getById")
    private Order createOrder(@RequestParam long id) {
        return orderService.getOrderById(id);
    }
}

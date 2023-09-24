package com.tacoshop.orderservice.service;

import com.tacoshop.orderservice.dto.OrderItemDto;
import com.tacoshop.orderservice.dto.OrderRequest;
import com.tacoshop.orderservice.exception.OrderNotFoundException;
import com.tacoshop.orderservice.model.Order;
import com.tacoshop.orderservice.model.OrderItem;
import com.tacoshop.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void createOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderItems(orderRequest.getOrderItemDtoList().stream()
                        .map(this::mapToDto).toList())
                .build();
        orderRepository.save(order);
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
}

package com.tacoshop.orderservice.exception;

public class OrderNotFoundException extends Exception{
    private long id;

    public OrderNotFoundException(long id) {
        super("Order with id=" + id + " not found!");
    }
}

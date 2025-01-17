package com.example.heb.model;

public interface OrderProjection {
    String getOrderId();
    Integer getUpc();

    String getName();

    Integer getQuantity();
}

package com.example.heb.controller;

import com.example.heb.entity.Order;
import com.example.heb.exception.ResourceNotFoundException;
import com.example.heb.model.OrderModel;
import com.example.heb.model.OrderProjection;
import com.example.heb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderModel order) {
        Order finalResult = orderService.createOrder(order);

        return new ResponseEntity<>(finalResult , HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable String orderId) {
        Order finalResult = orderService.getOrderById(orderId);
        if(finalResult!=null) {
            return new ResponseEntity<>(finalResult, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Order not found for given orderId : " + orderId);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrderByUpc(@RequestParam Integer upc) {
        List<OrderProjection> finalResult = orderService.getOrderByUpc(upc);
        if(!CollectionUtils.isEmpty(finalResult)) {
            return new ResponseEntity<>(finalResult, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Order not found for given upc : " + upc);
        }
    }
}

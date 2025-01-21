package com.example.heb.controller;

import com.example.heb.entity.Order;
import com.example.heb.exception.ResourceNotFoundException;
import com.example.heb.model.ItemsModel;
import com.example.heb.model.OrderModel;
import com.example.heb.model.OrderProjection;
import com.example.heb.service.OrderService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Validated
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody List<OrderModel> order) {
        List<OrderModel> finalResult = orderService.createOrder(order);
        return new ResponseEntity<>(finalResult , HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable @NotBlank String orderId) {
        OrderModel finalResult = orderService.getOrderById(orderId);
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

    @GetMapping("/orders/expectedTime")
    public ResponseEntity<?> getOrderByExpectedTime(@RequestParam @NotBlank String until) {
        Map<String, List<ItemsModel>> finalResult = orderService.getOrderByExpectedTime(until);
        if(!CollectionUtils.isEmpty(finalResult)) {
            return new ResponseEntity<>(finalResult, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No orders available by the expected time");
        }
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<?> deleteOrderById(@PathVariable @NotBlank String orderId) {
        orderService.deleteOrderById(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

package com.example.heb.service;

import com.example.heb.entity.Items;
import com.example.heb.entity.Order;
import com.example.heb.model.ItemsModel;
import com.example.heb.model.OrderModel;
import com.example.heb.model.OrderProjection;
import com.example.heb.repository.ItemsRepository;
import com.example.heb.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    OrderRepository orderRepository;


    @Autowired
    ItemsRepository itemsRepository;

    public Order createOrder(OrderModel order){

        log.info("Entered create order method");
        Order orderEntity = convertEntityToModel(order);

        Optional<Order> result = orderRepository.findById(order.getOrder());
        if(result.isPresent()){
            return result.get();
        } else {
            orderEntity = orderRepository.save(orderEntity);
        }
        return orderEntity;
    }

    private Order convertEntityToModel(OrderModel order){
        Order orderEntity = new Order();
        List<Items> itemsList = new ArrayList<>();
        //order.setPhone(order.getCustomer().getPhone());
        //order.setFirstName(order.getCustomer().getName().getFirstName());
        //order.setLastName(order.getCustomer().getName().getLastName());

        orderEntity.setOrder(order.getOrder());
        orderEntity.setOrderDate(order.getOrderDate());
        orderEntity.setExpectedPickupTime(order.getExpectedPickupTime());
        orderEntity.setStoreId(order.getStoreId());
        for(ItemsModel item:order.getItems()){
            Items items = new Items();
            items.setName(item.getName());
            items.setUpc(item.getUpc());
            items.setQuantity(item.getQuantity());
            items.setOrder_id(order.getOrder());
            itemsList.add(items);
        }
        orderEntity.setItems(itemsList);
        return orderEntity;
    }

    public Order getOrderById(String orderId){
        Optional<Order> result = orderRepository.findById(orderId);
        return result.orElse(null);
    }

    public List<OrderProjection> getOrderByUpc(Integer upc){
        return orderRepository.getOrderByUpc(upc);
    }
}

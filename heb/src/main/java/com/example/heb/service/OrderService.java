package com.example.heb.service;

import com.example.heb.entity.Items;
import com.example.heb.entity.Order;
import com.example.heb.model.*;
import com.example.heb.repository.ItemsRepository;
import com.example.heb.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    OrderRepository orderRepository;


    @Autowired
    ItemsRepository itemsRepository;

    public List<OrderModel> createOrder(List<OrderModel> orderList){

        log.info("Entered create order method");
        List<Order> orderEntityList = convertModelToEntity(orderList);
        orderRepository.saveAll(orderEntityList);
        return orderList;
    }

    private List<Order> convertModelToEntity(List<OrderModel> orderModelList){
        List<Order> orderEntityList = new ArrayList<>();

        //parallel performance with synchronization
        orderModelList.parallelStream().forEach(o ->{
            Order orderEntity = new Order();
            List<Items> itemsList = new ArrayList<>();
            orderEntity.setOrder(o.getOrder());
            orderEntity.setOrderDate(o.getOrderDate());
            orderEntity.setExpectedPickupTime(parseTime(o.getExpectedPickupTime()));
            orderEntity.setStoreId(o.getStoreId());
            orderEntity.setPhone(o.getCustomer().getPhone());
            orderEntity.setFirstName(o.getCustomer().getName().getFirstName());
            orderEntity.setLastName(o.getCustomer().getName().getLastName());
            o.getItems().forEach(i->{
                Items items = new Items();
                items.setName(i.getName());
                items.setUpc(i.getUpc());
                items.setQuantity(i.getQuantity());
                items.setOrder_id(o.getOrder());
                itemsList.add(items);
            });
            orderEntity.setItems(itemsList);
            synchronized (orderEntityList) {
                orderEntityList.add(orderEntity);
            }
        });

//serial performance with non synchronization
/*
        for(OrderModel order:orderModelList) {
            Order orderEntity = new Order();
            List<Items> itemsList = new ArrayList<>();

            orderEntity.setOrder(order.getOrder());
            orderEntity.setOrderDate(order.getOrderDate());
            orderEntity.setExpectedPickupTime(parseTime(order.getExpectedPickupTime()));
            orderEntity.setStoreId(order.getStoreId());
            orderEntity.setPhone(order.getCustomer().getPhone());
            orderEntity.setFirstName(order.getCustomer().getName().getFirstName());
            orderEntity.setLastName(order.getCustomer().getName().getLastName());
            for (ItemsModel item : order.getItems()) {
                Items items = new Items();
                items.setName(item.getName());
                items.setUpc(item.getUpc());
                items.setQuantity(item.getQuantity());
                items.setOrder_id(order.getOrder());
                itemsList.add(items);
            }
            orderEntity.setItems(itemsList);
            orderEntityList.add(orderEntity);
        }*/

        return orderEntityList;
    }

    private OrderModel convertEntityToModel(Order order){
        OrderModel orderModel = new OrderModel();
        ItemsModel itemsModel = new ItemsModel();
        CustomerModel customerModel = new CustomerModel();
        NameModel nameModel = new NameModel();
        nameModel.setFirstName(order.getFirstName());
        nameModel.setLastName(order.getLastName());
        customerModel.setName(nameModel);
        customerModel.setPhone(order.getPhone());
        List<ItemsModel> itemsModelList = new ArrayList<>();
        orderModel.setOrder(order.getOrder());
        orderModel.setStoreId(order.getStoreId());
        orderModel.setPhone(order.getPhone());
        orderModel.setOrderDate(order.getOrderDate());
        orderModel.setCustomer(customerModel);
        itemsModel.setUpc(order.getItems().get(0).getUpc());
        itemsModel.setQuantity(order.getItems().get(0).getQuantity());
        itemsModel.setName(order.getItems().get(0).getName());
        itemsModelList.add(itemsModel);
        orderModel.setItems(itemsModelList);
        return orderModel;
    }

    private String parseTime(String time){
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("h:mma", Locale.US)).format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public OrderModel getOrderById(String orderId){
        Optional<Order> result = orderRepository.findById(orderId);
        OrderModel orderModel = null;
        if(result.isPresent()) {
            orderModel = convertEntityToModel(result.get());
        }
        return orderModel;
    }

    public List<OrderProjection> getOrderByUpc(Integer upc){
        return orderRepository.getOrderByUpc(upc);
    }

    public Map<String, List<ItemsModel>> getOrderByExpectedTime(String expectedTime){
        Map<String, List<ItemsModel>> finalResult = new HashMap<>();
        expectedTime = parseTime(expectedTime);
        List<OrderProjection> result = orderRepository.getOrderByExpectedTime(expectedTime);
        for(OrderProjection data : result){
            if(!finalResult.containsKey(data.getOrderId())){
                List<ItemsModel> itemsModelList = new ArrayList<>();
                itemsModelList.add(setItemsModelData(data));
                finalResult.put(data.getOrderId(), itemsModelList);
            } else {
                List<ItemsModel> itemsModelList = finalResult.get(data.getOrderId());
                itemsModelList.add(setItemsModelData(data));
                finalResult.put(data.getOrderId(), itemsModelList);
            }
        }
        return finalResult;
    }

    @Transactional
    public void deleteOrderById(String orderId){
        orderRepository.deleteFromItemsByOrderId(orderId);
        orderRepository.deleteByOrderId(orderId);
    }

    private ItemsModel setItemsModelData(OrderProjection data){
        ItemsModel itemsModel = new ItemsModel();
        itemsModel.setUpc(data.getUpc());
        itemsModel.setName(data.getName());
        itemsModel.setQuantity(data.getQuantity());
        itemsModel.setOrderId(data.getOrderId());
        return itemsModel;
    }
}

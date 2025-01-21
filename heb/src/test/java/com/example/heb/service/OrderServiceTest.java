package com.example.heb.service;

import com.example.heb.entity.Items;
import com.example.heb.entity.Order;
import com.example.heb.model.ItemsModel;
import com.example.heb.model.OrderModel;
import com.example.heb.model.OrderProjection;
import com.example.heb.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testGetOrderById() {
        Order order = new Order();
        Items items = new Items();
        items.setName("carrot");
        items.setQuantity(2);
        items.setUpc(8993);
        List<Items> itemsList = new ArrayList<>();
        itemsList.add(items);
        order.setItems(itemsList);
        order.setStoreId("127");
        order.setOrderDate("1-17-2024");
        order.setExpectedPickupTime("3:00PM");
        order.setPhone("469-632-8971");
        Mockito.when(orderRepository.findById("HEB34334")).thenReturn(java.util.Optional.of(order));

        OrderModel result = orderService.getOrderById("HEB34334");

        Mockito.verify(orderRepository).findById("HEB34334");
        assertEquals(order.getOrder(), result.getOrder());
        assertEquals(order.getStoreId(), result.getStoreId());
        assertEquals(order.getPhone(), result.getPhone());
    }

    @Test
    public void testGetOrderByUpc() {
        List<OrderProjection> mockOrders = List.of(
                new OrderProjection() {
                    @Override
                    public String getOrderId() {
                        return "";
                    }

                    @Override
                    public Integer getUpc() {
                        return 8994;
                    }

                    @Override
                    public String getName() {
                        return "Carrot";
                    }

                    @Override
                    public Integer getQuantity() {
                        return 3;
                    }
                }
        );

        Mockito.when(orderRepository.getOrderByUpc(8994)).thenReturn(mockOrders);

        List<OrderProjection> result = orderService.getOrderByUpc(8994);

        Mockito.verify(orderRepository).getOrderByUpc(8994);
        assertEquals(mockOrders, result);
    }

    @Test
    public void testGetOrderByExpectedTime() {
        List<OrderProjection> mockOrders = List.of(
                new OrderProjection() {
                    @Override
                    public String getOrderId() {
                        return "HEB4334";
                    }

                    @Override
                    public Integer getUpc() {
                        return 1234;
                    }

                    @Override
                    public String getName() {
                        return "cabbage";
                    }

                    @Override
                    public Integer getQuantity() {
                        return 2;
                    }
                }, new OrderProjection(){
                    @Override
                    public String getOrderId() {
                        return "HEB4664";
                    }

                    @Override
                    public Integer getUpc() {
                        return 5678;
                    }

                    @Override
                    public String getName() {
                        return "carrot";
                    }

                    @Override
                    public Integer getQuantity() {
                        return 3;
                    }
                }
        );

        Map<String, List<ItemsModel>> order = new HashMap<>();
        List<ItemsModel> itemsModelList = new ArrayList<>();
        List<ItemsModel> itemsModelList1 = new ArrayList<>();
        ItemsModel itemsModel = new ItemsModel();
        ItemsModel itemsModel1 = new ItemsModel();
        itemsModel.setUpc(1234);
        itemsModel.setName("cabbage");
        itemsModel.setQuantity(2);
        itemsModel.setOrderId("HEB4334");
        itemsModelList.add(itemsModel);
        itemsModel1.setUpc(5678);
        itemsModel1.setName("carrot");
        itemsModel1.setQuantity(3);
        itemsModel1.setOrderId("HEB4664");
        itemsModelList1.add(itemsModel1);
        order.put("HEB4334", itemsModelList);
        order.put("HEB4664", itemsModelList1);
        Mockito.when(orderRepository.getOrderByExpectedTime("15:00")).thenReturn(mockOrders);

        Map<String, List<ItemsModel>> result = orderService.getOrderByExpectedTime("3:00PM");

        Mockito.verify(orderRepository).getOrderByExpectedTime("15:00");
        assertEquals(order.get("HEB4334").get(0).getName(), result.get("HEB4334").get(0).getName());
        assertEquals(order.get("HEB4664").get(0).getName(), result.get("HEB4664").get(0).getName());
    }
}

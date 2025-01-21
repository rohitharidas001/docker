package com.example.heb.controller;

import com.example.heb.entity.Items;
import com.example.heb.entity.Order;
import com.example.heb.model.CustomerModel;
import com.example.heb.model.ItemsModel;
import com.example.heb.model.NameModel;
import com.example.heb.model.OrderModel;
import com.example.heb.repository.OrderRepository;
import com.example.heb.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Test
    public void testCreateOrderEndPoint() throws Exception {
        CustomerModel customerModel = new CustomerModel();
        NameModel nameModel = new NameModel();
        nameModel.setFirstName("John");
        nameModel.setLastName("Wick");
        customerModel.setName(nameModel);
        customerModel.setPhone("469-871-0921");
        List<OrderModel> orderModelList = new ArrayList<>();
        List<ItemsModel> itemsModelList = new ArrayList<>();
        OrderModel orderModel = new OrderModel();
        ItemsModel itemsModel = new ItemsModel();
        itemsModel.setName("carrot");
        itemsModel.setQuantity(2);
        itemsModel.setUpc(8992);
        itemsModelList.add(itemsModel);
        orderModel.setOrder("HEB4334");
        orderModel.setStoreId("127");
        orderModel.setItems(itemsModelList);
        orderModel.setExpectedPickupTime("6:00AM");
        orderModel.setOrderDate("2023-06-30");
        orderModel.setCustomer(customerModel);
        orderModelList.add(orderModel);
        when(orderService.createOrder(orderModelList)).thenReturn(orderModelList);

        mockMvc.perform(post("/orders").content(new ObjectMapper().writeValueAsString(orderModelList)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGeOrderEndPoint() throws Exception {
        OrderModel orderModel = new OrderModel();
        ItemsModel itemsModel = new ItemsModel();
        List<ItemsModel> itemsModelList = new ArrayList<>();
        itemsModel.setName("carrot");
        itemsModel.setQuantity(2);
        itemsModel.setUpc(8992);
        itemsModelList.add(itemsModel);
        orderModel.setOrder("HEB4334");
        orderModel.setStoreId("127");
        orderModel.setItems(itemsModelList);
        orderModel.setExpectedPickupTime("6:00AM");
        orderModel.setOrderDate("2023-06-30");
        when(orderService.getOrderById("HEB4334")).thenReturn(orderModel);

        mockMvc.perform(get("/orders/{orderId}", "HEB4334"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteOrderEndPoint() throws Exception {
        Mockito.doNothing().when(orderService).deleteOrderById("HEB4334");

        mockMvc.perform(delete("/orders/{orderId}", "HEB4334"))
                .andExpect(status().isNoContent());
    }
}

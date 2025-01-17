package com.example.heb.repository;

import com.example.heb.entity.Order;
import com.example.heb.model.OrderProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order,String> {

    @Query(value = "SELECT o.order_id, i.upc, i.name, i.quantity FROM orders o inner join items i on o.order_id = i.order_id  WHERE i.upc = :upc", nativeQuery = true)
    List<OrderProjection> getOrderByUpc(Integer upc);
}

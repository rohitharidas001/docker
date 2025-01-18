package com.example.heb.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @Column(name="order_id")
    private String order;
    //Customer customer;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="order_id",referencedColumnName = "order_id")
    private List<Items> items;
    private String storeId;
    private String orderDate;
    private String expectedPickupTime;
    private String firstName;
    private String lastName;
    private String phone;

/*    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }*/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getExpectedPickupTime() {
        return expectedPickupTime;
    }

    public void setExpectedPickupTime(String expectedPickupTime) {
        this.expectedPickupTime = expectedPickupTime;
    }
}

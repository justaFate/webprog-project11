package webprog.anhphat_24162091.services;

import java.util.List;
import webprog.anhphat_24162091.entities.Order_24162091;

public interface IOrderService_24162091 {
    boolean createOrder(Order_24162091 order);
    Order_24162091 findById(String orderId);
    List<Order_24162091> findAll();
    List<Order_24162091> findByUsername(String username);
}

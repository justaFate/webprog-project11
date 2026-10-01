package webprog.anhphat_24162091.dao;

import java.util.List;
import webprog.anhphat_24162091.entities.Order_24162091;

public interface IOrderDao_24162091 {
    void insert(Order_24162091 order) throws Exception;
    void update(Order_24162091 order) throws Exception;
    Order_24162091 findById(String orderId);
    List<Order_24162091> findAll();
    List<Order_24162091> findByUsername(String username);
}

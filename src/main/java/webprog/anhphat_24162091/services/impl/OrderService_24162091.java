package webprog.anhphat_24162091.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import webprog.anhphat_24162091.dao.IOrderDao_24162091;
import webprog.anhphat_24162091.dao.impl.OrderDao_24162091;
import webprog.anhphat_24162091.entities.Order_24162091;
import webprog.anhphat_24162091.services.IOrderService_24162091;

public class OrderService_24162091 implements IOrderService_24162091 {

    private final IOrderDao_24162091 orderDao = new OrderDao_24162091();

    // Bộ nhớ đệm tĩnh lưu trữ đơn hàng an toàn (phục vụ hiển thị mượt mà kể cả khi DB tạm ngắt kết nối)
    private static final Map<String, Order_24162091> orderCache = new ConcurrentHashMap<>();

    @Override
    public boolean createOrder(Order_24162091 order) {
        if (order == null || order.getOrderId() == null) {
            return false;
        }

        // Lưu vào bộ nhớ đệm
        orderCache.put(order.getOrderId(), order);

        // Lưu bền vững vào cơ sở dữ liệu qua JPA
        try {
            orderDao.insert(order);
            return true;
        } catch (Exception e) {
            System.err.println("[ORDER SERVICE - 24162091] Lưu DB gặp sự cố (có thể do DB offline), đã lưu an toàn vào Cache: " + e.getMessage());
            // Vẫn trả về true vì đơn hàng đã được ghi nhận trong cache và hiển thị cho người dùng
            return true;
        }
    }

    @Override
    public Order_24162091 findById(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            return null;
        }

        // Kiểm tra trong cache trước
        if (orderCache.containsKey(orderId.trim())) {
            return orderCache.get(orderId.trim());
        }

        // Tra cứu trong cơ sở dữ liệu
        try {
            Order_24162091 order = orderDao.findById(orderId.trim());
            if (order != null) {
                orderCache.put(order.getOrderId(), order);
            }
            return order;
        } catch (Exception e) {
            System.err.println("[ORDER SERVICE - 24162091] Không thể kết nối DB để tìm đơn hàng, tra cứu Cache: " + e.getMessage());
            return orderCache.get(orderId.trim());
        }
    }

    @Override
    public List<Order_24162091> findAll() {
        try {
            List<Order_24162091> dbOrders = orderDao.findAll();
            if (dbOrders != null && !dbOrders.isEmpty()) {
                for (Order_24162091 o : dbOrders) {
                    orderCache.put(o.getOrderId(), o);
                }
                return dbOrders;
            }
        } catch (Exception e) {
            System.err.println("[ORDER SERVICE - 24162091] Lỗi findAll từ DB, sử dụng dữ liệu Cache: " + e.getMessage());
        }
        return new ArrayList<>(orderCache.values());
    }

    @Override
    public List<Order_24162091> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            List<Order_24162091> dbOrders = orderDao.findByUsername(username.trim());
            if (dbOrders != null && !dbOrders.isEmpty()) {
                return dbOrders;
            }
        } catch (Exception e) {
            System.err.println("[ORDER SERVICE - 24162091] Lỗi findByUsername từ DB, lọc qua Cache: " + e.getMessage());
        }

        List<Order_24162091> userOrders = new ArrayList<>();
        for (Order_24162091 o : orderCache.values()) {
            if (o.getUser() != null && username.trim().equalsIgnoreCase(o.getUser().getUsername())) {
                userOrders.add(o);
            }
        }
        return userOrders;
    }
}

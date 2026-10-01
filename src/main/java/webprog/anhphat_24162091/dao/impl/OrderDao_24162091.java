package webprog.anhphat_24162091.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import webprog.anhphat_24162091.configs.JpaConfig_24162091;
import webprog.anhphat_24162091.dao.IOrderDao_24162091;
import webprog.anhphat_24162091.entities.Order_24162091;

public class OrderDao_24162091 implements IOrderDao_24162091 {

    @Override
    public void insert(Order_24162091 order) throws Exception {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(order);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public void update(Order_24162091 order) throws Exception {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(order);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public Order_24162091 findById(String orderId) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            return enma.find(Order_24162091.class, orderId);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Order_24162091> findAll() {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            TypedQuery<Order_24162091> query = enma.createNamedQuery("Order_24162091.findAll", Order_24162091.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Order_24162091> findByUsername(String username) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT o FROM Order_24162091 o WHERE o.user.username = :username ORDER BY o.orderDate DESC";
            TypedQuery<Order_24162091> query = enma.createQuery(jpql, Order_24162091.class);
            query.setParameter("username", username);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }
}

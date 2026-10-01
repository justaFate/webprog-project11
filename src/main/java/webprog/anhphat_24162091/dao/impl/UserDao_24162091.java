package webprog.anhphat_24162091.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import webprog.anhphat_24162091.configs.JpaConfig_24162091;
import webprog.anhphat_24162091.dao.IUserDao_24162091;
import webprog.anhphat_24162091.entities.User_24162091;

public class UserDao_24162091 implements IUserDao_24162091 {

    @Override
    public void insert(User_24162091 user) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(user);
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
    public void update(User_24162091 user) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(user);
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
    public void delete(String username) throws Exception {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            User_24162091 user = enma.find(User_24162091.class, username);
            if (user != null) {
                enma.remove(user);
            } else {
                throw new Exception("Không tìm thấy người dùng với username: " + username);
            }
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
    public User_24162091 findById(String username) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            return enma.find(User_24162091.class, username);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<User_24162091> findAll() {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            TypedQuery<User_24162091> query = enma.createNamedQuery("User_24162091.findAll", User_24162091.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<User_24162091> findAll(int page, int pageSize) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            TypedQuery<User_24162091> query = enma.createNamedQuery("User_24162091.findAll", User_24162091.class);
            query.setFirstResult(page * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<User_24162091> searchByFullname(String fullname) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT u FROM User_24162091 u WHERE u.fullname LIKE :fullname";
            TypedQuery<User_24162091> query = enma.createQuery(jpql, User_24162091.class);
            query.setParameter("fullname", "%" + fullname + "%");
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public User_24162091 findByEmail(String email) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT u FROM User_24162091 u WHERE u.email = :email";
            TypedQuery<User_24162091> query = enma.createQuery(jpql, User_24162091.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            enma.close();
        }
    }

    @Override
    public User_24162091 checkLogin(String username, String password) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT u FROM User_24162091 u WHERE u.username = :username AND u.password = :password";
            TypedQuery<User_24162091> query = enma.createQuery(jpql, User_24162091.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            enma.close();
        }
    }

    @Override
    public int count() {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT count(u) FROM User_24162091 u";
            Query query = enma.createQuery(jpql);
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            enma.close();
        }
    }
}


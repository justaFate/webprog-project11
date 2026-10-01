package webprog.anhphat_24162091.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import webprog.anhphat_24162091.configs.JpaConfig_24162091;
import webprog.anhphat_24162091.dao.ICategoryDao_24162091;
import webprog.anhphat_24162091.entities.Category_24162091;

public class CategoryDao_24162091 implements ICategoryDao_24162091 {

    @Override
    public void insert(Category_24162091 category) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(category);
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
    public void update(Category_24162091 category) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(category);
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
    public void delete(int categoryId) throws Exception {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Category_24162091 category = enma.find(Category_24162091.class, categoryId);
            if (category != null) {
                enma.remove(category);
            } else {
                throw new Exception("Không tìm thấy Category với ID: " + categoryId);
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
    public Category_24162091 findById(int categoryId) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            return enma.find(Category_24162091.class, categoryId);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Category_24162091> findAll() {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            TypedQuery<Category_24162091> query = enma.createNamedQuery("Category_24162091.findAll", Category_24162091.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Category_24162091> findAll(int page, int pageSize) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            TypedQuery<Category_24162091> query = enma.createNamedQuery("Category_24162091.findAll", Category_24162091.class);
            query.setFirstResult(page * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Category_24162091> searchByName(String keyword) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT c FROM Category_24162091 c WHERE c.categoryname LIKE :keyword";
            TypedQuery<Category_24162091> query = enma.createQuery(jpql, Category_24162091.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public Category_24162091 findByCategoryname(String name) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT c FROM Category_24162091 c WHERE c.categoryname = :name";
            TypedQuery<Category_24162091> query = enma.createQuery(jpql, Category_24162091.class);
            query.setParameter("name", name);
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
            String jpql = "SELECT count(c) FROM Category_24162091 c";
            Query query = enma.createQuery(jpql);
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            enma.close();
        }
    }

    @Override
    public int countVideosByCategoryId(int categoryId) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT count(v) FROM Video_24162091 v WHERE v.category.categoryId = :categoryId";
            Query query = enma.createQuery(jpql);
            query.setParameter("categoryId", categoryId);
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            enma.close();
        }
    }
}


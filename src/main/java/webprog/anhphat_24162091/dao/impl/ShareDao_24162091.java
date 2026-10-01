package webprog.anhphat_24162091.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import webprog.anhphat_24162091.configs.JpaConfig_24162091;
import webprog.anhphat_24162091.dao.IShareDao_24162091;
import webprog.anhphat_24162091.entities.Share_24162091;

public class ShareDao_24162091 implements IShareDao_24162091 {

    @Override
    public void insert(Share_24162091 share) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(share);
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
    public void delete(int shareId) throws Exception {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Share_24162091 share = enma.find(Share_24162091.class, shareId);
            if (share != null) {
                enma.remove(share);
            } else {
                throw new Exception("Không tìm thấy Share với ID: " + shareId);
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
    public Share_24162091 findById(int shareId) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            return enma.find(Share_24162091.class, shareId);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Share_24162091> findAll() {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT s FROM Share_24162091 s";
            TypedQuery<Share_24162091> query = enma.createQuery(jpql, Share_24162091.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Share_24162091> findByUsername(String username) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT s FROM Share_24162091 s WHERE s.user.username = :username";
            TypedQuery<Share_24162091> query = enma.createQuery(jpql, Share_24162091.class);
            query.setParameter("username", username);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Share_24162091> findByVideoId(String videoId) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT s FROM Share_24162091 s WHERE s.video.videoId = :videoId";
            TypedQuery<Share_24162091> query = enma.createQuery(jpql, Share_24162091.class);
            query.setParameter("videoId", videoId);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public int count() {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT count(s) FROM Share_24162091 s";
            Query query = enma.createQuery(jpql);
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            enma.close();
        }
    }

    @Override
    public int countByVideoId(String videoId) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT count(s) FROM Share_24162091 s WHERE s.video.videoId = :videoId";
            Query query = enma.createQuery(jpql);
            query.setParameter("videoId", videoId);
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            enma.close();
        }
    }
}


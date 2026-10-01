package webprog.anhphat_24162091.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import webprog.anhphat_24162091.configs.JpaConfig_24162091;
import webprog.anhphat_24162091.dao.IFavoriteDao_24162091;
import webprog.anhphat_24162091.entities.Favorite_24162091;

public class FavoriteDao_24162091 implements IFavoriteDao_24162091 {

    @Override
    public void insert(Favorite_24162091 favorite) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(favorite);
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
    public void delete(int favoriteId) throws Exception {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Favorite_24162091 fav = enma.find(Favorite_24162091.class, favoriteId);
            if (fav != null) {
                enma.remove(fav);
            } else {
                throw new Exception("Không tìm thấy Favorite với ID: " + favoriteId);
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
    public Favorite_24162091 findById(int favoriteId) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            return enma.find(Favorite_24162091.class, favoriteId);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Favorite_24162091> findAll() {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT f FROM Favorite_24162091 f";
            TypedQuery<Favorite_24162091> query = enma.createQuery(jpql, Favorite_24162091.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Favorite_24162091> findByUsername(String username) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT f FROM Favorite_24162091 f WHERE f.user.username = :username";
            TypedQuery<Favorite_24162091> query = enma.createQuery(jpql, Favorite_24162091.class);
            query.setParameter("username", username);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Favorite_24162091> findByVideoId(String videoId) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT f FROM Favorite_24162091 f WHERE f.video.videoId = :videoId";
            TypedQuery<Favorite_24162091> query = enma.createQuery(jpql, Favorite_24162091.class);
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
            String jpql = "SELECT count(f) FROM Favorite_24162091 f";
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
            String jpql = "SELECT count(f) FROM Favorite_24162091 f WHERE f.video.videoId = :videoId";
            Query query = enma.createQuery(jpql);
            query.setParameter("videoId", videoId);
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            enma.close();
        }
    }
}


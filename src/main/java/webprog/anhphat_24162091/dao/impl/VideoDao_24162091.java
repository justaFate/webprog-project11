package webprog.anhphat_24162091.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import webprog.anhphat_24162091.configs.JpaConfig_24162091;
import webprog.anhphat_24162091.dao.IVideoDao_24162091;
import webprog.anhphat_24162091.entities.Video_24162091;

public class VideoDao_24162091 implements IVideoDao_24162091 {

    @Override
    public void insert(Video_24162091 video) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(video);
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
    public void update(Video_24162091 video) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(video);
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
    public void delete(String videoId) throws Exception {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Video_24162091 video = enma.find(Video_24162091.class, videoId);
            if (video != null) {
                enma.remove(video);
            } else {
                throw new Exception("Không tìm thấy Video với ID: " + videoId);
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
    public Video_24162091 findById(String videoId) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            return enma.find(Video_24162091.class, videoId);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Video_24162091> findAll() {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            TypedQuery<Video_24162091> query = enma.createNamedQuery("Video_24162091.findAll", Video_24162091.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Video_24162091> findAll(int page, int pageSize) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video_24162091 v ORDER BY v.videoId DESC";
            TypedQuery<Video_24162091> query = enma.createQuery(jpql, Video_24162091.class);
            int offset = (page > 0) ? (page - 1) * pageSize : 0;
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Video_24162091> findByTitle(String title) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video_24162091 v WHERE v.title LIKE :title";
            TypedQuery<Video_24162091> query = enma.createQuery(jpql, Video_24162091.class);
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Video_24162091> searchByTitle(String title, int page, int pageSize) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video_24162091 v WHERE v.title LIKE :title ORDER BY v.videoId DESC";
            TypedQuery<Video_24162091> query = enma.createQuery(jpql, Video_24162091.class);
            query.setParameter("title", "%" + title + "%");
            int offset = (page > 0) ? (page - 1) * pageSize : 0;
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Video_24162091> findByCategoryId(int categoryId) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video_24162091 v WHERE v.category.categoryId = :categoryId";
            TypedQuery<Video_24162091> query = enma.createQuery(jpql, Video_24162091.class);
            query.setParameter("categoryId", categoryId);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public int count() {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT count(v) FROM Video_24162091 v";
            Query query = enma.createQuery(jpql);
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            enma.close();
        }
    }

    @Override
    public int countSearch(String title) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT count(v) FROM Video_24162091 v WHERE v.title LIKE :title";
            Query query = enma.createQuery(jpql);
            query.setParameter("title", "%" + title + "%");
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Video_24162091> findByCategoryId(int categoryId, int page, int pageSize) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video_24162091 v WHERE v.category.categoryId = :categoryId ORDER BY v.videoId ASC";
            TypedQuery<Video_24162091> query = enma.createQuery(jpql, Video_24162091.class);
            query.setParameter("categoryId", categoryId);
            int offset = (page > 0) ? (page - 1) * pageSize : 0;
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public int countByCategoryId(int categoryId) {
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

    @Override
    public int countLikes(String videoId) {
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

    @Override
    public int countShares(String videoId) {
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

    @Override
    public void increaseViews(String videoId) {
        EntityManager enma = JpaConfig_24162091.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Video_24162091 video = enma.find(Video_24162091.class, videoId);
            if (video != null) {
                video.setViews((video.getViews() != null ? video.getViews() : 0) + 1);
                enma.merge(video);
            }
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
        } finally {
            enma.close();
        }
    }
}

package webprog.anhphat_24162091.dao;

import java.util.List;
import webprog.anhphat_24162091.entities.Share_24162091;

public interface IShareDao_24162091 {
    void insert(Share_24162091 share);
    void delete(int shareId) throws Exception;
    Share_24162091 findById(int shareId);
    List<Share_24162091> findAll();
    List<Share_24162091> findByUsername(String username);
    List<Share_24162091> findByVideoId(String videoId);
    int count();
    int countByVideoId(String videoId);
}


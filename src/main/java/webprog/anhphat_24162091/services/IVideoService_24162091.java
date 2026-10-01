package webprog.anhphat_24162091.services;

import java.util.List;
import webprog.anhphat_24162091.entities.Video_24162091;

public interface IVideoService_24162091 {
    void insert(Video_24162091 video);
    void update(Video_24162091 video);
    void delete(String videoId) throws Exception;
    Video_24162091 findById(String videoId);
    List<Video_24162091> findAll();
    List<Video_24162091> findAll(int page, int pageSize);
    List<Video_24162091> findByTitle(String title);
    List<Video_24162091> searchByTitle(String title, int page, int pageSize);
    List<Video_24162091> findByCategoryId(int categoryId);
    List<Video_24162091> findByCategoryId(int categoryId, int page, int pageSize);
    int count();
    int countSearch(String title);
    int countByCategoryId(int categoryId);
    int countLikes(String videoId);
    int countShares(String videoId);
    void increaseViews(String videoId);
}

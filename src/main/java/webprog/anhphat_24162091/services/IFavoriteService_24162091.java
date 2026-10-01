package webprog.anhphat_24162091.services;

import java.util.List;
import webprog.anhphat_24162091.entities.Favorite_24162091;

public interface IFavoriteService_24162091 {
    void insert(Favorite_24162091 favorite);
    void delete(int favoriteId) throws Exception;
    Favorite_24162091 findById(int favoriteId);
    List<Favorite_24162091> findAll();
    List<Favorite_24162091> findByUsername(String username);
    List<Favorite_24162091> findByVideoId(String videoId);
    int count();
    int countByVideoId(String videoId);
}


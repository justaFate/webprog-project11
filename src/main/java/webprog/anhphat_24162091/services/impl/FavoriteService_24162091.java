package webprog.anhphat_24162091.services.impl;

import java.util.List;
import webprog.anhphat_24162091.dao.IFavoriteDao_24162091;
import webprog.anhphat_24162091.dao.impl.FavoriteDao_24162091;
import webprog.anhphat_24162091.entities.Favorite_24162091;
import webprog.anhphat_24162091.services.IFavoriteService_24162091;

public class FavoriteService_24162091 implements IFavoriteService_24162091 {

    private final IFavoriteDao_24162091 favoriteDao = new FavoriteDao_24162091();

    @Override
    public void insert(Favorite_24162091 favorite) {
        favoriteDao.insert(favorite);
    }

    @Override
    public void delete(int favoriteId) throws Exception {
        favoriteDao.delete(favoriteId);
    }

    @Override
    public Favorite_24162091 findById(int favoriteId) {
        return favoriteDao.findById(favoriteId);
    }

    @Override
    public List<Favorite_24162091> findAll() {
        return favoriteDao.findAll();
    }

    @Override
    public List<Favorite_24162091> findByUsername(String username) {
        return favoriteDao.findByUsername(username);
    }

    @Override
    public List<Favorite_24162091> findByVideoId(String videoId) {
        return favoriteDao.findByVideoId(videoId);
    }

    @Override
    public int count() {
        return favoriteDao.count();
    }

    @Override
    public int countByVideoId(String videoId) {
        return favoriteDao.countByVideoId(videoId);
    }
}


package webprog.anhphat_24162091.services.impl;

import java.util.List;
import webprog.anhphat_24162091.dao.IVideoDao_24162091;
import webprog.anhphat_24162091.dao.impl.VideoDao_24162091;
import webprog.anhphat_24162091.entities.Video_24162091;
import webprog.anhphat_24162091.services.IVideoService_24162091;

public class VideoService_24162091 implements IVideoService_24162091 {

    private final IVideoDao_24162091 videoDao = new VideoDao_24162091();

    @Override
    public void insert(Video_24162091 video) {
        videoDao.insert(video);
    }

    @Override
    public void update(Video_24162091 video) {
        videoDao.update(video);
    }

    @Override
    public void delete(String videoId) throws Exception {
        videoDao.delete(videoId);
    }

    @Override
    public Video_24162091 findById(String videoId) {
        return videoDao.findById(videoId);
    }

    @Override
    public List<Video_24162091> findAll() {
        return videoDao.findAll();
    }

    @Override
    public List<Video_24162091> findAll(int page, int pageSize) {
        return videoDao.findAll(page, pageSize);
    }

    @Override
    public List<Video_24162091> findByTitle(String title) {
        return videoDao.findByTitle(title);
    }

    @Override
    public List<Video_24162091> searchByTitle(String title, int page, int pageSize) {
        return videoDao.searchByTitle(title, page, pageSize);
    }

    @Override
    public List<Video_24162091> findByCategoryId(int categoryId) {
        return videoDao.findByCategoryId(categoryId);
    }

    @Override
    public int count() {
        return videoDao.count();
    }

    @Override
    public int countSearch(String title) {
        return videoDao.countSearch(title);
    }

    @Override
    public List<Video_24162091> findByCategoryId(int categoryId, int page, int pageSize) {
        return videoDao.findByCategoryId(categoryId, page, pageSize);
    }

    @Override
    public int countByCategoryId(int categoryId) {
        return videoDao.countByCategoryId(categoryId);
    }

    @Override
    public int countLikes(String videoId) {
        return videoDao.countLikes(videoId);
    }

    @Override
    public int countShares(String videoId) {
        return videoDao.countShares(videoId);
    }

    @Override
    public void increaseViews(String videoId) {
        videoDao.increaseViews(videoId);
    }
}

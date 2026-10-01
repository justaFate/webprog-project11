package webprog.anhphat_24162091.services.impl;

import java.util.List;
import webprog.anhphat_24162091.dao.IShareDao_24162091;
import webprog.anhphat_24162091.dao.impl.ShareDao_24162091;
import webprog.anhphat_24162091.entities.Share_24162091;
import webprog.anhphat_24162091.services.IShareService_24162091;

public class ShareService_24162091 implements IShareService_24162091 {

    private final IShareDao_24162091 shareDao = new ShareDao_24162091();

    @Override
    public void insert(Share_24162091 share) {
        shareDao.insert(share);
    }

    @Override
    public void delete(int shareId) throws Exception {
        shareDao.delete(shareId);
    }

    @Override
    public Share_24162091 findById(int shareId) {
        return shareDao.findById(shareId);
    }

    @Override
    public List<Share_24162091> findAll() {
        return shareDao.findAll();
    }

    @Override
    public List<Share_24162091> findByUsername(String username) {
        return shareDao.findByUsername(username);
    }

    @Override
    public List<Share_24162091> findByVideoId(String videoId) {
        return shareDao.findByVideoId(videoId);
    }

    @Override
    public int count() {
        return shareDao.count();
    }

    @Override
    public int countByVideoId(String videoId) {
        return shareDao.countByVideoId(videoId);
    }
}


package webprog.anhphat_24162091.dao;

import java.util.List;
import webprog.anhphat_24162091.entities.User_24162091;

public interface IUserDao_24162091 {
    void insert(User_24162091 user);
    void update(User_24162091 user);
    void delete(String username) throws Exception;
    User_24162091 findById(String username);
    List<User_24162091> findAll();
    List<User_24162091> findAll(int page, int pageSize);
    List<User_24162091> searchByFullname(String fullname);
    User_24162091 findByEmail(String email);
    User_24162091 checkLogin(String username, String password);
    int count();
}


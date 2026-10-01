package webprog.anhphat_24162091.services.impl;

import java.util.List;
import webprog.anhphat_24162091.dao.IUserDao_24162091;
import webprog.anhphat_24162091.dao.impl.UserDao_24162091;
import webprog.anhphat_24162091.entities.User_24162091;
import webprog.anhphat_24162091.services.IUserService_24162091;

public class UserService_24162091 implements IUserService_24162091 {

    private final IUserDao_24162091 userDao = new UserDao_24162091();

    @Override
    public void insert(User_24162091 user) {
        userDao.insert(user);
    }

    @Override
    public void update(User_24162091 user) {
        userDao.update(user);
    }

    @Override
    public void delete(String username) throws Exception {
        userDao.delete(username);
    }

    @Override
    public User_24162091 findById(String username) {
        return userDao.findById(username);
    }

    @Override
    public List<User_24162091> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<User_24162091> findAll(int page, int pageSize) {
        return userDao.findAll(page, pageSize);
    }

    @Override
    public List<User_24162091> searchByFullname(String fullname) {
        return userDao.searchByFullname(fullname);
    }

    @Override
    public User_24162091 findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User_24162091 login(String username, String password) {
        User_24162091 user = this.findById(username);
        if (user != null && password != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean register(String username, String password, String email, String fullname, String phone) {
        if (checkExistUsername(username) || checkExistEmail(email)) {
            return false;
        }
        User_24162091 user = new User_24162091();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setAdmin(false);
        user.setActive(false); // Chưa kích hoạt, cần xác thực bằng OTP
        user.setImages("default.png");
        userDao.insert(user);
        return true;
    }

    @Override
    public boolean activateAccount(String username) {
        User_24162091 user = this.findById(username);
        if (user != null) {
            user.setActive(true);
            this.update(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.findByEmail(email) != null;
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.findById(username) != null;
    }

    @Override
    public int count() {
        return userDao.count();
    }
}


package webprog.anhphat_24162091.services.impl;

import java.util.List;
import webprog.anhphat_24162091.dao.ICategoryDao_24162091;
import webprog.anhphat_24162091.dao.impl.CategoryDao_24162091;
import webprog.anhphat_24162091.entities.Category_24162091;
import webprog.anhphat_24162091.services.ICategoryService_24162091;

public class CategoryService_24162091 implements ICategoryService_24162091 {

    private final ICategoryDao_24162091 categoryDao = new CategoryDao_24162091();

    @Override
    public void insert(Category_24162091 category) {
        categoryDao.insert(category);
    }

    @Override
    public void update(Category_24162091 category) {
        categoryDao.update(category);
    }

    @Override
    public void delete(int categoryId) throws Exception {
        categoryDao.delete(categoryId);
    }

    @Override
    public Category_24162091 findById(int categoryId) {
        return categoryDao.findById(categoryId);
    }

    @Override
    public List<Category_24162091> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<Category_24162091> findAll(int page, int pageSize) {
        return categoryDao.findAll(page, pageSize);
    }

    @Override
    public List<Category_24162091> searchByName(String keyword) {
        return categoryDao.searchByName(keyword);
    }

    @Override
    public Category_24162091 findByCategoryname(String name) {
        return categoryDao.findByCategoryname(name);
    }

    @Override
    public int count() {
        return categoryDao.count();
    }

    @Override
    public int countVideosByCategoryId(int categoryId) {
        return categoryDao.countVideosByCategoryId(categoryId);
    }
}


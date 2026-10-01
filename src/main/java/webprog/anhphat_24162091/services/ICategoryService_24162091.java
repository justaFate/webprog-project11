package webprog.anhphat_24162091.services;

import java.util.List;
import webprog.anhphat_24162091.entities.Category_24162091;

public interface ICategoryService_24162091 {
    void insert(Category_24162091 category);
    void update(Category_24162091 category);
    void delete(int categoryId) throws Exception;
    Category_24162091 findById(int categoryId);
    List<Category_24162091> findAll();
    List<Category_24162091> findAll(int page, int pageSize);
    List<Category_24162091> searchByName(String keyword);
    Category_24162091 findByCategoryname(String name);
    int count();
    int countVideosByCategoryId(int categoryId);
}


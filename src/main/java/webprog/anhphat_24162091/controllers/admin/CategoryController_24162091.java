package webprog.anhphat_24162091.controllers.admin;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webprog.anhphat_24162091.entities.Category_24162091;
import webprog.anhphat_24162091.services.ICategoryService_24162091;
import webprog.anhphat_24162091.services.impl.CategoryService_24162091;

@WebServlet(urlPatterns = {
    "/admin/categories",
    "/admin/category/add",
    "/admin/category/insert",
    "/admin/category/edit",
    "/admin/category/update",
    "/admin/category/delete"
})
public class CategoryController_24162091 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ICategoryService_24162091 categoryService = new CategoryService_24162091();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.contains("/admin/category/add")) {
            req.getRequestDispatcher("/WEB-INF/views/admin/category-add.jsp").forward(req, resp);
        } else if (uri.contains("/admin/category/edit")) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                Category_24162091 category = categoryService.findById(id);
                req.setAttribute("category", category);
            } catch (Exception e) {
                e.printStackTrace();
            }
            req.getRequestDispatcher("/WEB-INF/views/admin/category-edit.jsp").forward(req, resp);
        } else if (uri.contains("/admin/category/delete")) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                categoryService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        } else {
            // Danh sách Category
            List<Category_24162091> list = categoryService.findAll();
            req.setAttribute("categories", list);
            req.getRequestDispatcher("/WEB-INF/views/admin/category-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.contains("/admin/category/insert")) {
            String name = req.getParameter("categoryname");
            String code = req.getParameter("categorycode");
            String images = req.getParameter("images");
            String statusParam = req.getParameter("status");

            Category_24162091 category = new Category_24162091();
            category.setCategoryname(name);
            category.setCategorycode(code);
            category.setImages(images != null && !images.trim().isEmpty() ? images : "category_default.png");
            category.setStatus("1".equals(statusParam) || "true".equalsIgnoreCase(statusParam));

            try {
                categoryService.insert(category);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        } else if (uri.contains("/admin/category/update")) {
            try {
                int id = Integer.parseInt(req.getParameter("categoryId"));
                String name = req.getParameter("categoryname");
                String code = req.getParameter("categorycode");
                String images = req.getParameter("images");
                String statusParam = req.getParameter("status");

                Category_24162091 category = categoryService.findById(id);
                if (category != null) {
                    category.setCategoryname(name);
                    category.setCategorycode(code);
                    if (images != null && !images.trim().isEmpty()) {
                        category.setImages(images);
                    }
                    category.setStatus("1".equals(statusParam) || "true".equalsIgnoreCase(statusParam));
                    categoryService.update(category);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }
}


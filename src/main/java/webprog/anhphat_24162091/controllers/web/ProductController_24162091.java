package webprog.anhphat_24162091.controllers.web;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webprog.anhphat_24162091.entities.Category_24162091;
import webprog.anhphat_24162091.entities.Video_24162091;
import webprog.anhphat_24162091.services.ICategoryService_24162091;
import webprog.anhphat_24162091.services.IVideoService_24162091;
import webprog.anhphat_24162091.services.impl.CategoryService_24162091;
import webprog.anhphat_24162091.services.impl.VideoService_24162091;

@WebServlet(urlPatterns = {"/products", "/san-pham", "/product/detail"})
public class ProductController_24162091 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ICategoryService_24162091 categoryService = new CategoryService_24162091();
    private final IVideoService_24162091 videoService = new VideoService_24162091();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.contains("/product/detail")) {
            String id = req.getParameter("id");
            resp.sendRedirect(req.getContextPath() + "/video/detail?id=" + (id != null ? id : ""));
            return;
        }

        // Danh sách sản phẩm (Video) kèm lọc theo danh mục hoặc tìm kiếm
        String cateIdParam = req.getParameter("categoryId");
        String keyword = req.getParameter("keyword");

        List<Category_24162091> categories = categoryService.findAll();
        List<Video_24162091> videos;

        if (keyword != null && !keyword.trim().isEmpty()) {
            videos = videoService.findByTitle(keyword.trim());
            req.setAttribute("keyword", keyword);
        } else if (cateIdParam != null && !cateIdParam.trim().isEmpty()) {
            try {
                int cateId = Integer.parseInt(cateIdParam);
                videos = videoService.findByCategoryId(cateId);
                req.setAttribute("selectedCategoryId", cateId);
            } catch (NumberFormatException e) {
                videos = videoService.findAll();
            }
        } else {
            videos = videoService.findAll();
        }

        req.setAttribute("categories", categories);
        req.setAttribute("videos", videos);
        req.getRequestDispatcher("/WEB-INF/views/web/products.jsp").forward(req, resp);
    }
}


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

@WebServlet(urlPatterns = {"/home", "/trang-chu"})
public class HomeController_24162091 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // CÂU 5: Phân trang 3 video trên 01 trang
    public static final int PAGE_SIZE = 3;

    private final ICategoryService_24162091 categoryService = new CategoryService_24162091();
    private final IVideoService_24162091 videoService = new VideoService_24162091();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Lấy danh sách tất cả các Category
            List<Category_24162091> categories = categoryService.findAll();

            // CÂU 6: Đếm số lượng Video theo từng Category
            for (Category_24162091 cat : categories) {
                int count = categoryService.countVideosByCategoryId(cat.getCategoryId());
                cat.setVideoCount(count);
            }

            // Xác định Category đang được chọn
            Category_24162091 currentCategory = null;
            String categoryIdParam = req.getParameter("categoryId");
            if (categoryIdParam != null && !categoryIdParam.trim().isEmpty()) {
                try {
                    int catId = Integer.parseInt(categoryIdParam.trim());
                    for (Category_24162091 cat : categories) {
                        if (cat.getCategoryId() == catId) {
                            currentCategory = cat;
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    currentCategory = null;
                }
            }

            // Nếu không chọn hoặc không tìm thấy, mặc định chọn Category đầu tiên
            if (currentCategory == null && !categories.isEmpty()) {
                currentCategory = categories.get(0);
            }

            // Xử lý phân trang 3 video / trang cho Category được chọn (CÂU 5)
            int page = 1;
            String pageParam = req.getParameter("page");
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam.trim());
                    if (page < 1) page = 1;
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            List<Video_24162091> videos = null;
            int totalVideos = 0;
            int totalPages = 1;

            if (currentCategory != null) {
                totalVideos = currentCategory.getVideoCount();
                totalPages = (int) Math.ceil((double) totalVideos / PAGE_SIZE);
                if (totalPages == 0) totalPages = 1;
                if (page > totalPages) page = totalPages;

                videos = videoService.findByCategoryId(currentCategory.getCategoryId(), page, PAGE_SIZE);

                // Lấy số lượng Like và Share cho từng video
                for (Video_24162091 v : videos) {
                    v.setLikeCount(videoService.countLikes(v.getVideoId()));
                    v.setShareCount(videoService.countShares(v.getVideoId()));
                }
            }

            req.setAttribute("categories", categories);
            req.setAttribute("currentCategory", currentCategory);
            req.setAttribute("videos", videos);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("totalVideos", totalVideos);
            req.setAttribute("pageSize", PAGE_SIZE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/WEB-INF/views/web/home.jsp").forward(req, resp);
    }
}

package webprog.anhphat_24162091.controllers.admin;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webprog.anhphat_24162091.entities.Category_24162091;
import webprog.anhphat_24162091.entities.Video_24162091;
import webprog.anhphat_24162091.services.ICategoryService_24162091;
import webprog.anhphat_24162091.services.IVideoService_24162091;
import webprog.anhphat_24162091.services.impl.CategoryService_24162091;
import webprog.anhphat_24162091.services.impl.VideoService_24162091;

@WebServlet(urlPatterns = {
    "/admin/videos",
    "/admin/video/add",
    "/admin/video/insert",
    "/admin/video/edit",
    "/admin/video/update",
    "/admin/video/delete"
})
public class VideoController_24162091 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Số lượng video/trang
    public static final int PAGE_SIZE = 6;

    private final IVideoService_24162091 videoService = new VideoService_24162091();
    private final ICategoryService_24162091 categoryService = new CategoryService_24162091();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        // Chuyển thông báo từ session flash (nếu có) sang request
        if (session != null && session.getAttribute("message") != null) {
            req.setAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }
        if (session != null && session.getAttribute("errorMessage") != null) {
            req.setAttribute("errorMessage", session.getAttribute("errorMessage"));
            session.removeAttribute("errorMessage");
        }

        // 1. TẠO (CREATE) - Hiển thị form thêm mới video
        if (uri.contains("/admin/video/add")) {
            List<Category_24162091> categories = categoryService.findAll();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/WEB-INF/views/admin/video-add.jsp").forward(req, resp);
            return;
        }

        // 2. CẬP NHẬT (UPDATE) - Hiển thị form cập nhật video
        if (uri.contains("/admin/video/edit")) {
            String id = req.getParameter("id");
            if (id != null && !id.trim().isEmpty()) {
                Video_24162091 video = videoService.findById(id.trim());
                if (video != null) {
                    List<Category_24162091> categories = categoryService.findAll();
                    req.setAttribute("video", video);
                    req.setAttribute("categories", categories);
                    req.getRequestDispatcher("/WEB-INF/views/admin/video-edit.jsp").forward(req, resp);
                    return;
                }
            }
            if (session != null) session.setAttribute("errorMessage", "Không tìm thấy video cần chỉnh sửa!");
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
            return;
        }

        // 3. XÓA (DELETE) - Xử lý xóa video theo mã ID
        if (uri.contains("/admin/video/delete")) {
            String id = req.getParameter("id");
            if (id != null && !id.trim().isEmpty()) {
                try {
                    videoService.delete(id.trim());
                    if (session != null) session.setAttribute("message", "Đã xóa video thành công: " + id.trim());
                } catch (Exception e) {
                    e.printStackTrace();
                    if (session != null) session.setAttribute("errorMessage", "Lỗi khi xóa video: " + e.getMessage());
                }
            }
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
            return;
        }

        // 4. XEM (READ) - Danh sách video có PHÂN TRANG 6 VIDEO TRÊN 01 TRANG & TÌM KIẾM
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

        String keyword = req.getParameter("keyword");
        boolean isSearch = (keyword != null && !keyword.trim().isEmpty());

        int totalVideos = isSearch ? videoService.countSearch(keyword.trim()) : videoService.count();
        int totalPages = (int) Math.ceil((double) totalVideos / PAGE_SIZE);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;

        List<Video_24162091> videos = isSearch
                ? videoService.searchByTitle(keyword.trim(), page, PAGE_SIZE)
                : videoService.findAll(page, PAGE_SIZE);

        req.setAttribute("videos", videos);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalVideos", totalVideos);
        req.setAttribute("pageSize", PAGE_SIZE);
        req.setAttribute("keyword", isSearch ? keyword.trim() : "");

        req.getRequestDispatcher("/WEB-INF/views/admin/video-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(true);

        // XỬ LÝ LƯU THÊM MỚI (INSERT)
        if (uri.contains("/admin/video/insert")) {
            String videoId = req.getParameter("videoId");
            String title = req.getParameter("title");
            String poster = req.getParameter("poster");
            String description = req.getParameter("description");
            String activeParam = req.getParameter("active");
            String categoryIdParam = req.getParameter("categoryId");

            if (videoId == null || videoId.trim().isEmpty() || title == null || title.trim().isEmpty()) {
                session.setAttribute("errorMessage", "Mã video và tiêu đề video là bắt buộc!");
                resp.sendRedirect(req.getContextPath() + "/admin/video/add");
                return;
            }

            // Kiểm tra trùng mã VideoId
            if (videoService.findById(videoId.trim()) != null) {
                session.setAttribute("errorMessage", "Mã Video '" + videoId.trim() + "' đã tồn tại! Vui lòng chọn mã khác.");
                resp.sendRedirect(req.getContextPath() + "/admin/video/add");
                return;
            }

            Video_24162091 video = new Video_24162091();
            video.setVideoId(videoId.trim());
            video.setTitle(title.trim());
            video.setPoster(poster != null && !poster.trim().isEmpty() ? poster.trim() : "default_video.jpg");
            video.setDescription(description != null ? description.trim() : "");
            video.setViews(0);
            video.setActive("1".equals(activeParam) || "true".equalsIgnoreCase(activeParam));

            if (categoryIdParam != null && !categoryIdParam.trim().isEmpty()) {
                try {
                    int catId = Integer.parseInt(categoryIdParam.trim());
                    Category_24162091 cat = categoryService.findById(catId);
                    video.setCategory(cat);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                videoService.insert(video);
                session.setAttribute("message", "Thêm video mới thành công: " + video.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("errorMessage", "Lỗi thêm video: " + e.getMessage());
            }
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
            return;
        }

        // XỬ LÝ CẬP NHẬT (UPDATE)
        if (uri.contains("/admin/video/update")) {
            String videoId = req.getParameter("videoId");
            String title = req.getParameter("title");
            String poster = req.getParameter("poster");
            String description = req.getParameter("description");
            String activeParam = req.getParameter("active");
            String categoryIdParam = req.getParameter("categoryId");

            if (videoId != null && !videoId.trim().isEmpty()) {
                Video_24162091 video = videoService.findById(videoId.trim());
                if (video != null) {
                    video.setTitle(title != null ? title.trim() : "");
                    if (poster != null && !poster.trim().isEmpty()) {
                        video.setPoster(poster.trim());
                    }
                    video.setDescription(description != null ? description.trim() : "");
                    video.setActive("1".equals(activeParam) || "true".equalsIgnoreCase(activeParam));

                    if (categoryIdParam != null && !categoryIdParam.trim().isEmpty()) {
                        try {
                            int catId = Integer.parseInt(categoryIdParam.trim());
                            Category_24162091 cat = categoryService.findById(catId);
                            video.setCategory(cat);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        video.setCategory(null);
                    }

                    try {
                        videoService.update(video);
                        session.setAttribute("message", "Cập nhật video thành công: " + video.getVideoId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.setAttribute("errorMessage", "Lỗi cập nhật video: " + e.getMessage());
                    }
                }
            }
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
        }
    }
}

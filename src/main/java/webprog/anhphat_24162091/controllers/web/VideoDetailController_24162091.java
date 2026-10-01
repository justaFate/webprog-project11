package webprog.anhphat_24162091.controllers.web;

import java.io.IOException;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webprog.anhphat_24162091.configs.Constant_24162091;
import webprog.anhphat_24162091.entities.Favorite_24162091;
import webprog.anhphat_24162091.entities.Share_24162091;
import webprog.anhphat_24162091.entities.User_24162091;
import webprog.anhphat_24162091.entities.Video_24162091;
import webprog.anhphat_24162091.services.IFavoriteService_24162091;
import webprog.anhphat_24162091.services.IShareService_24162091;
import webprog.anhphat_24162091.services.IUserService_24162091;
import webprog.anhphat_24162091.services.IVideoService_24162091;
import webprog.anhphat_24162091.services.impl.FavoriteService_24162091;
import webprog.anhphat_24162091.services.impl.ShareService_24162091;
import webprog.anhphat_24162091.services.impl.UserService_24162091;
import webprog.anhphat_24162091.services.impl.VideoService_24162091;

@WebServlet(urlPatterns = {
    "/video/detail",
    "/video/like",
    "/video/share"
})
public class VideoDetailController_24162091 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IVideoService_24162091 videoService = new VideoService_24162091();
    private final IFavoriteService_24162091 favoriteService = new FavoriteService_24162091();
    private final IShareService_24162091 shareService = new ShareService_24162091();
    private final IUserService_24162091 userService = new UserService_24162091();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String videoId = req.getParameter("id");

        if (videoId == null || videoId.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        videoId = videoId.trim();

        // 1. Xử lý Like video
        if (uri.contains("/video/like")) {
            handleLike(req, videoId);
            resp.sendRedirect(req.getContextPath() + "/video/detail?id=" + videoId);
            return;
        }

        // 2. Xử lý Share video
        if (uri.contains("/video/share")) {
            handleShare(req, videoId);
            resp.sendRedirect(req.getContextPath() + "/video/detail?id=" + videoId);
            return;
        }

        // 3. CÂU 4: Hiển thị trang chi tiết video
        Video_24162091 video = videoService.findById(videoId);
        if (video == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        // Tăng lượt xem (View)
        videoService.increaseViews(videoId);
        video.setViews((video.getViews() != null ? video.getViews() : 0) + 1);

        // Lấy số lượt Like và Share
        video.setLikeCount(videoService.countLikes(videoId));
        video.setShareCount(videoService.countShares(videoId));

        req.setAttribute("video", video);
        req.getRequestDispatcher("/WEB-INF/views/web/video-detail.jsp").forward(req, resp);
    }

    private void handleLike(HttpServletRequest req, String videoId) {
        HttpSession session = req.getSession(true);
        User_24162091 user = (User_24162091) session.getAttribute(Constant_24162091.SESSION_ACCOUNT);
        if (user == null) {
            // Mặc định gán user demo nếu chưa login để test thuận tiện
            user = userService.findById("user");
            if (user == null) {
                user = userService.findById("admin");
            }
        }
        if (user != null) {
            Video_24162091 video = videoService.findById(videoId);
            if (video != null) {
                Favorite_24162091 fav = new Favorite_24162091(new Date(), video, user);
                favoriteService.insert(fav);
                session.setAttribute("message", "Đã thích video thành công!");
            }
        }
    }

    private void handleShare(HttpServletRequest req, String videoId) {
        HttpSession session = req.getSession(true);
        User_24162091 user = (User_24162091) session.getAttribute(Constant_24162091.SESSION_ACCOUNT);
        if (user == null) {
            user = userService.findById("user");
            if (user == null) {
                user = userService.findById("admin");
            }
        }
        if (user != null) {
            Video_24162091 video = videoService.findById(videoId);
            if (video != null) {
                String email = req.getParameter("email");
                if (email == null || email.trim().isEmpty()) {
                    email = "friend@example.com";
                }
                Share_24162091 share = new Share_24162091(email.trim(), new Date(), user, video);
                shareService.insert(share);
                session.setAttribute("message", "Đã chia sẻ video thành công!");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

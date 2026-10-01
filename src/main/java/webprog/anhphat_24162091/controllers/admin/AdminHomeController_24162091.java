package webprog.anhphat_24162091.controllers.admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webprog.anhphat_24162091.services.ICategoryService_24162091;
import webprog.anhphat_24162091.services.IFavoriteService_24162091;
import webprog.anhphat_24162091.services.IShareService_24162091;
import webprog.anhphat_24162091.services.IUserService_24162091;
import webprog.anhphat_24162091.services.IVideoService_24162091;
import webprog.anhphat_24162091.services.impl.CategoryService_24162091;
import webprog.anhphat_24162091.services.impl.FavoriteService_24162091;
import webprog.anhphat_24162091.services.impl.ShareService_24162091;
import webprog.anhphat_24162091.services.impl.UserService_24162091;
import webprog.anhphat_24162091.services.impl.VideoService_24162091;

@WebServlet(urlPatterns = {"/admin/home", "/admin"})
public class AdminHomeController_24162091 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ICategoryService_24162091 categoryService = new CategoryService_24162091();
    private final IVideoService_24162091 videoService = new VideoService_24162091();
    private final IUserService_24162091 userService = new UserService_24162091();
    private final IFavoriteService_24162091 favoriteService = new FavoriteService_24162091();
    private final IShareService_24162091 shareService = new ShareService_24162091();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int countCategory = 0;
        int countVideo = 0;
        int countUser = 0;
        int countFavorite = 0;
        int countShare = 0;

        try {
            countCategory = categoryService.count();
            countVideo = videoService.count();
            countUser = userService.count();
            countFavorite = favoriteService.count();
            countShare = shareService.count();
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("countCategory", countCategory);
        req.setAttribute("countVideo", countVideo);
        req.setAttribute("countUser", countUser);
        req.setAttribute("countFavorite", countFavorite);
        req.setAttribute("countShare", countShare);

        req.getRequestDispatcher("/WEB-INF/views/admin/home.jsp").forward(req, resp);
    }
}


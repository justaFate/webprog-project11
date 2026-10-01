package webprog.anhphat_24162091.filters;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webprog.anhphat_24162091.configs.Constant_24162091;
import webprog.anhphat_24162091.entities.User_24162091;

@WebFilter(filterName = "authFilter", urlPatterns = "/admin/*")
public class AuthFilter_24162091 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        User_24162091 user = (session != null) ? (User_24162091) session.getAttribute(Constant_24162091.SESSION_ACCOUNT) : null;

        // Chỉ cho phép admin truy cập vào các đường dẫn /admin/*
        if (user != null && Boolean.TRUE.equals(user.getAdmin())) {
            chain.doFilter(request, response);
        } else {
            // Chưa đăng nhập hoặc không phải admin -> chuyển hướng về trang đăng nhập
            resp.sendRedirect(req.getContextPath() + "/login?error=access_denied");
        }
    }

    @Override
    public void destroy() {}
}


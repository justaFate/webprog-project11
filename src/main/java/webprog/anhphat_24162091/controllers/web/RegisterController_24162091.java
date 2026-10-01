package webprog.anhphat_24162091.controllers.web;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webprog.anhphat_24162091.configs.EmailUtil_24162091;
import webprog.anhphat_24162091.services.IUserService_24162091;
import webprog.anhphat_24162091.services.impl.UserService_24162091;

@WebServlet(urlPatterns = {"/register", "/dang-ky"})
public class RegisterController_24162091 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_24162091 userService = new UserService_24162091();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        // Validate cơ bản
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            req.setAttribute("errorMessage", "Vui lòng nhập đầy đủ các trường bắt buộc (*)");
            req.setAttribute("username", username);
            req.setAttribute("fullname", fullname);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
            return;
        }

        if (!password.equals(confirmPassword)) {
            req.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
            req.setAttribute("username", username);
            req.setAttribute("fullname", fullname);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra trùng username hoặc email
        if (userService.checkExistUsername(username.trim())) {
            req.setAttribute("errorMessage", "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác!");
            req.setAttribute("fullname", fullname);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
            return;
        }

        if (userService.checkExistEmail(email.trim())) {
            req.setAttribute("errorMessage", "Email này đã được đăng ký trong hệ thống!");
            req.setAttribute("username", username);
            req.setAttribute("fullname", fullname);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
            return;
        }

        // Đăng ký tài khoản vào CSDL với active = false (chờ kích hoạt OTP)
        boolean isSuccess = userService.register(username.trim(), password.trim(), email.trim(), 
                fullname != null ? fullname.trim() : "", phone != null ? phone.trim() : "");

        if (isSuccess) {
            // Sinh mã OTP ngẫu nhiên 6 chữ số
            String otpCode = EmailUtil_24162091.generateOtp(6);
            long expiryTime = System.currentTimeMillis() + (5 * 60 * 1000); // Hết hạn sau 5 phút

            // Lưu thông tin kích hoạt OTP vào Session
            HttpSession session = req.getSession(true);
            session.setAttribute("otp_username", username.trim());
            session.setAttribute("otp_email", email.trim());
            session.setAttribute("otp_code", otpCode);
            session.setAttribute("otp_expiry", expiryTime);

            // Gửi OTP qua email (đồng thời in ra console log)
            EmailUtil_24162091.sendOtpEmail(email.trim(), otpCode);

            // Chuyển hướng sang trang nhập mã kích hoạt OTP
            resp.sendRedirect(req.getContextPath() + "/verify-otp?email=" + email.trim());
        } else {
            req.setAttribute("errorMessage", "Đăng ký không thành công do lỗi hệ thống, vui lòng thử lại!");
            req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
        }
    }
}


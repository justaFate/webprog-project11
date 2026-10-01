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

@WebServlet(urlPatterns = {"/verify-otp", "/kich-hoat", "/resend-otp"})
public class VerifyOtpController_24162091 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_24162091 userService = new UserService_24162091();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        // Xử lý gửi lại mã OTP
        if (uri.contains("/resend-otp")) {
            if (session != null && session.getAttribute("otp_username") != null) {
                String username = (String) session.getAttribute("otp_username");
                String email = (String) session.getAttribute("otp_email");

                String newOtp = EmailUtil_24162091.generateOtp(6);
                long expiry = System.currentTimeMillis() + (5 * 60 * 1000);

                session.setAttribute("otp_code", newOtp);
                session.setAttribute("otp_expiry", expiry);

                EmailUtil_24162091.sendOtpEmail(email, newOtp);

                req.setAttribute("successMessage", "Mã OTP mới đã được gửi đến email " + email);
            } else {
                req.setAttribute("errorMessage", "Phiên kích hoạt đã hết hạn, vui lòng thực hiện lại từ đầu!");
            }
            req.getRequestDispatcher("/WEB-INF/views/web/verify-otp.jsp").forward(req, resp);
            return;
        }

        // Hiển thị trang xác thực OTP
        String emailParam = req.getParameter("email");
        if (emailParam != null && !emailParam.trim().isEmpty()) {
            req.setAttribute("email", emailParam.trim());
        } else if (session != null && session.getAttribute("otp_email") != null) {
            req.setAttribute("email", session.getAttribute("otp_email"));
        }
        req.getRequestDispatcher("/WEB-INF/views/web/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String otpInput = req.getParameter("otp");
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("otp_code") == null) {
            req.setAttribute("errorMessage", "Phiên kích hoạt không tồn tại hoặc đã hết hạn, vui lòng đăng ký lại!");
            req.getRequestDispatcher("/WEB-INF/views/web/verify-otp.jsp").forward(req, resp);
            return;
        }

        String storedOtp = (String) session.getAttribute("otp_code");
        String username = (String) session.getAttribute("otp_username");
        Long expiry = (Long) session.getAttribute("otp_expiry");

        // Kiểm tra thời gian hiệu lực 5 phút
        if (expiry == null || System.currentTimeMillis() > expiry) {
            req.setAttribute("errorMessage", "Mã OTP đã hết hạn (quá 5 phút). Vui lòng nhấn 'Gửi lại mã OTP'!");
            req.getRequestDispatcher("/WEB-INF/views/web/verify-otp.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra mã OTP người dùng nhập
        if (otpInput != null && otpInput.trim().equals(storedOtp)) {
            // Kích hoạt tài khoản trong CSDL (set active = true)
            boolean activated = userService.activateAccount(username);
            if (activated) {
                // Xóa session OTP sau khi kích hoạt thành công
                session.removeAttribute("otp_code");
                session.removeAttribute("otp_username");
                session.removeAttribute("otp_email");
                session.removeAttribute("otp_expiry");

                // Lưu thông báo thành công vào session để trang login hiển thị
                session.setAttribute("successMessage", "Kích hoạt tài khoản thành công! Bạn có thể đăng nhập ngay bây giờ.");
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                req.setAttribute("errorMessage", "Kích hoạt tài khoản thất bại do không tìm thấy người dùng!");
                req.getRequestDispatcher("/WEB-INF/views/web/verify-otp.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("errorMessage", "Mã OTP không chính xác, vui lòng kiểm tra lại email hoặc log hệ thống!");
            req.getRequestDispatcher("/WEB-INF/views/web/verify-otp.jsp").forward(req, resp);
        }
    }
}


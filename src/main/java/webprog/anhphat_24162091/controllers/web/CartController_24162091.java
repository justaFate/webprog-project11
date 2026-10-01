package webprog.anhphat_24162091.controllers.web;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webprog.anhphat_24162091.configs.Constant_24162091;
import webprog.anhphat_24162091.entities.Cart_24162091;
import webprog.anhphat_24162091.entities.User_24162091;
import webprog.anhphat_24162091.entities.Video_24162091;
import webprog.anhphat_24162091.services.IVideoService_24162091;
import webprog.anhphat_24162091.services.impl.VideoService_24162091;

@WebServlet(urlPatterns = {
    "/cart",
    "/gio-hang",
    "/cart/add",
    "/cart/update",
    "/cart/increase",
    "/cart/decrease",
    "/cart/remove",
    "/cart/clear",
    "/cart/checkout"
})
public class CartController_24162091 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IVideoService_24162091 videoService = new VideoService_24162091();

    private Cart_24162091 getCart(HttpSession session) {
        Cart_24162091 cart = (Cart_24162091) session.getAttribute(Constant_24162091.SESSION_CART);
        if (cart == null) {
            cart = new Cart_24162091();
            session.setAttribute(Constant_24162091.SESSION_CART, cart);
        }
        return cart;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession(true);
        Cart_24162091 cart = getCart(session);

        if ("/cart/add".equals(path)) {
            handleAdd(req, resp, session, cart);
        } else if ("/cart/update".equals(path)) {
            handleUpdate(req, resp, session, cart);
        } else if ("/cart/increase".equals(path)) {
            handleIncrease(req, resp, session, cart);
        } else if ("/cart/decrease".equals(path)) {
            handleDecrease(req, resp, session, cart);
        } else if ("/cart/remove".equals(path)) {
            handleRemove(req, resp, session, cart);
        } else if ("/cart/clear".equals(path)) {
            handleClear(req, resp, session, cart);
        } else if ("/cart/checkout".equals(path)) {
            resp.sendRedirect(req.getContextPath() + "/cart");
        } else {
            // Hiển thị giao diện giỏ hàng
            req.setAttribute("cart", cart);
            req.getRequestDispatcher("/WEB-INF/views/web/cart.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession(true);
        Cart_24162091 cart = getCart(session);

        if ("/cart/add".equals(path)) {
            handleAdd(req, resp, session, cart);
        } else if ("/cart/update".equals(path)) {
            handleUpdate(req, resp, session, cart);
        } else if ("/cart/remove".equals(path)) {
            handleRemove(req, resp, session, cart);
        } else if ("/cart/clear".equals(path)) {
            handleClear(req, resp, session, cart);
        } else if ("/cart/checkout".equals(path)) {
            handleCheckout(req, resp, session, cart);
        } else {
            doGet(req, resp);
        }
    }

    /**
     * 1. Thêm sản phẩm vào giỏ hàng
     */
    private void handleAdd(HttpServletRequest req, HttpServletResponse resp, HttpSession session, Cart_24162091 cart)
            throws IOException {
        String videoId = req.getParameter("id");
        if (videoId == null || videoId.trim().isEmpty()) {
            videoId = req.getParameter("videoId");
        }

        String qtyParam = req.getParameter("quantity");
        int quantity = 1;
        if (qtyParam != null && !qtyParam.trim().isEmpty()) {
            try {
                quantity = Integer.parseInt(qtyParam.trim());
                if (quantity < 1) quantity = 1;
            } catch (NumberFormatException e) {
                quantity = 1;
            }
        }

        boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(req.getHeader("X-Requested-With"))
                || "true".equalsIgnoreCase(req.getParameter("ajax"));

        if (videoId != null && !videoId.trim().isEmpty()) {
            Video_24162091 video = videoService.findById(videoId.trim());
            if (video != null) {
                String message = cart.add(video, quantity);
                session.setAttribute("cartAlertMsg", message);
                session.setAttribute("cartAlertType", message.contains("tối đa") ? "warning" : "success");
                session.setAttribute("message", message);

                if (isAjax) {
                    resp.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = resp.getWriter();
                    out.print(String.format("{\"status\":\"success\",\"message\":\"%s\",\"totalQuantity\":%d,\"totalAmount\":\"%s\"}",
                            escapeJson(message), cart.getTotalQuantity(), cart.getFormattedTotalAmount()));
                    out.flush();
                    return;
                }
            } else {
                session.setAttribute("cartAlertMsg", "Không tìm thấy sản phẩm yêu cầu!");
                session.setAttribute("cartAlertType", "danger");
                session.setAttribute("message", "Không tìm thấy sản phẩm yêu cầu!");
            }
        }

        String viewCart = req.getParameter("viewCart");
        String redirect = req.getParameter("redirect");

        if ("true".equalsIgnoreCase(viewCart)) {
            resp.sendRedirect(req.getContextPath() + "/cart");
        } else if (redirect != null && !redirect.trim().isEmpty()) {
            resp.sendRedirect(redirect);
        } else {
            String referer = req.getHeader("Referer");
            if (referer != null && !referer.trim().isEmpty()) {
                resp.sendRedirect(referer);
            } else {
                resp.sendRedirect(req.getContextPath() + "/cart");
            }
        }
    }

    /**
     * 2. Sửa / Cập nhật số lượng sản phẩm trong giới hạn
     */
    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp, HttpSession session, Cart_24162091 cart)
            throws IOException {
        String videoId = req.getParameter("id");
        if (videoId == null || videoId.trim().isEmpty()) {
            videoId = req.getParameter("videoId");
        }
        String qtyParam = req.getParameter("quantity");

        if (videoId != null && !videoId.trim().isEmpty() && qtyParam != null) {
            try {
                int quantity = Integer.parseInt(qtyParam.trim());
                String message = cart.update(videoId.trim(), quantity);
                session.setAttribute("cartAlertMsg", message);
                session.setAttribute("cartAlertType", message.contains("tối đa") ? "warning" : (message.contains("xóa") ? "info" : "success"));
                session.setAttribute("message", message);
            } catch (NumberFormatException e) {
                session.setAttribute("cartAlertMsg", "Số lượng không hợp lệ! Vui lòng nhập số nguyên từ 1 đến 10.");
                session.setAttribute("cartAlertType", "danger");
                session.setAttribute("message", "Số lượng không hợp lệ!");
            }
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    /**
     * 3. Tăng số lượng (+1) trong giới hạn
     */
    private void handleIncrease(HttpServletRequest req, HttpServletResponse resp, HttpSession session, Cart_24162091 cart)
            throws IOException {
        String videoId = req.getParameter("id");
        if (videoId != null && !videoId.trim().isEmpty()) {
            String message = cart.increase(videoId.trim());
            session.setAttribute("cartAlertMsg", message);
            session.setAttribute("cartAlertType", message.contains("tối đa") ? "warning" : "success");
            session.setAttribute("message", message);
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    /**
     * 4. Giảm số lượng (-1) trong giới hạn
     */
    private void handleDecrease(HttpServletRequest req, HttpServletResponse resp, HttpSession session, Cart_24162091 cart)
            throws IOException {
        String videoId = req.getParameter("id");
        if (videoId != null && !videoId.trim().isEmpty()) {
            String message = cart.decrease(videoId.trim());
            session.setAttribute("cartAlertMsg", message);
            session.setAttribute("cartAlertType", message.contains("tối thiểu") ? "warning" : "success");
            session.setAttribute("message", message);
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    /**
     * 5. Xóa sản phẩm khỏi giỏ hàng
     */
    private void handleRemove(HttpServletRequest req, HttpServletResponse resp, HttpSession session, Cart_24162091 cart)
            throws IOException {
        String videoId = req.getParameter("id");
        if (videoId != null && !videoId.trim().isEmpty()) {
            boolean removed = cart.remove(videoId.trim());
            if (removed) {
                session.setAttribute("cartAlertMsg", "Đã xóa sản phẩm khỏi giỏ hàng thành công!");
                session.setAttribute("cartAlertType", "info");
                session.setAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng thành công!");
            }
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    /**
     * 6. Làm trống giỏ hàng
     */
    private void handleClear(HttpServletRequest req, HttpServletResponse resp, HttpSession session, Cart_24162091 cart)
            throws IOException {
        cart.clear();
        session.setAttribute("cartAlertMsg", "Đã làm trống toàn bộ giỏ hàng!");
        session.setAttribute("cartAlertType", "info");
        session.setAttribute("message", "Đã làm trống toàn bộ giỏ hàng!");
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    /**
     * 7. Đặt hàng / Thanh toán mô phỏng
     */
    private void handleCheckout(HttpServletRequest req, HttpServletResponse resp, HttpSession session, Cart_24162091 cart)
            throws IOException {
        if (cart.isEmpty()) {
            session.setAttribute("cartAlertMsg", "Giỏ hàng đang trống! Vui lòng chọn sản phẩm trước khi thanh toán.");
            session.setAttribute("cartAlertType", "warning");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String paymentMethod = req.getParameter("paymentMethod");
        String note = req.getParameter("note");

        if (fullname == null || fullname.trim().isEmpty() || phone == null || phone.trim().isEmpty()) {
            session.setAttribute("cartAlertMsg", "Vui lòng nhập đầy đủ Họ tên và Số điện thoại nhận hàng!");
            session.setAttribute("cartAlertType", "danger");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        // Tạo mã đơn hàng ngẫu nhiên mô phỏng
        String orderId = "DH" + (System.currentTimeMillis() % 1000000);
        String totalAmount = cart.getFormattedTotalAmount();
        int totalQty = cart.getTotalQuantity();

        session.setAttribute("orderSuccess", true);
        session.setAttribute("orderId", orderId);
        session.setAttribute("orderCustomer", fullname.trim());
        session.setAttribute("orderPhone", phone.trim());
        session.setAttribute("orderEmail", (email != null) ? email.trim() : "");
        session.setAttribute("orderAddress", (address != null) ? address.trim() : "Địa chỉ mặc định");
        session.setAttribute("orderPaymentMethod", (paymentMethod != null) ? paymentMethod : "COD");
        session.setAttribute("orderTotal", totalAmount);
        session.setAttribute("orderQuantity", totalQty);

        // Làm trống giỏ hàng sau khi đặt hàng thành công
        cart.clear();

        session.setAttribute("cartAlertMsg", "Đặt hàng thành công! Mã đơn hàng: " + orderId + ". Cảm ơn quý khách!");
        session.setAttribute("cartAlertType", "success");
        session.setAttribute("message", "Đặt hàng thành công!");

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"").replace("\n", " ").replace("\r", "");
    }
}

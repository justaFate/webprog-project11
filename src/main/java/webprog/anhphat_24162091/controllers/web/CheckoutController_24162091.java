package webprog.anhphat_24162091.controllers.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webprog.anhphat_24162091.configs.Constant_24162091;
import webprog.anhphat_24162091.entities.CartItem_24162091;
import webprog.anhphat_24162091.entities.Cart_24162091;
import webprog.anhphat_24162091.entities.OrderDetail_24162091;
import webprog.anhphat_24162091.entities.Order_24162091;
import webprog.anhphat_24162091.entities.User_24162091;
import webprog.anhphat_24162091.services.IOrderService_24162091;
import webprog.anhphat_24162091.services.impl.OrderService_24162091;

@WebServlet(urlPatterns = {
    "/checkout",
    "/thanh-toan",
    "/order/success",
    "/order/detail",
    "/my-orders",
    "/don-hang-cua-toi"
})
public class CheckoutController_24162091 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Ngưỡng miễn phí vận chuyển COD (300.000 VNĐ)
    public static final double FREESHIP_THRESHOLD = 300000.0;
    // Phí giao hàng COD tiêu chuẩn (30.000 VNĐ)
    public static final double STANDARD_COD_SHIPPING_FEE = 30000.0;

    // Biểu thức chính quy kiểm tra số điện thoại Việt Nam (10 chữ số)
    private static final Pattern VIETNAM_PHONE_PATTERN = Pattern.compile("^(0|\\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$");

    private final IOrderService_24162091 orderService = new OrderService_24162091();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession(true);

        if ("/order/success".equals(path) || "/order/detail".equals(path)) {
            handleOrderDetail(req, resp, session);
        } else if ("/my-orders".equals(path) || "/don-hang-cua-toi".equals(path)) {
            handleMyOrders(req, resp, session);
        } else {
            // /checkout hoặc /thanh-toan -> Hiển thị trang thanh toán COD
            showCheckoutPage(req, resp, session);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession(true);

        if ("/checkout".equals(path) || "/thanh-toan".equals(path)) {
            processCodCheckout(req, resp, session);
        } else {
            doGet(req, resp);
        }
    }

    /**
     * 1. Hiển thị trang thanh toán COD
     */
    private void showCheckoutPage(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
            throws ServletException, IOException {
        Cart_24162091 cart = (Cart_24162091) session.getAttribute(Constant_24162091.SESSION_CART);

        // Nếu giỏ hàng trống, chuyển hướng về giỏ hàng kèm cảnh báo
        if (cart == null || cart.isEmpty()) {
            session.setAttribute("cartAlertMsg", "Giỏ hàng của bạn đang trống! Vui lòng chọn sản phẩm trước khi thanh toán.");
            session.setAttribute("cartAlertType", "warning");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        double subtotal = cart.getTotalAmount();
        double shippingFee = (subtotal >= FREESHIP_THRESHOLD) ? 0.0 : STANDARD_COD_SHIPPING_FEE;
        double totalAmount = subtotal + shippingFee;

        // Điền trước thông tin nếu đã đăng nhập
        User_24162091 user = (User_24162091) session.getAttribute(Constant_24162091.SESSION_ACCOUNT);

        req.setAttribute("cart", cart);
        req.setAttribute("subtotal", subtotal);
        req.setAttribute("shippingFee", shippingFee);
        req.setAttribute("totalAmount", totalAmount);
        req.setAttribute("freeshipThreshold", FREESHIP_THRESHOLD);
        req.setAttribute("user", user);

        req.getRequestDispatcher("/WEB-INF/views/web/checkout.jsp").forward(req, resp);
    }

    /**
     * 2. Xử lý đặt hàng bằng phương thức COD (Cash On Delivery)
     */
    private void processCodCheckout(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
            throws ServletException, IOException {
        Cart_24162091 cart = (Cart_24162091) session.getAttribute(Constant_24162091.SESSION_CART);

        if (cart == null || cart.isEmpty()) {
            session.setAttribute("cartAlertMsg", "Giỏ hàng đang trống!");
            session.setAttribute("cartAlertType", "warning");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String note = req.getParameter("note");
        String paymentMethod = req.getParameter("paymentMethod");

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            paymentMethod = "COD";
        }

        // Kiểm tra hợp lệ dữ liệu
        if (fullname == null || fullname.trim().isEmpty()) {
            req.setAttribute("errorMessage", "Vui lòng nhập họ và tên người nhận hàng!");
            showCheckoutPage(req, resp, session);
            return;
        }

        if (phone == null || phone.trim().isEmpty()) {
            req.setAttribute("errorMessage", "Vui lòng nhập số điện thoại người nhận hàng!");
            showCheckoutPage(req, resp, session);
            return;
        }

        String cleanPhone = phone.trim().replaceAll("\\s+", "");
        if (!VIETNAM_PHONE_PATTERN.matcher(cleanPhone).matches()) {
            req.setAttribute("errorMessage", "Số điện thoại không hợp lệ! Vui lòng nhập số điện thoại Việt Nam hợp lệ gồm 10 số (ví dụ: 0912345678).");
            showCheckoutPage(req, resp, session);
            return;
        }

        if (address == null || address.trim().isEmpty()) {
            req.setAttribute("errorMessage", "Vui lòng cung cấp địa chỉ nhận hàng chi tiết để bưu tá giao tận nơi!");
            showCheckoutPage(req, resp, session);
            return;
        }

        // Tính toán chi phí
        double subtotal = cart.getTotalAmount();
        double shippingFee = (subtotal >= FREESHIP_THRESHOLD) ? 0.0 : STANDARD_COD_SHIPPING_FEE;
        double totalAmount = subtotal + shippingFee;

        // Sinh mã đơn hàng COD duy nhất: COD-yyMMdd-XXXX
        String datePrefix = new SimpleDateFormat("yyMMdd").format(new Date());
        int randomSuffix = 1000 + (int) (Math.random() * 9000);
        String orderId = "COD-" + datePrefix + "-" + randomSuffix;

        User_24162091 currentUser = (User_24162091) session.getAttribute(Constant_24162091.SESSION_ACCOUNT);

        Order_24162091 order = new Order_24162091();
        order.setOrderId(orderId);
        order.setOrderDate(new Date());
        order.setFullname(fullname.trim());
        order.setPhone(cleanPhone);
        order.setEmail(email != null ? email.trim() : "");
        order.setAddress(address.trim());
        order.setNote(note != null ? note.trim() : "");
        order.setPaymentMethod("COD");
        order.setShippingFee(shippingFee);
        order.setTotalAmount(totalAmount);
        order.setStatus("Chờ xác nhận (COD)");
        order.setUser(currentUser);

        // Chuyển CartItem thành OrderDetail
        List<OrderDetail_24162091> details = new ArrayList<>();
        for (CartItem_24162091 item : cart.getItemList()) {
            OrderDetail_24162091 detail = new OrderDetail_24162091(order, item.getVideo(), item.getQuantity(), item.getPrice());
            details.add(detail);
        }
        order.setOrderDetails(details);

        // Lưu đơn hàng qua OrderService
        boolean success = orderService.createOrder(order);

        if (success) {
            // Lưu vào session đơn hàng gần nhất
            session.setAttribute("lastOrder", order);

            // Cập nhật danh sách lịch sử đơn hàng trong session
            @SuppressWarnings("unchecked")
            List<Order_24162091> sessionHistory = (List<Order_24162091>) session.getAttribute("sessionOrderHistory");
            if (sessionHistory == null) {
                sessionHistory = new ArrayList<>();
            }
            sessionHistory.add(0, order);
            session.setAttribute("sessionOrderHistory", sessionHistory);

            // Làm trống giỏ hàng sau khi đặt thành công
            cart.clear();

            session.setAttribute("cartAlertMsg", "Đặt hàng COD thành công! Mã đơn: " + orderId);
            session.setAttribute("cartAlertType", "success");

            // Chuyển hướng đến trang thông báo hóa đơn thành công
            resp.sendRedirect(req.getContextPath() + "/order/success?id=" + orderId);
        } else {
            req.setAttribute("errorMessage", "Có lỗi xảy ra trong quá trình khởi tạo đơn hàng. Vui lòng thử lại!");
            showCheckoutPage(req, resp, session);
        }
    }

    /**
     * 3. Hiển thị trang kết quả đơn hàng / chi tiết hóa đơn COD
     */
    private void handleOrderDetail(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
            throws ServletException, IOException {
        String orderId = req.getParameter("id");
        Order_24162091 order = null;

        if (orderId != null && !orderId.trim().isEmpty()) {
            order = orderService.findById(orderId.trim());
        }

        if (order == null) {
            order = (Order_24162091) session.getAttribute("lastOrder");
        }

        if (order == null) {
            session.setAttribute("cartAlertMsg", "Không tìm thấy thông tin đơn hàng yêu cầu!");
            session.setAttribute("cartAlertType", "danger");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/views/web/order-success.jsp").forward(req, resp);
    }

    /**
     * 4. Xem danh sách đơn hàng đã đặt (Lịch sử đơn hàng)
     */
    private void handleMyOrders(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
            throws ServletException, IOException {
        User_24162091 user = (User_24162091) session.getAttribute(Constant_24162091.SESSION_ACCOUNT);
        List<Order_24162091> orders;

        if (user != null) {
            orders = orderService.findByUsername(user.getUsername());
        } else {
            @SuppressWarnings("unchecked")
            List<Order_24162091> sessionHistory = (List<Order_24162091>) session.getAttribute("sessionOrderHistory");
            orders = (sessionHistory != null) ? sessionHistory : new ArrayList<>();
        }

        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/web/my-orders.jsp").forward(req, resp);
    }
}

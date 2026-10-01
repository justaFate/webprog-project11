<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thanh Toán Đơn Hàng COD - Nhận Hàng Trả Tiền</title>
    <style>
        .checkout-item-img {
            width: 70px;
            height: 50px;
            object-fit: cover;
            border-radius: 6px;
        }
        .payment-option-card {
            border: 2px solid #0d6efd;
            background-color: #f8fbff;
            border-radius: 0.75rem;
            cursor: pointer;
            transition: all 0.2s ease;
        }
        .order-summary-box {
            position: sticky;
            top: 20px;
        }
    </style>
</head>
<body>
    <div class="container py-4">
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home" class="text-decoration-none">Trang Chủ</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/cart" class="text-decoration-none">Giỏ Hàng</a></li>
                <li class="breadcrumb-item active" aria-current="page">Thanh Toán Đơn Hàng (COD)</li>
            </ol>
        </nav>

        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="fw-bold text-primary mb-1">
                    <i class="fa-solid fa-truck-ramp-box me-2"></i>Thanh Toán Đơn Hàng COD
                </h2>
                <p class="text-muted mb-0">Thanh toán trực tiếp bằng tiền mặt khi nhận hàng (Cash On Delivery)</p>
            </div>
            <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-secondary">
                <i class="fa-solid fa-arrow-left me-1"></i> Quay lại giỏ hàng
            </a>
        </div>

        <!-- Cảnh báo lỗi nếu có -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show shadow-sm mb-4" role="alert">
                <i class="fa-solid fa-circle-exclamation me-2 fs-5"></i>
                <strong>${errorMessage}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/checkout" method="post" id="checkoutForm" onsubmit="return validateCheckoutForm()">
            <div class="row g-4">
                <!-- Cột trái: Thông tin nhận hàng & Phương thức thanh toán -->
                <div class="col-lg-7">
                    <!-- Khung 1: Thông tin người nhận hàng -->
                    <div class="card shadow-sm border-0 mb-4">
                        <div class="card-header bg-white py-3 fw-bold fs-5 text-dark border-bottom">
                            <i class="fa-solid fa-location-dot text-primary me-2"></i>1. Thông Tin Nhận Hàng (COD)
                        </div>
                        <div class="card-body p-4">
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label class="form-label fw-semibold">Họ và tên người nhận <span class="text-danger">*</span></label>
                                    <input type="text" name="fullname" id="fullname" class="form-control" required
                                           value="${not empty param.fullname ? param.fullname : (user != null ? user.fullname : '')}"
                                           placeholder="Nguyễn Văn A">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-semibold">Số điện thoại nhận hàng <span class="text-danger">*</span></label>
                                    <input type="tel" name="phone" id="phone" class="form-control" required
                                           value="${not empty param.phone ? param.phone : (user != null ? user.phone : '')}"
                                           placeholder="0912345678">
                                    <div class="form-text small">Shipper sẽ gọi đến số này trước khi giao hàng.</div>
                                </div>
                                <div class="col-12">
                                    <label class="form-label fw-semibold">Email nhận hóa đơn / thông báo</label>
                                    <input type="email" name="email" id="email" class="form-control"
                                           value="${not empty param.email ? param.email : (user != null ? user.email : '')}"
                                           placeholder="email@example.com">
                                </div>
                                <div class="col-12">
                                    <label class="form-label fw-semibold">Địa chỉ nhận hàng chi tiết <span class="text-danger">*</span></label>
                                    <textarea name="address" id="address" class="form-control" rows="2" required
                                              placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành phố...">${param.address}</textarea>
                                </div>
                                <div class="col-12">
                                    <label class="form-label fw-semibold">Ghi chú giao hàng cho Shipper</label>
                                    <input type="text" name="note" class="form-control"
                                           value="${param.note}"
                                           placeholder="Ví dụ: Giao giờ hành chính, gọi trước 15 phút...">
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Khung 2: Phương thức thanh toán (COD làm trung tâm) -->
                    <div class="card shadow-sm border-0 mb-4">
                        <div class="card-header bg-white py-3 fw-bold fs-5 text-dark border-bottom">
                            <i class="fa-solid fa-credit-card text-primary me-2"></i>2. Phương Thức Thanh Toán
                        </div>
                        <div class="card-body p-4">
                            <!-- Lựa chọn COD nổi bật -->
                            <div class="payment-option-card p-3 mb-3">
                                <div class="form-check d-flex align-items-center">
                                    <input class="form-check-input fs-5 me-3" type="radio" name="paymentMethod" id="paymentCOD" value="COD" checked>
                                    <label class="form-check-label w-100" for="paymentCOD">
                                        <div class="d-flex justify-content-between align-items-center mb-1">
                                            <strong class="text-primary fs-6">
                                                <i class="fa-solid fa-hand-holding-dollar me-2"></i>Thanh toán tiền mặt khi nhận hàng (COD)
                                            </strong>
                                            <span class="badge bg-success">Khuyên dùng</span>
                                        </div>
                                        <p class="text-muted small mb-0">
                                            Quý khách được kiểm tra kiện hàng cùng nhân viên giao hàng và thanh toán tiền mặt trực tiếp khi nhận hàng tận nơi.
                                        </p>
                                    </label>
                                </div>
                            </div>

                            <!-- Lợi ích khi chọn COD -->
                            <div class="alert alert-light border d-flex align-items-center gap-3 py-2 px-3 mb-0">
                                <i class="fa-solid fa-shield-check text-success fs-3"></i>
                                <div class="small text-secondary">
                                    <strong>Chính sách giao hàng an toàn:</strong> Được mở hộp đồng kiểm tra số lượng và nội dung đơn hàng trước khi thanh toán. Miễn phí thu hộ COD.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Cột phải: Tóm tắt đơn hàng & Nút Xác nhận đặt hàng COD -->
                <div class="col-lg-5">
                    <div class="card shadow-sm border-0 order-summary-box">
                        <div class="card-header bg-primary text-white py-3 d-flex justify-content-between align-items-center">
                            <span class="fw-bold fs-5"><i class="fa-solid fa-basket-shopping me-2"></i>Đơn Hàng Của Bạn</span>
                            <span class="badge bg-light text-primary rounded-pill">${cart.totalQuantity} sản phẩm</span>
                        </div>
                        <div class="card-body p-4">
                            <!-- Danh sách sản phẩm trong giỏ -->
                            <div class="list-group list-group-flush mb-3" style="max-height: 280px; overflow-y: auto;">
                                <c:forEach items="${cart.itemList}" var="item">
                                    <div class="list-group-item px-0 py-2 d-flex align-items-center">
                                        <img src="${pageContext.request.contextPath}/images/${item.video.poster}" 
                                             alt="${item.video.title}" 
                                             class="checkout-item-img me-3 shadow-sm border"
                                             onerror="this.src='https://placehold.co/100x70/222/fff?text=No+Img'">
                                        <div class="flex-grow-1 me-2">
                                            <h6 class="mb-0 text-truncate fw-bold" style="max-width: 190px;" title="${item.video.title}">
                                                ${item.video.title}
                                            </h6>
                                            <small class="text-muted">SL: <strong>x${item.quantity}</strong> &bull; Đơn giá: ${item.formattedPrice}</small>
                                        </div>
                                        <div class="text-end fw-bold text-dark">
                                            ${item.formattedTotal}
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <hr class="my-3">

                            <!-- Bảng tính chi phí -->
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Tạm tính tiền hàng:</span>
                                <span class="fw-semibold text-dark">${cart.formattedTotalAmount}</span>
                            </div>

                            <div class="d-flex justify-content-between mb-2 align-items-center">
                                <span class="text-muted">Phí giao hàng COD:</span>
                                <span>
                                    <c:choose>
                                        <c:when test="${shippingFee == 0}">
                                            <span class="badge bg-success-subtle text-success border border-success fw-bold">Miễn phí giao hàng</span>
                                        </c:when>
                                        <c:otherwise>
                                            <strong class="text-dark">30.000 ₫</strong>
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>

                            <c:if test="${subtotal < freeshipThreshold}">
                                <div class="alert alert-info py-2 px-3 small mb-3">
                                    <i class="fa-solid fa-gift me-1"></i> Mua thêm <strong><fmt:formatNumber value="${freeshipThreshold - subtotal}" pattern="###,### ₫"/></strong> để được <strong>Miễn phí giao hàng COD</strong>!
                                </div>
                            </c:if>

                            <hr class="my-3">

                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <span class="fs-5 fw-bold text-dark">Tổng thanh toán COD:</span>
                                <span class="fs-3 fw-bold text-danger">
                                    <fmt:formatNumber value="${totalAmount}" pattern="###,###,### ₫"/>
                                </span>
                            </div>

                            <!-- Nút Xác nhận đặt hàng COD -->
                            <button type="submit" id="btnSubmitOrder" class="btn btn-success btn-lg w-100 shadow fw-bold py-3 mb-3">
                                <i class="fa-solid fa-truck-fast me-2"></i> Xác Nhận Đặt Hàng COD
                            </button>

                            <p class="text-center text-muted small mb-0">
                                <i class="fa-solid fa-lock me-1 text-success"></i> Nhấn xác nhận đồng nghĩa với việc bạn đồng ý với điều khoản giao nhận hàng COD.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <script>
        function validateCheckoutForm() {
            var phone = document.getElementById('phone').value.trim().replace(/\s+/g, '');
            // Kiểm tra số điện thoại Việt Nam gồm 10 chữ số
            var phoneRegex = /^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$/;
            if (!phoneRegex.test(phone)) {
                alert('Số điện thoại không hợp lệ! Vui lòng nhập số điện thoại Việt Nam gồm 10 chữ số (ví dụ: 0912345678).');
                document.getElementById('phone').focus();
                return false;
            }

            var address = document.getElementById('address').value.trim();
            if (address.length < 5) {
                alert('Vui lòng nhập địa chỉ nhận hàng chi tiết để shipper có thể giao đến bạn!');
                document.getElementById('address').focus();
                return false;
            }

            // Tránh bấm liên tục nhiều lần (prevent double submit)
            var btn = document.getElementById('btnSubmitOrder');
            btn.disabled = true;
            btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i> Đang xử lý đơn hàng COD...';
            return true;
        }
    </script>
</body>
</html>

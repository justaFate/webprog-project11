<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ Hàng Của Bạn</title>
    <style>
        .cart-img {
            width: 90px;
            height: 65px;
            object-fit: cover;
            border-radius: 6px;
        }
        .qty-input-group {
            width: 130px;
        }
        .qty-input-group input {
            text-align: center;
            font-weight: 600;
        }
        .order-summary-card {
            border-radius: 0.75rem;
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
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/products" class="text-decoration-none">Sản Phẩm</a></li>
                <li class="breadcrumb-item active" aria-current="page">Giỏ Hàng</li>
            </ol>
        </nav>

        <!-- Tiêu đề trang -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="fw-bold text-primary mb-1">
                    <i class="fa-solid fa-cart-shopping me-2"></i>Giỏ Hàng Của Bạn
                </h2>
                <p class="text-muted mb-0">Quản lý các sản phẩm, khóa học video đã chọn và tiến hành đặt hàng</p>
            </div>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary">
                <i class="fa-solid fa-arrow-left me-1"></i> Tiếp tục chọn sản phẩm
            </a>
        </div>

        <!-- Thông báo thao tác (Alert Messages) -->
        <c:if test="${not empty sessionScope.cartAlertMsg}">
            <div class="alert alert-${not empty sessionScope.cartAlertType ? sessionScope.cartAlertType : 'info'} alert-dismissible fade show shadow-sm" role="alert">
                <c:choose>
                    <c:when test="${sessionScope.cartAlertType == 'warning'}">
                        <i class="fa-solid fa-triangle-exclamation me-2 fs-5"></i>
                    </c:when>
                    <c:when test="${sessionScope.cartAlertType == 'success'}">
                        <i class="fa-solid fa-circle-check me-2 fs-5"></i>
                    </c:when>
                    <c:when test="${sessionScope.cartAlertType == 'danger'}">
                        <i class="fa-solid fa-circle-xmark me-2 fs-5"></i>
                    </c:when>
                    <c:otherwise>
                        <i class="fa-solid fa-circle-info me-2 fs-5"></i>
                    </c:otherwise>
                </c:choose>
                <strong>${sessionScope.cartAlertMsg}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <c:remove var="cartAlertMsg" scope="session" />
            <c:remove var="cartAlertType" scope="session" />
            <c:remove var="message" scope="session" />
        </c:if>

        <!-- Thông báo Đặt hàng thành công nếu có -->
        <c:if test="${sessionScope.orderSuccess}">
            <div class="card border-success shadow-sm mb-4">
                <div class="card-header bg-success text-white fw-bold">
                    <i class="fa-solid fa-circle-check me-2"></i> ĐẶT HÀNG THÀNH CÔNG!
                </div>
                <div class="card-body">
                    <h5 class="card-title text-success fw-bold">Cảm ơn bạn đã đặt mua sản phẩm!</h5>
                    <p class="card-text mb-2">Đơn hàng của bạn đã được ghi nhận trên hệ thống với thông tin sau:</p>
                    <ul class="list-unstyled mb-3 ms-2">
                        <li><strong>Mã đơn hàng:</strong> <span class="badge bg-secondary font-monospace">${sessionScope.orderId}</span></li>
                        <li><strong>Khách hàng:</strong> ${sessionScope.orderCustomer}</li>
                        <li><strong>Số điện thoại:</strong> ${sessionScope.orderPhone}</li>
                        <li><strong>Địa chỉ:</strong> ${sessionScope.orderAddress}</li>
                        <li><strong>Hình thức thanh toán:</strong> ${sessionScope.orderPaymentMethod}</li>
                        <li><strong>Số lượng:</strong> ${sessionScope.orderQuantity} sản phẩm</li>
                        <li><strong>Tổng thanh toán:</strong> <span class="text-danger fw-bold fs-5">${sessionScope.orderTotal}</span></li>
                    </ul>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-success">
                        <i class="fa-solid fa-bag-shopping me-1"></i> Tiếp tục mua sắm
                    </a>
                </div>
            </div>
            <c:remove var="orderSuccess" scope="session" />
            <c:remove var="orderId" scope="session" />
            <c:remove var="orderCustomer" scope="session" />
            <c:remove var="orderPhone" scope="session" />
            <c:remove var="orderEmail" scope="session" />
            <c:remove var="orderAddress" scope="session" />
            <c:remove var="orderPaymentMethod" scope="session" />
            <c:remove var="orderTotal" scope="session" />
            <c:remove var="orderQuantity" scope="session" />
        </c:if>

        <c:choose>
            <%-- Trường hợp 1: Giỏ hàng trống --%>
            <c:when test="${empty cart or cart.empty}">
                <div class="card shadow-sm border-0 py-5 text-center my-4">
                    <div class="card-body">
                        <div class="mb-3 text-muted">
                            <i class="fa-solid fa-cart-plus" style="font-size: 5rem; opacity: 0.35;"></i>
                        </div>
                        <h4 class="fw-bold text-secondary mb-2">Giỏ hàng của bạn đang trống!</h4>
                        <p class="text-muted mb-4">Bạn chưa chọn bất kỳ sản phẩm nào. Hãy khám phá danh sách khóa học &amp; video ngay nhé.</p>
                        <a href="${pageContext.request.contextPath}/products" class="btn btn-primary btn-lg px-4 shadow">
                            <i class="fa-solid fa-magnifying-glass me-2"></i> Khám Phá Sản Phẩm Ngay
                        </a>
                    </div>
                </div>
            </c:when>

            <%-- Trường hợp 2: Giỏ hàng có sản phẩm --%>
            <c:otherwise>
                <div class="row g-4">
                    <!-- Cột trái: Bảng danh sách sản phẩm -->
                    <div class="col-lg-8">
                        <div class="card shadow-sm border-0">
                            <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
                                <span class="fw-bold text-dark fs-5">
                                    <i class="fa-solid fa-boxes-stacked text-primary me-2"></i>Danh Sách Mặt Hàng
                                </span>
                                <span class="badge bg-primary rounded-pill px-3 py-2">
                                    ${cart.totalQuantity} sản phẩm (${cart.itemCount} loại)
                                </span>
                            </div>

                            <div class="table-responsive">
                                <table class="table table-hover align-middle mb-0">
                                    <thead class="table-light text-secondary">
                                        <tr>
                                            <th scope="col" style="min-width: 240px;">Sản phẩm</th>
                                            <th scope="col" class="text-center" style="width: 120px;">Đơn giá</th>
                                            <th scope="col" class="text-center" style="width: 180px;">Số lượng (1-10)</th>
                                            <th scope="col" class="text-end" style="width: 130px;">Thành tiền</th>
                                            <th scope="col" class="text-center" style="width: 60px;">Xóa</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${cart.itemList}" var="item">
                                            <tr>
                                                <!-- Cột sản phẩm: ảnh + tên -->
                                                <td>
                                                    <div class="d-flex align-items-center">
                                                        <a href="${pageContext.request.contextPath}/video/detail?id=${item.video.videoId}">
                                                            <img src="${pageContext.request.contextPath}/images/${item.video.poster}" 
                                                                 alt="${item.video.title}" 
                                                                 class="cart-img me-3 shadow-sm border"
                                                                 onerror="this.src='https://placehold.co/120x80/222/fff?text=No+Poster'">
                                                        </a>
                                                        <div>
                                                            <a href="${pageContext.request.contextPath}/video/detail?id=${item.video.videoId}" 
                                                               class="text-decoration-none fw-bold text-dark d-block text-truncate" 
                                                               style="max-width: 220px;" 
                                                               title="${item.video.title}">
                                                                ${item.video.title}
                                                            </a>
                                                            <div class="small text-muted mt-1">
                                                                <span class="badge bg-secondary font-monospace me-1">${item.video.videoId}</span>
                                                                <span class="badge bg-info text-dark">
                                                                    ${item.video.category != null ? item.video.category.categoryname : 'Khóa học'}
                                                                </span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </td>

                                                <!-- Cột Đơn giá -->
                                                <td class="text-center fw-semibold text-secondary">
                                                    ${item.formattedPrice}
                                                </td>

                                                <!-- Cột Số lượng có kiểm tra giới hạn (1 - 10) -->
                                                <td class="text-center">
                                                    <div class="d-inline-flex flex-column align-items-center">
                                                        <form action="${pageContext.request.contextPath}/cart/update" method="post" class="d-flex align-items-center">
                                                            <input type="hidden" name="id" value="${item.video.videoId}">
                                                            <div class="input-group input-group-sm qty-input-group">
                                                                <!-- Nút giảm (-) -->
                                                                <a href="${pageContext.request.contextPath}/cart/decrease?id=${item.video.videoId}" 
                                                                   class="btn btn-outline-secondary" 
                                                                   title="Giảm 1 số lượng">
                                                                    <i class="fa-solid fa-minus"></i>
                                                                </a>

                                                                <!-- Ô nhập số lượng trực tiếp (giới hạn 1 đến 10) -->
                                                                <input type="number" 
                                                                       name="quantity" 
                                                                       value="${item.quantity}" 
                                                                       min="1" 
                                                                       max="10" 
                                                                       class="form-control form-control-sm text-center fw-bold"
                                                                       onchange="validateAndSubmitQty(this)"
                                                                       title="Nhập số lượng từ 1 đến 10">

                                                                <!-- Nút tăng (+) -->
                                                                <a href="${pageContext.request.contextPath}/cart/increase?id=${item.video.videoId}" 
                                                                   class="btn btn-outline-secondary" 
                                                                   title="Tăng 1 số lượng">
                                                                    <i class="fa-solid fa-plus"></i>
                                                                </a>
                                                            </div>
                                                            <button type="submit" class="btn btn-sm btn-outline-primary ms-1" title="Lưu số lượng">
                                                                <i class="fa-solid fa-check"></i>
                                                            </button>
                                                        </form>
                                                        <small class="text-muted mt-1" style="font-size: 0.75rem;">
                                                            (Giới hạn: 1 - 10)
                                                        </small>
                                                    </div>
                                                </td>

                                                <!-- Cột Thành tiền -->
                                                <td class="text-end fw-bold text-danger">
                                                    ${item.formattedTotal}
                                                </td>

                                                <!-- Cột Nút Xóa -->
                                                <td class="text-center">
                                                    <button type="button" 
                                                            class="btn btn-outline-danger btn-sm rounded-circle" 
                                                            title="Xóa khỏi giỏ hàng"
                                                            onclick="confirmDelete('${item.video.videoId}', '${item.video.title}')">
                                                        <i class="fa-solid fa-trash-can"></i>
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Footer của bảng giỏ hàng: các nút hành động -->
                            <div class="card-footer bg-white py-3 d-flex flex-wrap justify-content-between align-items-center gap-2">
                                <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-secondary">
                                    <i class="fa-solid fa-arrow-left me-1"></i> Tiếp tục mua sắm
                                </a>
                                <button type="button" class="btn btn-outline-danger" onclick="confirmClearCart()">
                                    <i class="fa-solid fa-trash-arrow-up me-1"></i> Xóa toàn bộ giỏ hàng
                                </button>
                            </div>
                        </div>
                    </div>

                    <!-- Cột phải: Tóm tắt đơn hàng & Thanh toán -->
                    <div class="col-lg-4">
                        <div class="card shadow-sm border-0 order-summary-card">
                            <div class="card-header bg-primary text-white py-3 fw-bold fs-5">
                                <i class="fa-solid fa-receipt me-2"></i>Tóm Tắt Đơn Hàng
                            </div>
                            <div class="card-body p-4">
                                <div class="d-flex justify-content-between mb-2">
                                    <span class="text-muted">Tổng số lượng:</span>
                                    <span class="fw-bold">${cart.totalQuantity} sản phẩm</span>
                                </div>
                                <div class="d-flex justify-content-between mb-2">
                                    <span class="text-muted">Số loại sản phẩm:</span>
                                    <span class="fw-semibold">${cart.itemCount} loại</span>
                                </div>
                                <div class="d-flex justify-content-between mb-2">
                                    <span class="text-muted">Tạm tính:</span>
                                    <span class="fw-bold text-dark">${cart.formattedTotalAmount}</span>
                                </div>
                                <div class="d-flex justify-content-between mb-2">
                                    <span class="text-muted">Phí học / vận chuyển:</span>
                                    <span class="text-success fw-semibold">Miễn phí (Học online)</span>
                                </div>
                                <div class="d-flex justify-content-between mb-3">
                                    <span class="text-muted">Giảm giá ưu đãi:</span>
                                    <span class="text-muted">0 ₫</span>
                                </div>

                                <hr class="my-3">

                                <div class="d-flex justify-content-between align-items-center mb-4">
                                    <span class="fs-5 fw-bold text-dark">Tổng cộng:</span>
                                    <span class="fs-4 fw-bold text-danger">${cart.formattedTotalAmount}</span>
                                </div>

                                <!-- Nút Thanh Toán COD -->
                                <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success w-100 btn-lg shadow fw-bold mb-2 py-3">
                                    <i class="fa-solid fa-truck-fast me-2"></i> Thanh Toán Khi Nhận Hàng (COD)
                                </a>
                                <button type="button" class="btn btn-outline-primary w-100 mb-3 fw-semibold" data-bs-toggle="modal" data-bs-target="#checkoutModal">
                                    <i class="fa-solid fa-bolt me-1"></i> Đặt hàng nhanh tại đây
                                </button>

                                <!-- Các cam kết bảo hành / hỗ trợ -->
                                <div class="bg-light rounded p-3 small text-muted">
                                    <div class="d-flex align-items-center mb-2">
                                        <i class="fa-solid fa-shield-halved text-success me-2 fs-6"></i>
                                        <span>Bảo mật thanh toán 100% qua SSL</span>
                                    </div>
                                    <div class="d-flex align-items-center mb-2">
                                        <i class="fa-solid fa-bolt text-warning me-2 fs-6"></i>
                                        <span>Kích hoạt quyền xem video tức thì</span>
                                    </div>
                                    <div class="d-flex align-items-center">
                                        <i class="fa-solid fa-headset text-primary me-2 fs-6"></i>
                                        <span>Hỗ trợ giải đáp chuyên môn 24/7</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- MODAL XÁC NHẬN XÓA 1 SẢN PHẨM -->
    <div class="modal fade" id="deleteItemModal" tabindex="-1" aria-labelledby="deleteItemModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title" id="deleteItemModalLabel">
                        <i class="fa-solid fa-triangle-exclamation me-2"></i> Xác Nhận Xóa Sản Phẩm
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body py-4">
                    <p class="mb-1">Bạn có chắc chắn muốn xóa sản phẩm sau khỏi giỏ hàng?</p>
                    <p class="fw-bold text-danger fs-6 mb-0" id="deleteItemTitle"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <a id="btnConfirmDelete" href="#" class="btn btn-danger">
                        <i class="fa-solid fa-trash me-1"></i> Đồng Ý Xóa
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!-- MODAL XÁC NHẬN XÓA TOÀN BỘ GIỎ HÀNG -->
    <div class="modal fade" id="clearCartModal" tabindex="-1" aria-labelledby="clearCartModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-warning text-dark">
                    <h5 class="modal-title fw-bold" id="clearCartModalLabel">
                        <i class="fa-solid fa-trash-can-arrow-up me-2"></i> Làm Trống Giỏ Hàng
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body py-4">
                    <p class="mb-0 fs-6">Bạn có chắc chắn muốn xóa <strong>toàn bộ sản phẩm</strong> trong giỏ hàng không?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <a href="${pageContext.request.contextPath}/cart/clear" class="btn btn-danger">
                        <i class="fa-solid fa-trash me-1"></i> Xóa Hết
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!-- MODAL THANH TOÁN / ĐẶT HÀNG -->
    <div class="modal fade" id="checkoutModal" tabindex="-1" aria-labelledby="checkoutModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/checkout" method="post" onsubmit="return validateQuickModal()">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title fw-bold" id="checkoutModalLabel">
                            <i class="fa-solid fa-truck-fast me-2"></i> Đặt Hàng Thanh Toán Khi Nhận Hàng (COD)
                        </h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body p-4">
                        <div class="alert alert-info py-2 mb-3">
                            <i class="fa-solid fa-circle-info me-1"></i> Tổng số lượng: <strong>${cart.totalQuantity} sản phẩm</strong> - Tiền hàng: <strong class="text-danger">${cart.formattedTotalAmount}</strong>
                        </div>
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Họ và tên người nhận <span class="text-danger">*</span></label>
                                <input type="text" name="fullname" id="quickFullname" class="form-control" required 
                                       value="${sessionScope.account != null ? sessionScope.account.fullname : ''}" 
                                       placeholder="Nhập họ và tên...">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Số điện thoại <span class="text-danger">*</span></label>
                                <input type="tel" name="phone" id="quickPhone" class="form-control" required 
                                       value="${sessionScope.account != null ? sessionScope.account.phone : ''}" 
                                       placeholder="Ví dụ: 0912345678">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Email</label>
                                <input type="email" name="email" class="form-control" 
                                       value="${sessionScope.account != null ? sessionScope.account.email : ''}" 
                                       placeholder="Nhập địa chỉ email...">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-semibold">Phương thức thanh toán</label>
                                <select name="paymentMethod" class="form-select">
                                    <option value="COD" selected>Thanh toán khi nhận hàng (COD - Tiền mặt)</option>
                                    <option value="Chuyển khoản QR Ngân hàng">Chuyển khoản QR Ngân hàng</option>
                                    <option value="Ví điện tử MoMo / ZaloPay">Ví điện tử MoMo / ZaloPay</option>
                                </select>
                            </div>
                            <div class="col-12">
                                <label class="form-label fw-semibold">Địa chỉ nhận hàng / Thông tin kích hoạt</label>
                                <input type="text" name="address" class="form-control" 
                                       placeholder="Nhập địa chỉ của bạn...">
                            </div>
                            <div class="col-12">
                                <label class="form-label fw-semibold">Ghi chú đơn hàng</label>
                                <textarea name="note" class="form-control" rows="2" placeholder="Ghi chú thêm về đơn hàng nếu có..."></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        <button type="submit" class="btn btn-success fw-bold px-4">
                            <i class="fa-solid fa-check me-1"></i> Xác Nhận Đặt Hàng Ngay
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- SCRIPT XỬ LÝ GIỚI HẠN SỐ LƯỢNG & CONFIRM -->
    <script>
        function confirmDelete(id, title) {
            document.getElementById('deleteItemTitle').textContent = title + ' (' + id + ')';
            document.getElementById('btnConfirmDelete').href = '${pageContext.request.contextPath}/cart/remove?id=' + encodeURIComponent(id);
            var modal = new bootstrap.Modal(document.getElementById('deleteItemModal'));
            modal.show();
        }

        function confirmClearCart() {
            var modal = new bootstrap.Modal(document.getElementById('clearCartModal'));
            modal.show();
        }

        function validateAndSubmitQty(input) {
            var val = parseInt(input.value, 10);
            var min = 1;
            var max = 10;

            if (isNaN(val) || val < min) {
                alert('Số lượng tối thiểu là ' + min + '. Nếu muốn xóa sản phẩm, vui lòng chọn nút Xóa!');
                input.value = min;
            } else if (val > max) {
                alert('Số lượng tối đa cho mỗi sản phẩm là ' + max + '. Hệ thống đã tự động điều chỉnh về ' + max + '!');
                input.value = max;
            }
            // Tự động submit form cập nhật
            input.form.submit();
        }

        function validateQuickModal() {
            var phone = document.getElementById('quickPhone').value.trim().replace(/\s+/g, '');
            var phoneRegex = /^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$/;
            if (!phoneRegex.test(phone)) {
                alert('Số điện thoại không hợp lệ! Vui lòng nhập số điện thoại Việt Nam gồm 10 số (ví dụ: 0912345678).');
                document.getElementById('quickPhone').focus();
                return false;
            }
            return true;
        }
    </script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng Ký Tài Khoản</title>
</head>
<body>
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-7 col-lg-6">
                <div class="card shadow border-0 p-4">
                    <div class="text-center mb-4">
                        <div class="rounded-circle bg-success text-white d-inline-flex align-items-center justify-content-center mb-3" style="width: 64px; height: 64px;">
                            <i class="fa-solid fa-user-plus fs-3"></i>
                        </div>
                        <h3 class="fw-bold">ĐĂNG KÝ TÀI KHOẢN</h3>
                        <p class="text-muted small">Tạo tài khoản mới và kích hoạt bằng mã xác thực OTP</p>
                    </div>

                    <!-- Thông báo lỗi nếu có -->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger py-2 small" role="alert">
                            <i class="fa-solid fa-triangle-exclamation me-1"></i> ${errorMessage}
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/register" method="post">
                        <div class="mb-3">
                            <label for="username" class="form-label fw-semibold">Tên đăng nhập <span class="text-danger">*</span></label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fa-solid fa-user"></i></span>
                                <input type="text" class="form-control" id="username" name="username" value="${username}" placeholder="Nhập tên tài khoản" required autofocus>
                            </div>
                        </div>

                        <div class="row g-2 mb-3">
                            <div class="col-md-6">
                                <label for="password" class="form-label fw-semibold">Mật khẩu <span class="text-danger">*</span></label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="fa-solid fa-lock"></i></span>
                                    <input type="password" class="form-control" id="password" name="password" placeholder="Nhập mật khẩu" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label for="confirmPassword" class="form-label fw-semibold">Xác nhận mật khẩu <span class="text-danger">*</span></label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="fa-solid fa-shield-halved"></i></span>
                                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu" required>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label fw-semibold">Địa chỉ Email <span class="text-danger">*</span></label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fa-solid fa-envelope"></i></span>
                                <input type="email" class="form-control" id="email" name="email" value="${email}" placeholder="example@domain.com" required>
                            </div>
                            <div class="form-text small text-primary"><i class="fa-solid fa-info-circle me-1"></i>Mã OTP kích hoạt sẽ được gửi tới địa chỉ email này.</div>
                        </div>

                        <div class="mb-3">
                            <label for="fullname" class="form-label fw-semibold">Họ và tên</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fa-solid fa-id-card"></i></span>
                                <input type="text" class="form-control" id="fullname" name="fullname" value="${fullname}" placeholder="Nhập họ và tên đầy đủ">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="phone" class="form-label fw-semibold">Số điện thoại</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fa-solid fa-phone"></i></span>
                                <input type="tel" class="form-control" id="phone" name="phone" value="${phone}" placeholder="Ví dụ: 0912345678">
                            </div>
                        </div>

                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-success btn-lg fw-bold">
                                <i class="fa-solid fa-paper-plane me-2"></i> Đăng Ký &amp; Nhận Mã OTP
                            </button>
                        </div>
                    </form>

                    <div class="mt-4 pt-3 border-top text-center">
                        <span class="text-muted small">Đã có tài khoản?</span>
                        <a href="${pageContext.request.contextPath}/login" class="fw-bold text-primary text-decoration-none ms-1">
                            Đăng nhập tại đây
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>


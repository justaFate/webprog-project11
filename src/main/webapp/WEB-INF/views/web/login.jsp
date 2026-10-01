<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng Nhập Hệ Thống</title>
</head>
<body>
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-5">
                <div class="card shadow border-0 p-4">
                    <div class="text-center mb-4">
                        <div class="rounded-circle bg-primary text-white d-inline-flex align-items-center justify-content-center mb-3" style="width: 64px; height: 64px;">
                            <i class="fa-solid fa-lock fs-3"></i>
                        </div>
                        <h3 class="fw-bold">ĐĂNG NHẬP HỆ THỐNG</h3>
                        <p class="text-muted small">Quản lý phiên đăng nhập và phân quyền Session</p>
                    </div>

                    <!-- Thông báo thành công nếu có (Ví dụ: Đã kích hoạt OTP hoặc Đã đăng xuất) -->
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success py-2 small" role="alert">
                            <i class="fa-solid fa-circle-check me-1"></i> ${successMessage}
                        </div>
                    </c:if>

                    <!-- Thông báo lỗi nếu có (Ví dụ: Sai tài khoản, Chưa kích hoạt, hoặc Không phải vai trò Admin) -->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger py-2 small" role="alert">
                            <i class="fa-solid fa-triangle-exclamation me-1"></i> ${errorMessage}
                        </div>
                    </c:if>

                    <c:if test="${param.error == 'access_denied'}">
                        <div class="alert alert-warning py-2 small" role="alert">
                            <i class="fa-solid fa-ban me-1"></i> Bạn cần quyền Admin để truy cập trang quản trị!
                        </div>
                    </c:if>

                    <!-- Form Đăng nhập -->
                    <form action="${pageContext.request.contextPath}/login" method="post">
                        <div class="mb-3">
                            <label for="username" class="form-label fw-semibold">Tên đăng nhập <span class="text-danger">*</span></label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fa-solid fa-user"></i></span>
                                <input type="text" class="form-control" id="username" name="username" value="${username}" placeholder="Nhập tên đăng nhập" required autofocus>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label fw-semibold">Mật khẩu <span class="text-danger">*</span></label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fa-solid fa-key"></i></span>
                                <input type="password" class="form-control" id="password" name="password" placeholder="Nhập mật khẩu" required>
                            </div>
                        </div>

                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-primary btn-lg fw-bold">
                                <i class="fa-solid fa-right-to-bracket me-2"></i> Đăng Nhập
                            </button>
                        </div>
                    </form>

                    <!-- Liên kết tới trang Đăng ký -->
                    <div class="mt-4 pt-3 border-top text-center">
                        <span class="text-muted small">Chưa có tài khoản?</span>
                        <a href="${pageContext.request.contextPath}/register" class="fw-bold text-success text-decoration-none ms-1">
                            <i class="fa-solid fa-user-plus me-1"></i> Đăng ký ngay
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

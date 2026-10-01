<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Xác Thực Kích Hoạt OTP</title>
</head>
<body>
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-5">
                <div class="card shadow border-0 p-4">
                    <div class="text-center mb-4">
                        <div class="rounded-circle bg-warning-subtle text-warning d-inline-flex align-items-center justify-content-center mb-3" style="width: 64px; height: 64px;">
                            <i class="fa-solid fa-key fs-3"></i>
                        </div>
                        <h3 class="fw-bold">XÁC THỰC MÃ OTP</h3>
                        <p class="text-muted small">
                            Mã OTP 6 số đã được gửi tới email:<br>
                            <strong class="text-primary fs-6">${email}</strong>
                        </p>
                    </div>

                    <!-- Thông báo lỗi nếu có -->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger py-2 small" role="alert">
                            <i class="fa-solid fa-triangle-exclamation me-1"></i> ${errorMessage}
                        </div>
                    </c:if>

                    <!-- Thông báo thành công nếu có -->
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success py-2 small" role="alert">
                            <i class="fa-solid fa-circle-check me-1"></i> ${successMessage}
                        </div>
                    </c:if>

                    <!-- Form nhập mã OTP -->
                    <form action="${pageContext.request.contextPath}/verify-otp" method="post">
                        <div class="mb-4">
                            <label for="otp" class="form-label fw-semibold text-center d-block">Nhập Mã OTP (6 chữ số)</label>
                            <input type="text" class="form-control form-control-lg text-center fw-bold fs-4" 
                                   id="otp" name="otp" maxlength="6" pattern="[0-9]{6}" 
                                   placeholder="000000" style="letter-spacing: 8px;" required autofocus>
                            <div class="form-text text-center mt-2 small">Mã có hiệu lực trong vòng 5 phút.</div>
                        </div>

                        <div class="d-grid mb-3">
                            <button type="submit" class="btn btn-primary btn-lg fw-bold">
                                <i class="fa-solid fa-circle-check me-2"></i> Xác Nhận Kích Hoạt
                            </button>
                        </div>
                    </form>

                    <div class="text-center mt-2">
                        <span class="text-muted small">Chưa nhận được mã?</span>
                        <a href="${pageContext.request.contextPath}/resend-otp" class="fw-bold text-decoration-none ms-1">
                            <i class="fa-solid fa-rotate-right me-1"></i> Gửi lại mã OTP
                        </a>
                    </div>

                    <!-- Kiểm thử -->
                    <c:if test="${not empty sessionScope.otp_code}">
                        <div class="mt-4 pt-3 border-top text-center">
                            <div class="alert alert-info py-2 px-3 small mb-0 d-inline-block text-start" role="alert">
                                <div><i class="fa-solid fa-terminal me-1"></i> <strong>Chế độ kiểm thử (Debug / Console Log):</strong></div>
                                <div>Mã OTP hiện tại: <span class="badge bg-danger fs-6 px-2 py-1 ms-1" role="button" onclick="document.getElementById('otp').value='${sessionScope.otp_code}'">${sessionScope.otp_code}</span> (Bấm để điền nhanh)</div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</body>
</html>


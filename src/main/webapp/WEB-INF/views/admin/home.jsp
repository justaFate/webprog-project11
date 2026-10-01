<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bảng Điều Khiển Quản Trị</title>
</head>
<body>
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-2 pb-3 mb-4 border-bottom">
        <div>
            <h2 class="fw-bold text-dark mb-0">
                <i class="fa-solid fa-gauge-high text-primary me-2"></i>Bảng Điều Khiển Quản Trị
            </h2>
            <p class="text-muted mb-0 small">Hệ thống phân hệ Admin - Quản lý CSDL Servlet + JPA + JSP</p>
        </div>
        <div class="btn-toolbar mb-2 mb-md-0">
            <a href="${pageContext.request.contextPath}/admin/category/add" class="btn btn-sm btn-primary me-2">
                <i class="fa-solid fa-plus me-1"></i> Thêm Danh Mục
            </a>
            <a href="${pageContext.request.contextPath}/admin/video/add" class="btn btn-sm btn-success">
                <i class="fa-solid fa-upload me-1"></i> Thêm Video
            </a>
        </div>
    </div>

    <!-- Thống kê Card tổng quan -->
    <div class="row g-3 mb-4">
        <div class="col-sm-6 col-xl-3">
            <div class="card admin-card bg-primary text-white p-3">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-uppercase fw-semibold mb-1" style="font-size: 0.8rem;">Danh Mục</h6>
                        <h2 class="fw-bold mb-0">${countCategory}</h2>
                    </div>
                    <div class="fs-1 text-white-50"><i class="fa-solid fa-layer-group"></i></div>
                </div>
                <a href="${pageContext.request.contextPath}/admin/categories" class="text-white-50 text-decoration-none small mt-2 d-inline-block">Chi tiết danh mục &rarr;</a>
            </div>
        </div>

        <div class="col-sm-6 col-xl-3">
            <div class="card admin-card bg-success text-white p-3">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-uppercase fw-semibold mb-1" style="font-size: 0.8rem;">Video / Sản Phẩm</h6>
                        <h2 class="fw-bold mb-0">${countVideo}</h2>
                    </div>
                    <div class="fs-1 text-white-50"><i class="fa-solid fa-video"></i></div>
                </div>
                <a href="${pageContext.request.contextPath}/admin/videos" class="text-white-50 text-decoration-none small mt-2 d-inline-block">Chi tiết video &rarr;</a>
            </div>
        </div>

        <div class="col-sm-6 col-xl-3">
            <div class="card admin-card bg-warning text-dark p-3">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-uppercase fw-semibold mb-1" style="font-size: 0.8rem;">Người Dùng</h6>
                        <h2 class="fw-bold mb-0">${countUser}</h2>
                    </div>
                    <div class="fs-1 text-black-50"><i class="fa-solid fa-users"></i></div>
                </div>
                <span class="text-dark-50 small mt-2 d-inline-block">Tài khoản hệ thống</span>
            </div>
        </div>

        <div class="col-sm-6 col-xl-3">
            <div class="card admin-card bg-danger text-white p-3">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-uppercase fw-semibold mb-1" style="font-size: 0.8rem;">Yêu Thích / Chia Sẻ</h6>
                        <h2 class="fw-bold mb-0">${countFavorite} / ${countShare}</h2>
                    </div>
                    <div class="fs-1 text-white-50"><i class="fa-solid fa-heart"></i></div>
                </div>
                <span class="text-white-50 small mt-2 d-inline-block">Lượt tương tác</span>
            </div>
        </div>
    </div>

    <!-- Thông tin dự án và thí sinh -->
    <div class="card admin-card p-4">
        <h5 class="fw-bold text-dark border-bottom pb-2 mb-3">
            <i class="fa-solid fa-circle-info text-info me-2"></i>Thông Tin Dự Án &amp; Thí Sinh
        </h5>
        <div class="row">
            <div class="col-md-6">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item d-flex justify-content-between">
                        <span class="text-muted">Họ và tên thí sinh:</span>
                        <strong class="text-primary">Huỳnh Tấn Anh Phát</strong>
                    </li>
                    <li class="list-group-item d-flex justify-content-between">
                        <span class="text-muted">Mã số sinh viên (MSSV):</span>
                        <strong>24162091</strong>
                    </li>
                    <li class="list-group-item d-flex justify-content-between">
                        <span class="text-muted">Đề thi:</span>
                        <strong class="badge bg-warning text-dark fs-6">Đề 3</strong>
                    </li>
                </ul>
            </div>
            <div class="col-md-6">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item d-flex justify-content-between">
                        <span class="text-muted">Kiến trúc:</span>
                        <span>Mô hình 3 lớp (Presentation, Business, DAO)</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between">
                        <span class="text-muted">Template Decorator:</span>
                        <span>SiteMesh 3 (User &amp; Admin)</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between">
                        <span class="text-muted">Quy tắc đặt tên file:</span>
                        <span><code>tenclass_24162091.java</code></span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>


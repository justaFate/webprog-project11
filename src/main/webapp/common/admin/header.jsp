<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow">
        <div class="container-fluid px-4">
            <a class="navbar-brand fw-bold text-warning" href="${pageContext.request.contextPath}/admin/home">
                <i class="fa-solid fa-gauge me-2"></i>ADMIN DASHBOARD - 24162091
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#adminNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="adminNavbar">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/home">
                            <i class="fa-solid fa-chart-line me-1"></i> Bảng điều khiển
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/categories">
                            <i class="fa-solid fa-list-check me-1"></i> Quản lý Danh mục
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/videos">
                            <i class="fa-solid fa-video me-1"></i> Quản lý Video
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-info" href="${pageContext.request.contextPath}/home">
                            <i class="fa-solid fa-arrow-up-right-from-square me-1"></i> Xem trang User
                        </a>
                    </li>
                </ul>

                <ul class="navbar-nav ms-auto align-items-center">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-warning fw-bold d-flex align-items-center" href="#" role="button" data-bs-toggle="dropdown">
                            <i class="fa-solid fa-user-shield me-2 fs-5"></i>
                            <span>${not empty sessionScope.account.fullname ? sessionScope.account.fullname : 'Admin'}</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end shadow">
                            <li>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/home">
                                    <i class="fa-solid fa-house me-2"></i> Trang Chủ User
                                </a>
                            </li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                                    <i class="fa-solid fa-right-from-bracket me-2"></i> Đăng xuất
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>


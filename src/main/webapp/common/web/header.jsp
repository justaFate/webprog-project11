<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow">
        <div class="container">
            <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/home">
                <i class="fa-solid fa-play me-2"></i>Dự Án 24162091
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#userNavbar" aria-controls="userNavbar" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="userNavbar">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <!-- Menu: Trang Chủ -->
                    <li class="nav-item">
                        <a class="nav-link px-3" href="${pageContext.request.contextPath}/home">
                            <i class="fa-solid fa-house me-1"></i> Trang Chủ
                        </a>
                    </li>
                    <!-- Menu: Sản phẩm -->
                    <li class="nav-item">
                        <a class="nav-link px-3" href="${pageContext.request.contextPath}/products">
                            <i class="fa-solid fa-film me-1"></i> Sản phẩm
                        </a>
                    </li>
                    <!-- Menu: Giỏ hàng -->
                    <li class="nav-item">
                        <a class="nav-link px-3 position-relative" href="${pageContext.request.contextPath}/cart">
                            <i class="fa-solid fa-cart-shopping me-1"></i> Giỏ hàng
                            <c:if test="${not empty sessionScope.cart and sessionScope.cart.totalQuantity > 0}">
                                <span class="badge bg-danger rounded-pill ms-1">${sessionScope.cart.totalQuantity}</span>
                            </c:if>
                        </a>
                    </li>
                    <!-- Menu: Trang quản trị (CHỈ ADMIN MỚI CÓ CHỨC NĂNG NÀY) -->
                    <c:if test="${sessionScope.account != null and sessionScope.account.admin}">
                        <li class="nav-item">
                            <a class="nav-link px-3 text-warning fw-bold" href="${pageContext.request.contextPath}/admin/home">
                                <i class="fa-solid fa-screwdriver-wrench me-1"></i> Trang quản trị
                            </a>
                        </li>
                    </c:if>
                </ul>

                <!-- Menu: Đăng nhập / Tài khoản -->
                <ul class="navbar-nav ms-auto align-items-center">
                    <c:choose>
                        <c:when test="${empty sessionScope.account}">
                            <li class="nav-item me-2">
                                <a class="nav-link text-white fw-semibold" href="${pageContext.request.contextPath}/register">
                                    <i class="fa-solid fa-user-plus me-1"></i> Đăng ký
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-outline-light px-3 py-1 fw-semibold" href="${pageContext.request.contextPath}/login">
                                    <i class="fa-solid fa-right-to-bracket me-1"></i> Đăng nhập
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle text-white fw-bold d-flex align-items-center" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="fa-solid fa-circle-user fs-5 me-2"></i>
                                    <span>${not empty sessionScope.account.fullname ? sessionScope.account.fullname : sessionScope.account.username}</span>
                                    <c:if test="${sessionScope.account.admin}">
                                        <span class="badge bg-warning text-dark ms-2">Admin</span>
                                    </c:if>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end shadow">
                                    <c:if test="${sessionScope.account.admin}">
                                        <li>
                                            <a class="dropdown-item fw-semibold text-primary" href="${pageContext.request.contextPath}/admin/home">
                                                <i class="fa-solid fa-gauge-high me-2"></i> Vào Trang Quản Trị
                                            </a>
                                        </li>
                                        <li><hr class="dropdown-divider"></li>
                                    </c:if>
                                    <li>
                                        <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                                            <i class="fa-solid fa-arrow-right-from-bracket me-2"></i> Đăng xuất
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
</header>


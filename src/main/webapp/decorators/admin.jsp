<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property='title'/> - Admin Panel 24162091</title>

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <style>
        body {
            background-color: #f4f6f9;
            font-family: system-ui, -apple-system, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
        }
        .sidebar {
            min-height: calc(100vh - 140px);
            background-color: #ffffff;
            border-right: 1px solid #dee2e6;
        }
        .sidebar .nav-link {
            color: #495057;
            font-weight: 500;
            padding: 12px 18px;
            border-radius: 6px;
            margin-bottom: 4px;
        }
        .sidebar .nav-link:hover, .sidebar .nav-link.active {
            background-color: #0d6efd;
            color: #ffffff;
        }
        .sidebar .nav-link i {
            width: 22px;
        }
        .admin-card {
            border-radius: 8px;
            border: none;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }
    </style>

    <sitemesh:write property='head'/>
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- Header Navigation chung cho vai trò Admin -->
    <%@ include file="/common/admin/header.jsp" %>

    <div class="container-fluid flex-grow-1">
        <div class="row">
            <!-- Sidebar Quản trị -->
            <nav class="col-md-3 col-lg-2 d-md-block sidebar py-3">
                <div class="position-sticky">
                    <h6 class="text-uppercase text-muted px-3 mb-3 fw-bold" style="font-size: 0.75rem;">Menu Quản Trị</h6>
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/home">
                                <i class="fa-solid fa-gauge-high me-2"></i> Bảng điều khiển
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/categories">
                                <i class="fa-solid fa-layer-group me-2"></i> Quản lý Danh mục
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/videos">
                                <i class="fa-solid fa-video me-2"></i> Quản lý Video
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-success" href="${pageContext.request.contextPath}/home">
                                <i class="fa-solid fa-arrow-left me-2"></i> Xem trang User
                            </a>
                        </li>
                        <li class="nav-item mt-3">
                            <a class="nav-link text-danger" href="${pageContext.request.contextPath}/logout">
                                <i class="fa-solid fa-right-from-bracket me-2"></i> Đăng xuất
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <!-- Nội dung chính của từng trang Admin -->
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
                <sitemesh:write property='body'/>
            </main>
        </div>
    </div>

    <!-- Footer chung cho vai trò Admin -->
    <%@ include file="/common/admin/footer.jsp" %>

    <!-- Bootstrap 5 JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


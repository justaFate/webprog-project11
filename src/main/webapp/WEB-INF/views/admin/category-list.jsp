<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản Lý Danh Mục</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold text-dark mb-0">
                <i class="fa-solid fa-list text-primary me-2"></i>Danh Sách Danh Mục
            </h3>
            <p class="text-muted small mb-0">Quản lý các chuyên mục phân loại video</p>
        </div>
        <a href="${pageContext.request.contextPath}/admin/category/add" class="btn btn-primary">
            <i class="fa-solid fa-plus me-1"></i> Thêm Danh Mục Mới
        </a>
    </div>

    <div class="card admin-card shadow-sm border-0">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                        <tr>
                            <th class="ps-3" style="width: 70px;">ID</th>
                            <th>Hình ảnh</th>
                            <th>Tên danh mục</th>
                            <th>Mã danh mục</th>
                            <th>Trạng thái</th>
                            <th class="text-center" style="width: 160px;">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${categories}" var="cate">
                            <tr>
                                <td class="ps-3 fw-bold text-muted">${cate.categoryId}</td>
                                <td>
                                    <img src="${pageContext.request.contextPath}/images/${cate.images}" 
                                         alt="${cate.categoryname}" 
                                         class="rounded border" 
                                         style="width: 60px; height: 45px; object-fit: cover;"
                                         onerror="this.src='https://placehold.co/60x45?text=Cate'">
                                </td>
                                <td class="fw-semibold text-dark">${cate.categoryname}</td>
                                <td><span class="badge bg-secondary">${cate.categorycode}</span></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${cate.status}">
                                            <span class="badge bg-success"><i class="fa-solid fa-check me-1"></i>Hoạt động</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger"><i class="fa-solid fa-lock me-1"></i>Khóa</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center">
                                    <a href="${pageContext.request.contextPath}/admin/category/edit?id=${cate.categoryId}" class="btn btn-sm btn-outline-primary me-1">
                                        <i class="fa-solid fa-pen-to-square"></i> Sửa
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/category/delete?id=${cate.categoryId}" 
                                       class="btn btn-sm btn-outline-danger" 
                                       onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này không?');">
                                        <i class="fa-solid fa-trash"></i> Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty categories}">
                            <tr>
                                <td colspan="6" class="text-center text-muted py-4">Chưa có danh mục nào trong hệ thống.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>


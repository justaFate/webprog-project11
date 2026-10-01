<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cập Nhật Danh Mục</title>
</head>
<body>
    <div class="mb-4">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/home">Bảng điều khiển</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/categories">Danh mục</a></li>
                <li class="breadcrumb-item active">Cập nhật</li>
            </ol>
        </nav>
        <h3 class="fw-bold text-dark">
            <i class="fa-solid fa-pen-to-square text-primary me-2"></i>Cập Nhật Danh Mục #${category.categoryId}
        </h3>
    </div>

    <div class="card admin-card shadow-sm border-0 p-4">
        <form action="${pageContext.request.contextPath}/admin/category/update" method="post">
            <input type="hidden" name="categoryId" value="${category.categoryId}">

            <div class="row g-3">
                <div class="col-md-6">
                    <label for="categoryname" class="form-label fw-semibold">Tên Danh Mục <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="categoryname" name="categoryname" value="${category.categoryname}" required>
                </div>

                <div class="col-md-6">
                    <label for="categorycode" class="form-label fw-semibold">Mã Danh Mục <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="categorycode" name="categorycode" value="${category.categorycode}" required>
                </div>

                <div class="col-md-12">
                    <label for="images" class="form-label fw-semibold">Tên tệp hình ảnh / Đường dẫn</label>
                    <input type="text" class="form-control" id="images" name="images" value="${category.images}">
                </div>

                <div class="col-md-12">
                    <label class="form-label fw-semibold d-block">Trạng Thái</label>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="status" id="status1" value="1" ${category.status ? 'checked' : ''}>
                        <label class="form-check-label text-success fw-semibold" for="status1">
                            <i class="fa-solid fa-check me-1"></i> Hoạt động
                        </label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="status" id="status0" value="0" ${not category.status ? 'checked' : ''}>
                        <label class="form-check-label text-danger fw-semibold" for="status0">
                            <i class="fa-solid fa-lock me-1"></i> Khóa
                        </label>
                    </div>
                </div>

                <div class="col-12 mt-4 pt-3 border-top d-flex gap-2">
                    <button type="submit" class="btn btn-primary px-4">
                        <i class="fa-solid fa-floppy-disk me-1"></i> Lưu Cập Nhật
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-outline-secondary px-4">
                        Hủy Bỏ
                    </a>
                </div>
            </div>
        </form>
    </div>
</body>
</html>


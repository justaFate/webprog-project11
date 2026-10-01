<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thêm Video Mới</title>
</head>
<body>
    <div class="mb-4">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/home">Bảng điều khiển</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/videos">Video</a></li>
                <li class="breadcrumb-item active">Thêm mới</li>
            </ol>
        </nav>
        <h3 class="fw-bold text-dark">
            <i class="fa-solid fa-plus-circle text-success me-2"></i>Thêm Mới Video
        </h3>
    </div>

    <div class="card admin-card shadow-sm border-0 p-4">
        <form action="${pageContext.request.contextPath}/admin/video/insert" method="post">
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="videoId" class="form-label fw-semibold">Mã Video (ID) <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="videoId" name="videoId" placeholder="Ví dụ: V001 hoặc youtube-id" required>
                </div>

                <div class="col-md-6">
                    <label for="title" class="form-label fw-semibold">Tiêu Đề Video <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="title" name="title" placeholder="Nhập tiêu đề video" required>
                </div>

                <div class="col-md-6">
                    <label for="categoryId" class="form-label fw-semibold">Danh Mục Trực Thuộc</label>
                    <select class="form-select" id="categoryId" name="categoryId">
                        <option value="">-- Chọn danh mục --</option>
                        <c:forEach items="${categories}" var="cat">
                            <option value="${cat.categoryId}">${cat.categoryname}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-6">
                    <label for="poster" class="form-label fw-semibold">Hình Poster / Ảnh đại diện</label>
                    <input type="text" class="form-control" id="poster" name="poster" placeholder="Ví dụ: poster1.jpg hoặc https://...">
                </div>

                <div class="col-12">
                    <label for="description" class="form-label fw-semibold">Mô Tả Video</label>
                    <textarea class="form-control" id="description" name="description" rows="4" placeholder="Nhập mô tả nội dung..."></textarea>
                </div>

                <div class="col-12">
                    <label class="form-label fw-semibold d-block">Trạng Thái</label>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="active" id="active1" value="1" checked>
                        <label class="form-check-label text-success fw-semibold" for="active1">
                            <i class="fa-solid fa-check me-1"></i> Hoạt động
                        </label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="active" id="active0" value="0">
                        <label class="form-check-label text-danger fw-semibold" for="active0">
                            <i class="fa-solid fa-lock me-1"></i> Khóa
                        </label>
                    </div>
                </div>

                <div class="col-12 mt-4 pt-3 border-top d-flex gap-2">
                    <button type="submit" class="btn btn-success px-4">
                        <i class="fa-solid fa-floppy-disk me-1"></i> Lưu Video
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/videos" class="btn btn-outline-secondary px-4">
                        Hủy Bỏ
                    </a>
                </div>
            </div>
        </form>
    </div>
</body>
</html>


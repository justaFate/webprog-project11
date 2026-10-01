<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản Lý Video - Phân Trang 6 Video/Trang</title>
</head>
<body>
    <!-- Tiêu đề trang -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div>
            <h3 class="fw-bold text-dark mb-0">
                <i class="fa-solid fa-video text-primary me-2"></i>Quản Trị Dữ Liệu Bảng Videos
            </h3>
            <p class="text-muted small mb-0">Chức năng CRUD (Thêm, Xem, Cập nhật, Xóa) - Phân trang cố định 6 video / 01 trang</p>
        </div>
        <a href="${pageContext.request.contextPath}/admin/video/add" class="btn btn-success fw-semibold">
            <i class="fa-solid fa-plus-circle me-1"></i> Thêm Video Mới
        </a>
    </div>

    <!-- Thông báo kết quả thao tác CRUD (Flash Message) -->
    <c:if test="${not empty message}">
        <div class="alert alert-success alert-dismissible fade show py-2 small shadow-sm" role="alert">
            <i class="fa-solid fa-circle-check me-2"></i> ${message}
            <button type="button" class="btn-close py-2" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show py-2 small shadow-sm" role="alert">
            <i class="fa-solid fa-triangle-exclamation me-2"></i> ${errorMessage}
            <button type="button" class="btn-close py-2" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Thanh tìm kiếm và bộ lọc -->
    <div class="card admin-card shadow-sm border-0 mb-4">
        <div class="card-body p-3">
            <form action="${pageContext.request.contextPath}/admin/videos" method="get" class="row g-2 align-items-center">
                <div class="col-md-6 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-text bg-white"><i class="fa-solid fa-magnifying-glass text-muted"></i></span>
                        <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm video theo tiêu đề..." value="${keyword}">
                    </div>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">
                        <i class="fa-solid fa-filter me-1"></i> Tìm kiếm
                    </button>
                    <c:if test="${not empty keyword}">
                        <a href="${pageContext.request.contextPath}/admin/videos" class="btn btn-outline-secondary ms-1">
                            <i class="fa-solid fa-xmark me-1"></i> Bỏ lọc
                        </a>
                    </c:if>
                </div>
                <div class="col text-end text-muted small d-none d-md-block">
                    <span class="badge bg-primary-subtle text-primary border border-primary-subtle px-3 py-2 fs-6">
                        <i class="fa-solid fa-layer-group me-1"></i> Phân trang: <strong>${pageSize}</strong> video / trang
                    </span>
                </div>
            </form>
        </div>
    </div>

    <!-- Bảng danh sách dữ liệu Videos (READ) -->
    <div class="card admin-card shadow-sm border-0 mb-4">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                        <tr>
                            <th class="ps-3" style="width: 60px;">STT</th>
                            <th style="width: 120px;">Mã Video</th>
                            <th style="width: 100px;">Hình Poster</th>
                            <th>Tiêu Đề Video</th>
                            <th>Danh Mục</th>
                            <th style="width: 110px;">Lượt Xem</th>
                            <th style="width: 130px;">Trạng Thái</th>
                            <th class="text-center" style="width: 160px;">Thao Tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${videos}" var="video" varStatus="stt">
                            <tr>
                                <td class="ps-3 fw-bold text-muted">${(currentPage - 1) * pageSize + stt.count}</td>
                                <td>
                                    <span class="badge bg-secondary font-monospace fs-6">${video.videoId}</span>
                                </td>
                                <td>
                                    <img src="${pageContext.request.contextPath}/images/${video.poster}" 
                                         alt="${video.title}" 
                                         class="rounded border shadow-sm" 
                                         style="width: 80px; height: 50px; object-fit: cover;"
                                         onerror="this.src='https://placehold.co/80x50/333/fff?text=No+Image'">
                                </td>
                                <td>
                                    <div class="fw-bold text-dark text-truncate" style="max-width: 280px;" title="${video.title}">
                                        ${video.title}
                                    </div>
                                    <small class="text-muted text-truncate d-block" style="max-width: 280px;">
                                        ${not empty video.description ? video.description : 'Không có mô tả'}
                                    </small>
                                </td>
                                <td>
                                    <span class="badge bg-info-subtle text-info-emphasis border border-info-subtle">
                                        <i class="fa-solid fa-folder me-1"></i>${video.category != null ? video.category.categoryname : 'Chưa gán'}
                                    </span>
                                </td>
                                <td>
                                    <span class="badge bg-light text-dark border">
                                        <i class="fa-solid fa-eye me-1 text-primary"></i> ${video.views}
                                    </span>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${video.active}">
                                            <span class="badge bg-success-subtle text-success border border-success-subtle">
                                                <i class="fa-solid fa-circle-check me-1"></i> Hoạt động
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger-subtle text-danger border border-danger-subtle">
                                                <i class="fa-solid fa-lock me-1"></i> Đã khóa
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center">
                                    <!-- Nút SỬA (UPDATE) -->
                                    <a href="${pageContext.request.contextPath}/admin/video/edit?id=${video.videoId}" 
                                       class="btn btn-sm btn-outline-primary me-1" title="Cập nhật video">
                                        <i class="fa-solid fa-pen-to-square"></i> Sửa
                                    </a>
                                    <!-- Nút XÓA (DELETE) -->
                                    <a href="${pageContext.request.contextPath}/admin/video/delete?id=${video.videoId}" 
                                       class="btn btn-sm btn-outline-danger" 
                                       title="Xóa video"
                                       onclick="return confirm('Bạn có chắc chắn muốn xóa video: [${video.videoId}] ${video.title} không?');">
                                        <i class="fa-solid fa-trash"></i> Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty videos}">
                            <tr>
                                <td colspan="8" class="text-center text-muted py-5">
                                    <i class="fa-solid fa-video-slash fs-1 d-block mb-2 text-secondary"></i>
                                    Không có video nào trong danh sách.
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- THANH PHÂN TRANG -->
        <div class="card-footer bg-white d-flex flex-column flex-md-row justify-content-between align-items-center py-3">
            <div class="text-muted small mb-2 mb-md-0">
                Hiển thị <strong>${videos.size()}</strong> / tổng số <strong>${totalVideos}</strong> video 
                (Trang <strong>${currentPage}</strong> trên <strong>${totalPages}</strong> trang)
            </div>

            <nav aria-label="Page navigation">
                <ul class="pagination pagination-sm mb-0">
                    <!-- Nút Trang Trước (Previous) -->
                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/videos?page=${currentPage - 1}&keyword=${keyword}" aria-label="Previous">
                            <span aria-hidden="true">&laquo; Trước</span>
                        </a>
                    </li>

                    <!-- Các nút số trang -->
                    <c:forEach begin="1" end="${totalPages}" var="p">
                        <li class="page-item ${p == currentPage ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/videos?page=${p}&keyword=${keyword}">
                                ${p}
                            </a>
                        </li>
                    </c:forEach>

                    <!-- Nút Trang Sau (Next) -->
                    <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/videos?page=${currentPage + 1}&keyword=${keyword}" aria-label="Next">
                            <span aria-hidden="true">Sau &raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</body>
</html>

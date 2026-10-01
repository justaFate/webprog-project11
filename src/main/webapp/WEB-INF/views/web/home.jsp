<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trang Chủ - Xem Video Theo Category</title>
    <style>
        .category-header-title {
            font-size: 1.5rem;
            font-weight: 700;
            color: #212529;
            margin-bottom: 20px;
            padding-bottom: 8px;
            border-bottom: 2px solid #333;
        }
        .video-box {
            border: 2px solid #333;
            background-color: #fff;
            height: 100%;
            display: flex;
            flex-direction: column;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }
        .video-box:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
        }
        .video-poster-wrapper {
            border-bottom: 2px solid #333;
            background-color: #f8f9fa;
            height: 200px;
            overflow: hidden;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .video-poster-wrapper img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .video-info {
            padding: 16px;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
            gap: 6px;
            font-size: 0.95rem;
        }
        .video-info strong {
            color: #333;
            min-width: 110px;
            display: inline-block;
        }
        .nav-category-pills .nav-link {
            border: 1px solid #dee2e6;
            color: #495057;
            font-weight: 600;
            margin-right: 8px;
            margin-bottom: 8px;
            border-radius: 20px;
            padding: 6px 16px;
        }
        .nav-category-pills .nav-link.active {
            background-color: #0d6efd;
            border-color: #0d6efd;
            color: #fff;
        }
    </style>
</head>
<body>
    <div class="container my-4">
        <!-- Banner chào mừng -->
        <div class="p-4 mb-4 rounded-3 text-white shadow-sm" style="background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%);">
            <div class="container-fluid py-2">
                <h2 class="fw-bold mb-1">HỆ THỐNG VIDEO THEO TỪNG CATEGORY</h2>
                <p class="mb-0 fs-6">Đề 3 - Sinh viên: Huỳnh Tấn Anh Phát - MSSV: 24162091</p>
            </div>
        </div>

        <!-- Thông báo giỏ hàng nếu có -->
        <c:if test="${not empty sessionScope.cartAlertMsg}">
            <div class="alert alert-${not empty sessionScope.cartAlertType ? sessionScope.cartAlertType : 'info'} alert-dismissible fade show shadow-sm mb-4" role="alert">
                <i class="fa-solid fa-circle-check me-2"></i> ${sessionScope.cartAlertMsg}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <c:remove var="cartAlertMsg" scope="session" />
            <c:remove var="cartAlertType" scope="session" />
            <c:remove var="message" scope="session" />
        </c:if>

        <!-- CÂU 6: Danh sách Category kèm đếm số lượng Video theo từng Category -->
        <div class="mb-4">
            <h5 class="fw-bold text-secondary mb-2">
                <i class="fa-solid fa-layer-group me-1"></i> Chọn Danh Mục (Category):
            </h5>
            <div class="nav nav-pills nav-category-pills">
                <c:forEach items="${categories}" var="cat">
                    <a class="nav-link ${cat.categoryId == currentCategory.categoryId ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/home?categoryId=${cat.categoryId}&page=1">
                        ${cat.categoryname} <span class="badge ${cat.categoryId == currentCategory.categoryId ? 'bg-light text-primary' : 'bg-secondary'} ms-1">(${cat.videoCount})</span>
                    </a>
                </c:forEach>
            </div>
        </div>

        <!-- CÂU 5 & CÂU 6: BỐ CỤC THEO ĐÚNG MẪU BẢNG TRONG ĐỀ THI -->
        <c:choose>
            <c:when test="${not empty currentCategory}">
                <!-- Tiêu đề: Category Name (Số lượng video) -->
                <div class="category-header-title d-flex justify-content-between align-items-center">
                    <span>${currentCategory.categoryname} (${currentCategory.videoCount})</span>
                    <span class="fs-6 fw-normal text-muted">
                        <i class="fa-solid fa-list-check me-1"></i> Phân trang: <strong>${pageSize}</strong> video / trang
                    </span>
                </div>

                <!-- Lưới 3 video trên 01 trang (3 video/trang) -->
                <div class="row row-cols-1 row-cols-md-3 g-4 mb-4">
                    <c:forEach items="${videos}" var="video">
                        <div class="col">
                            <div class="video-box rounded-1 shadow-sm">
                                <!-- [poster] -->
                                <div class="video-poster-wrapper">
                                    <a href="${pageContext.request.contextPath}/video/detail?id=${video.videoId}" title="Xem chi tiết ${video.title}">
                                        <img src="${pageContext.request.contextPath}/images/${video.poster}" 
                                             alt="${video.title}"
                                             onerror="this.src='https://placehold.co/400x250/333/fff?text=No+Poster'">
                                    </a>
                                </div>

                                <!-- Thông tin chi tiết theo đúng mẫu -->
                                <div class="video-info">
                                    <div>
                                        <strong>Tiêu đề:</strong> 
                                        <a href="${pageContext.request.contextPath}/video/detail?id=${video.videoId}" class="text-decoration-none fw-bold text-dark text-truncate d-inline-block align-bottom" style="max-width: 60%;" title="${video.title}">
                                            ${video.title}
                                        </a>
                                    </div>

                                    <div>
                                        <strong>Mã video:</strong> 
                                        <span class="badge bg-secondary font-monospace">${video.videoId}</span>
                                    </div>

                                    <div>
                                        <strong>Category name:</strong> 
                                        <span class="text-primary fw-semibold">${video.category != null ? video.category.categoryname : currentCategory.categoryname}</span>
                                    </div>

                                    <div>
                                        <strong>View:</strong> 
                                        <span class="badge bg-light text-dark border"><i class="fa-solid fa-eye me-1 text-primary"></i>${video.views}</span>
                                    </div>

                                    <div>
                                        <strong>Giá bán:</strong> 
                                        <span class="text-danger fw-bold fs-6">${video.formattedPrice}</span>
                                    </div>

                                    <div class="d-flex gap-2 mt-auto pt-2 border-top">
                                        <a href="${pageContext.request.contextPath}/video/share?id=${video.videoId}" class="btn btn-sm btn-outline-info flex-fill">
                                            <i class="fa-solid fa-share-nodes me-1"></i>Share(${video.shareCount})
                                        </a>
                                        <a href="${pageContext.request.contextPath}/video/like?id=${video.videoId}" class="btn btn-sm btn-outline-danger flex-fill">
                                            <i class="fa-solid fa-heart me-1"></i>Like(${video.likeCount})
                                        </a>
                                    </div>

                                    <div class="mt-2">
                                        <a href="${pageContext.request.contextPath}/cart/add?id=${video.videoId}&quantity=1" class="btn btn-sm btn-primary w-100" title="Thêm vào giỏ hàng">
                                            <i class="fa-solid fa-cart-plus me-1"></i> Thêm vào giỏ
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    <c:if test="${empty videos}">
                        <div class="col-12 py-5 text-center text-muted">
                            <i class="fa-solid fa-video-slash fs-1 d-block mb-2 text-secondary"></i>
                            Không có video nào trong danh mục này.
                        </div>
                    </c:if>
                </div>

                <!-- CÂU 5: PHÂN TRANG THEO MẪU << 1 2 3 4 5 >> -->
                <c:if test="${totalPages > 0}">
                    <div class="d-flex justify-content-center my-4">
                        <nav aria-label="Page navigation">
                            <ul class="pagination pagination-lg mb-0 shadow-sm">
                                <!-- Nút Trước: << -->
                                <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/home?categoryId=${currentCategory.categoryId}&page=${currentPage - 1}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>

                                <!-- Danh sách các số trang: 1 2 3 4 5... -->
                                <c:forEach begin="1" end="${totalPages}" var="p">
                                    <li class="page-item ${p == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/home?categoryId=${currentCategory.categoryId}&page=${p}">
                                            ${p}
                                        </a>
                                    </li>
                                </c:forEach>

                                <!-- Nút Sau: >> -->
                                <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/home?categoryId=${currentCategory.categoryId}&page=${currentPage + 1}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </c:if>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning text-center py-4">
                    Chưa có danh mục nào trong hệ thống!
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>

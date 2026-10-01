<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${video != null ? video.title : 'Chi Tiết Video'}</title>
</head>
<body>
    <div class="container py-4">
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home">Trang Chủ</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/products">Sản Phẩm</a></li>
                <li class="breadcrumb-item active" aria-current="page">${video.title}</li>
            </ol>
        </nav>

        <c:choose>
            <c:when test="${not empty video}">
                <div class="row g-4">
                    <!-- Ảnh đại diện / Video frame -->
                    <div class="col-lg-7">
                        <div class="card shadow-sm border-0 overflow-hidden">
                            <img src="${pageContext.request.contextPath}/images/${video.poster}" 
                                 class="img-fluid w-100" 
                                 style="max-height: 450px; object-fit: cover;"
                                 alt="${video.title}"
                                 onerror="this.src='https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=800&auto=format&fit=crop&q=80'">
                        </div>
                    </div>

                    <!-- Thông tin chi tiết -->
                    <div class="col-lg-5">
                        <div class="card shadow-sm border-0 p-4 h-100">
                            <span class="badge bg-primary text-uppercase align-self-start mb-2">
                                ${video.category != null ? video.category.categoryname : 'Danh mục chung'}
                            </span>
                            <h2 class="fw-bold mb-3">${video.title}</h2>
                            
                            <div class="d-flex align-items-center gap-4 text-muted mb-4 pb-3 border-bottom">
                                <div><i class="fa-solid fa-barcode me-1"></i> Mã: <strong>${video.videoId}</strong></div>
                                <div><i class="fa-solid fa-eye me-1"></i> Lượt xem: <strong>${video.views}</strong></div>
                                <div><i class="fa-solid fa-circle-check text-success me-1"></i> ${video.active ? 'Hoạt động' : 'Tạm khóa'}</div>
                            </div>

                            <div class="mb-3">
                                <span class="text-muted">Giá bán:</span>
                                <span class="text-danger fw-bold fs-4 ms-2">${video.formattedPrice}</span>
                            </div>

                            <!-- Khối thêm vào giỏ hàng -->
                            <form action="${pageContext.request.contextPath}/cart/add" method="post" class="p-3 bg-light rounded border mb-4">
                                <input type="hidden" name="id" value="${video.videoId}">
                                <div class="d-flex align-items-center gap-3 mb-3">
                                    <label class="fw-bold mb-0">Số lượng:</label>
                                    <input type="number" name="quantity" value="1" min="1" max="10" 
                                           class="form-control text-center fw-bold" style="width: 100px;">
                                    <small class="text-muted">(1 - 10)</small>
                                </div>
                                <div class="d-flex gap-2">
                                    <button type="submit" class="btn btn-primary flex-fill">
                                        <i class="fa-solid fa-cart-plus me-1"></i> Thêm vào giỏ
                                    </button>
                                    <button type="submit" name="viewCart" value="true" class="btn btn-danger flex-fill">
                                        <i class="fa-solid fa-bolt me-1"></i> Mua ngay
                                    </button>
                                </div>
                            </form>

                            <h5 class="fw-bold mb-2">Mô tả sản phẩm:</h5>
                            <p class="text-secondary leading-relaxed mb-4">
                                ${not empty video.description ? video.description : 'Không có mô tả chi tiết cho video này.'}
                            </p>

                            <div class="mt-auto d-flex gap-2">
                                <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-secondary">
                                    <i class="fa-solid fa-arrow-left me-1"></i> Quay lại
                                </a>
                                <button type="button" class="btn btn-danger">
                                    <i class="fa-solid fa-heart me-1"></i> Yêu Thích
                                </button>
                                <button type="button" class="btn btn-info text-white">
                                    <i class="fa-solid fa-share-nodes me-1"></i> Chia Sẻ
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning text-center py-5">
                    <h4>Không tìm thấy thông tin sản phẩm/video yêu cầu!</h4>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary mt-3">Quay lại danh sách</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>


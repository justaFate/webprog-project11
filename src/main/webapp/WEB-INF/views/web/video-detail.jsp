<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi Tiết Video - ${video.title}</title>
    <style>
        .detail-box {
            border: 2px solid #333;
            background-color: #fff;
        }
        .detail-top-left {
            border-right: 2px solid #333;
            border-bottom: 2px solid #333;
            padding: 24px;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #fdfdfd;
        }
        .detail-top-right {
            border-bottom: 2px solid #333;
            padding: 24px;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }
        .detail-bottom {
            padding: 24px;
            min-height: 120px;
        }
        .detail-row-item {
            font-size: 1.15rem;
            margin-bottom: 12px;
        }
    </style>
</head>
<body>
    <div class="container my-5">
        <!-- Breadcrumb & Tiêu đề hướng dẫn -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb mb-1">
                        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home" class="text-decoration-none">Trang Chủ</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Chi Tiết Video</li>
                    </ol>
                </nav>
                <h4 class="fw-bold text-dark mb-0">CÂU 4: Trang Chi Tiết 01 Video</h4>
            </div>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary">
                <i class="fa-solid fa-arrow-left me-1"></i> Quay lại Trang chủ
            </a>
        </div>

        <c:if test="${not empty sessionScope.message}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fa-solid fa-circle-check me-2"></i> ${sessionScope.message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <c:remove var="message" scope="session" />
        </c:if>

        <!-- KHUNG CHI TIẾT VIDEO THEO ĐÚNG MẪU ĐỀ BÀI -->
        <div class="detail-box shadow-sm rounded-1">
            <div class="row g-0">
                <!-- Cột trái: [poster] -->
                <div class="col-md-6 detail-top-left">
                    <img src="${pageContext.request.contextPath}/images/${video.poster}" 
                         alt="${video.title}" 
                         class="img-fluid rounded border shadow-sm" 
                         style="max-height: 340px; width: 100%; object-fit: cover;"
                         onerror="this.src='https://placehold.co/600x400/222/fff?text=No+Poster'">
                </div>

                <!-- Cột phải: Thông tin -->
                <div class="col-md-6 detail-top-right">
                    <div class="detail-row-item">
                        <strong>Tiêu đề:</strong> <span>${video.title}</span>
                    </div>

                    <div class="detail-row-item">
                        <strong>Mã video:</strong> <span class="badge bg-secondary font-monospace fs-6">${video.videoId}</span>
                    </div>

                    <div class="detail-row-item">
                        <strong>Category name:</strong> <span class="text-primary fw-semibold">${video.category != null ? video.category.categoryname : 'Chưa phân loại'}</span>
                    </div>

                    <div class="detail-row-item">
                        <strong>View:</strong> <span class="badge bg-light text-dark border fs-6"><i class="fa-solid fa-eye me-1 text-primary"></i>${video.views}</span>
                    </div>

                    <div class="detail-row-item">
                        <strong>Giá bán:</strong> <span class="text-danger fw-bold fs-4">${video.formattedPrice}</span>
                    </div>

                    <!-- KHỐI CHỌN SỐ LƯỢNG VÀ THÊM VÀO GIỎ HÀNG (GIỚI HẠN 1-10) -->
                    <form action="${pageContext.request.contextPath}/cart/add" method="post" class="mt-3 mb-2 p-3 bg-light rounded border">
                        <input type="hidden" name="id" value="${video.videoId}">
                        <div class="d-flex align-items-center gap-3 mb-3">
                            <label class="fw-bold text-dark mb-0">Số lượng:</label>
                            <div class="input-group" style="width: 140px;">
                                <button type="button" class="btn btn-outline-secondary" onclick="changeDetailQty(-1)">
                                    <i class="fa-solid fa-minus"></i>
                                </button>
                                <input type="number" id="detailQty" name="quantity" value="1" min="1" max="10" 
                                       class="form-control text-center fw-bold" onchange="checkDetailQty(this)">
                                <button type="button" class="btn btn-outline-secondary" onclick="changeDetailQty(1)">
                                    <i class="fa-solid fa-plus"></i>
                                </button>
                            </div>
                            <small class="text-muted">(Tối đa 10)</small>
                        </div>

                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary flex-fill">
                                <i class="fa-solid fa-cart-plus me-1"></i> Thêm vào giỏ hàng
                            </button>
                            <button type="submit" name="viewCart" value="true" class="btn btn-danger flex-fill">
                                <i class="fa-solid fa-bolt me-1"></i> Mua ngay
                            </button>
                        </div>
                    </form>

                    <div class="detail-row-item mt-2">
                        <a href="${pageContext.request.contextPath}/video/share?id=${video.videoId}" class="btn btn-outline-info me-2">
                            <i class="fa-solid fa-share-nodes me-1"></i> Share(${video.shareCount})
                        </a>
                        <a href="${pageContext.request.contextPath}/video/like?id=${video.videoId}" class="btn btn-outline-danger">
                            <i class="fa-solid fa-heart me-1"></i> Like(${video.likeCount})
                        </a>
                    </div>
                </div>
            </div>

            <!-- Hàng dưới: description -->
            <div class="detail-bottom">
                <h5 class="fw-bold mb-2">description</h5>
                <p class="text-secondary fs-6 mb-0" style="white-space: pre-line;">${not empty video.description ? video.description : 'Không có mô tả cho video này.'}</p>
            </div>
        </div>
    </div>

    <script>
        function changeDetailQty(delta) {
            var input = document.getElementById('detailQty');
            var current = parseInt(input.value, 10);
            if (isNaN(current)) current = 1;
            var next = current + delta;
            if (next < 1) {
                alert('Số lượng tối thiểu là 1!');
                next = 1;
            } else if (next > 10) {
                alert('Số lượng tối đa cho phép là 10!');
                next = 10;
            }
            input.value = next;
        }

        function checkDetailQty(input) {
            var val = parseInt(input.value, 10);
            if (isNaN(val) || val < 1) {
                alert('Số lượng tối thiểu là 1!');
                input.value = 1;
            } else if (val > 10) {
                alert('Số lượng tối đa cho phép là 10!');
                input.value = 10;
            }
        }
    </script>
</body>
</html>


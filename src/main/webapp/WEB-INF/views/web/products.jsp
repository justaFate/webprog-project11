<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh Sách Sản Phẩm / Video</title>
</head>
<body>
    <div class="container py-4">
        <!-- Breadcrumb & Tiêu đề -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home">Trang Chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Sản Phẩm</li>
            </ol>
        </nav>

        <div class="row mb-4 align-items-center">
            <div class="col-md-6">
                <h2 class="fw-bold text-primary mb-1">
                    <i class="fa-solid fa-film me-2"></i>Tất Cả Sản Phẩm &amp; Video
                </h2>
                <p class="text-muted mb-0">Khám phá nội dung phong phú và hấp dẫn</p>
            </div>
            <!-- Thanh tìm kiếm -->
            <div class="col-md-6 mt-3 mt-md-0">
                <form action="${pageContext.request.contextPath}/products" method="get" class="d-flex gap-2">
                    <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm theo tiêu đề video..." value="${keyword}">
                    <button type="submit" class="btn btn-primary px-4">
                        <i class="fa-solid fa-magnifying-glass"></i>
                    </button>
                </form>
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

        <div class="row">
            <!-- Cột trái: Bộ lọc danh mục -->
            <div class="col-lg-3 mb-4">
                <div class="card shadow-sm border-0">
                    <div class="card-header bg-primary text-white fw-bold">
                        <i class="fa-solid fa-list me-2"></i> Danh Mục Phân Loại
                    </div>
                    <div class="list-group list-group-flush">
                        <a href="${pageContext.request.contextPath}/products" 
                           class="list-group-item list-group-item-action d-flex justify-content-between align-items-center ${empty selectedCategoryId ? 'active' : ''}">
                            <span>Tất cả danh mục</span>
                        </a>
                        <c:forEach items="${categories}" var="cat">
                            <a href="${pageContext.request.contextPath}/products?categoryId=${cat.categoryId}" 
                               class="list-group-item list-group-item-action d-flex justify-content-between align-items-center ${selectedCategoryId == cat.categoryId ? 'active' : ''}">
                                <span>${cat.categoryname}</span>
                                <span class="badge ${selectedCategoryId == cat.categoryId ? 'bg-light text-dark' : 'bg-secondary'} rounded-pill">${cat.categorycode}</span>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <!-- Cột phải: Danh sách video / sản phẩm -->
            <div class="col-lg-9">
                <div class="row row-cols-1 row-cols-md-2 row-cols-xl-3 g-4">
                    <c:forEach items="${videos}" var="video">
                        <div class="col">
                            <div class="card h-100 shadow-sm border-0">
                                <div class="position-relative">
                                    <img src="${pageContext.request.contextPath}/images/${video.poster}" 
                                         class="card-img-top video-card-img" 
                                         alt="${video.title}"
                                         onerror="this.src='https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=500&auto=format&fit=crop&q=60'">
                                    <span class="badge bg-danger position-absolute top-0 end-0 m-2">
                                        <i class="fa-solid fa-eye me-1"></i> ${video.views}
                                    </span>
                                </div>
                                <div class="card-body d-flex flex-column">
                                    <h5 class="card-title fw-bold text-dark text-truncate" title="${video.title}">${video.title}</h5>
                                    <p class="card-text text-muted small flex-grow-1">${video.description}</p>
                                    
                                    <div class="d-flex justify-content-between align-items-center mb-2">
                                        <span class="badge bg-info text-dark">
                                            ${video.category != null ? video.category.categoryname : 'Mặc định'}
                                        </span>
                                        <span class="fw-bold text-danger fs-6">${video.formattedPrice}</span>
                                    </div>

                                    <div class="d-flex gap-2 mt-auto pt-2 border-top">
                                        <a href="${pageContext.request.contextPath}/product/detail?id=${video.videoId}" class="btn btn-sm btn-outline-primary flex-fill">
                                            <i class="fa-solid fa-circle-info me-1"></i> Chi tiết
                                        </a>
                                        <a href="${pageContext.request.contextPath}/cart/add?id=${video.videoId}&quantity=1" class="btn btn-sm btn-primary flex-fill" title="Thêm vào giỏ hàng">
                                            <i class="fa-solid fa-cart-plus me-1"></i> Thêm giỏ
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <c:if test="${empty videos}">
                    <div class="alert alert-info text-center my-4 p-4 shadow-sm" role="alert">
                        <i class="fa-solid fa-circle-exclamation fs-3 d-block mb-2"></i>
                        Không tìm thấy video nào phù hợp với điều kiện tìm kiếm.
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>


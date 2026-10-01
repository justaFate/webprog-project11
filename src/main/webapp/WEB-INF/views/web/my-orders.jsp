<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đơn Hàng Của Tôi</title>
</head>
<body>
    <div class="container py-4">
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home" class="text-decoration-none">Trang Chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Lịch Sử Đơn Hàng</li>
            </ol>
        </nav>

        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="fw-bold text-primary mb-1">
                    <i class="fa-solid fa-clock-rotate-left me-2"></i>Đơn Hàng Của Tôi
                </h2>
                <p class="text-muted mb-0">Theo dõi trạng thái các đơn hàng COD và trực tuyến đã đặt</p>
            </div>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary">
                <i class="fa-solid fa-cart-plus me-1"></i> Mua sắm thêm
            </a>
        </div>

        <c:choose>
            <c:when test="${empty orders}">
                <div class="card shadow-sm border-0 py-5 text-center my-4">
                    <div class="card-body">
                        <div class="mb-3 text-muted">
                            <i class="fa-solid fa-box-open" style="font-size: 4.5rem; opacity: 0.35;"></i>
                        </div>
                        <h4 class="fw-bold text-secondary mb-2">Bạn chưa có đơn hàng nào!</h4>
                        <p class="text-muted mb-4">Hãy khám phá danh sách sản phẩm và đặt hàng để theo dõi tại đây nhé.</p>
                        <a href="${pageContext.request.contextPath}/products" class="btn btn-primary px-4 shadow">
                            <i class="fa-solid fa-bag-shopping me-1"></i> Khám Phá Sản Phẩm Ngay
                        </a>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <div class="card shadow-sm border-0">
                    <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
                        <span class="fw-bold fs-5 text-dark">
                            <i class="fa-solid fa-list-check text-primary me-2"></i>Danh Sách Đơn Hàng (${orders.size()})
                        </span>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0">
                            <thead class="table-light text-secondary">
                                <tr>
                                    <th scope="col">Mã đơn hàng</th>
                                    <th scope="col">Ngày đặt</th>
                                    <th scope="col">Người nhận</th>
                                    <th scope="col">Phương thức</th>
                                    <th scope="col">Số lượng</th>
                                    <th scope="col" class="text-end">Tổng tiền</th>
                                    <th scope="col" class="text-center">Trạng thái</th>
                                    <th scope="col" class="text-center" style="width: 130px;">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${orders}" var="ord">
                                    <tr>
                                        <td>
                                            <span class="fw-bold text-primary font-monospace">${ord.orderId}</span>
                                        </td>
                                        <td class="text-muted small">${ord.formattedOrderDate}</td>
                                        <td>
                                            <div><strong>${ord.fullname}</strong></div>
                                            <small class="text-muted">${ord.phone}</small>
                                        </td>
                                        <td>
                                            <span class="badge bg-light text-dark border">
                                                <i class="fa-solid fa-hand-holding-dollar text-success me-1"></i>${ord.paymentMethod}
                                            </span>
                                        </td>
                                        <td>${ord.totalQuantity} món</td>
                                        <td class="text-end fw-bold text-danger">${ord.formattedTotalAmount}</td>
                                        <td class="text-center">
                                            <span class="badge bg-warning text-dark px-2 py-1">
                                                ${ord.status}
                                            </span>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/order/detail?id=${ord.orderId}" 
                                               class="btn btn-sm btn-outline-primary" title="Xem chi tiết hóa đơn">
                                                <i class="fa-solid fa-eye me-1"></i> Chi tiết
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>

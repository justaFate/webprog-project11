<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hóa Đơn Đơn Hàng COD - ${order.orderId}</title>
    <style>
        .invoice-card {
            border-radius: 1rem;
            background-color: #fff;
        }
        .step-icon-circle {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            font-size: 1.25rem;
        }
        .table-invoice th, .table-invoice td {
            vertical-align: middle;
        }
        @media print {
            body * {
                visibility: hidden;
            }
            #printableInvoice, #printableInvoice * {
                visibility: visible;
            }
            #printableInvoice {
                position: absolute;
                left: 0;
                top: 0;
                width: 100%;
            }
            .no-print {
                display: none !important;
            }
        }
    </style>
</head>
<body>
    <div class="container py-4">
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb" class="no-print">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home" class="text-decoration-none">Trang Chủ</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/products" class="text-decoration-none">Sản Phẩm</a></li>
                <li class="breadcrumb-item active" aria-current="page">Hóa Đơn Đơn Hàng</li>
            </ol>
        </nav>

        <!-- Thông báo Đặt hàng thành công nổi bật -->
        <div class="card shadow-sm border-0 mb-4 bg-success text-white py-3 px-4 rounded-3 no-print">
            <div class="d-flex align-items-center">
                <div class="fs-1 me-3">
                    <i class="fa-solid fa-circle-check"></i>
                </div>
                <div>
                    <h3 class="fw-bold mb-1">ĐẶT HÀNG COD THÀNH CÔNG!</h3>
                    <p class="mb-0 fs-6 opacity-90">
                        Cảm ơn quý khách <strong>${order.fullname}</strong> đã tin tưởng đặt mua sản phẩm. Đơn hàng của bạn đang được hệ thống tiếp nhận.
                    </p>
                </div>
            </div>
        </div>

        <!-- Quy trình giao nhận đơn hàng COD -->
        <div class="card shadow-sm border-0 mb-4 no-print">
            <div class="card-header bg-white py-3 fw-bold text-dark fs-6 border-bottom">
                <i class="fa-solid fa-route text-primary me-2"></i>Quy Trình Giao Nhận &amp; Thanh Toán Tiền Mặt (COD)
            </div>
            <div class="card-body p-4">
                <div class="row g-3 text-center">
                    <div class="col-md-4">
                        <div class="step-icon-circle bg-primary-subtle text-primary mb-2">
                            <i class="fa-solid fa-phone-volume"></i>
                        </div>
                        <h6 class="fw-bold mb-1">1. Gọi Điện Xác Nhận</h6>
                        <p class="text-muted small mb-0">Nhân viên CSKH sẽ liên hệ SĐT <strong>${order.phone}</strong> để xác nhận đơn và địa chỉ nhận.</p>
                    </div>
                    <div class="col-md-4">
                        <div class="step-icon-circle bg-warning-subtle text-warning mb-2">
                            <i class="fa-solid fa-truck-fast"></i>
                        </div>
                        <h6 class="fw-bold mb-1">2. Đóng Gói &amp; Vận Chuyển</h6>
                        <p class="text-muted small mb-0">Kiện hàng được đóng gói cẩn thận và chuyển phát nhanh đến tận địa chỉ của bạn trong 2 - 3 ngày.</p>
                    </div>
                    <div class="col-md-4">
                        <div class="step-icon-circle bg-success-subtle text-success mb-2">
                            <i class="fa-solid fa-hand-holding-dollar"></i>
                        </div>
                        <h6 class="fw-bold mb-1">3. Kiểm Tra &amp; Trả Tiền Mặt</h6>
                        <p class="text-muted small mb-0">Được đồng kiểm tra hàng cùng shipper và thanh toán đúng số tiền <strong>${order.formattedTotalAmount}</strong>.</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Khung Hóa đơn chi tiết (Có thể in ấn) -->
        <div class="card shadow-sm border-0 invoice-card p-4 mb-4" id="printableInvoice">
            <!-- Header Hóa đơn -->
            <div class="d-flex justify-content-between align-items-start border-bottom pb-4 mb-4">
                <div>
                    <h3 class="fw-bold text-primary mb-1">
                        <i class="fa-solid fa-receipt me-2"></i>HÓA ĐƠN ĐƠN HÀNG COD
                    </h3>
                    <p class="text-muted mb-0">Hệ thống thương mại điện tử - Web Programming 24162091</p>
                    <span class="badge bg-primary fs-6 mt-2">
                        ${order.status}
                    </span>
                </div>
                <div class="text-end">
                    <h5 class="fw-bold mb-1 font-monospace text-dark">MÃ ĐƠN: ${order.orderId}</h5>
                    <div class="text-muted small">Ngày đặt: <strong>${order.formattedOrderDate}</strong></div>
                    <div class="text-muted small">Hình thức: <span class="badge bg-info text-dark">COD - Nhận hàng trả tiền</span></div>
                </div>
            </div>

            <!-- Thông tin khách hàng & Địa chỉ -->
            <div class="row g-4 mb-4">
                <div class="col-md-6">
                    <h6 class="fw-bold text-uppercase text-secondary mb-2" style="letter-spacing: 1px;">
                        <i class="fa-solid fa-user me-1 text-primary"></i> Thông Tin Khách Hàng:
                    </h6>
                    <div class="p-3 bg-light rounded border">
                        <div class="mb-1"><strong>Họ và tên:</strong> ${order.fullname}</div>
                        <div class="mb-1"><strong>Số điện thoại:</strong> <span class="text-primary fw-bold">${order.phone}</span></div>
                        <div><strong>Email:</strong> ${not empty order.email ? order.email : 'Chưa cung cấp'}</div>
                    </div>
                </div>
                <div class="col-md-6">
                    <h6 class="fw-bold text-uppercase text-secondary mb-2" style="letter-spacing: 1px;">
                        <i class="fa-solid fa-truck-location me-1 text-primary"></i> Địa Chỉ Giao Hàng (COD):
                    </h6>
                    <div class="p-3 bg-light rounded border">
                        <div class="mb-1"><strong>Địa chỉ nhận:</strong> ${order.address}</div>
                        <div><strong>Ghi chú giao hàng:</strong> <em>${not empty order.note ? order.note : 'Không có ghi chú'}</em></div>
                    </div>
                </div>
            </div>

            <!-- Bảng danh sách mặt hàng đã đặt -->
            <h6 class="fw-bold text-uppercase text-secondary mb-2" style="letter-spacing: 1px;">
                <i class="fa-solid fa-boxes-packing me-1 text-primary"></i> Chi Tiết Mặt Hàng:
            </h6>
            <div class="table-responsive mb-4">
                <table class="table table-bordered table-invoice align-middle">
                    <thead class="table-light text-secondary">
                        <tr>
                            <th scope="col" style="width: 50px;" class="text-center">#</th>
                            <th scope="col">Tên sản phẩm / Khóa học</th>
                            <th scope="col" style="width: 140px;" class="text-center">Đơn giá</th>
                            <th scope="col" style="width: 100px;" class="text-center">Số lượng</th>
                            <th scope="col" style="width: 150px;" class="text-end">Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${order.orderDetails}" var="detail" varStatus="loop">
                            <tr>
                                <td class="text-center fw-bold">${loop.index + 1}</td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <c:if test="${detail.video != null}">
                                            <img src="${pageContext.request.contextPath}/images/${detail.video.poster}" 
                                                 alt="${detail.video.title}" 
                                                 class="rounded me-2 border" 
                                                 style="width: 50px; height: 35px; object-fit: cover;"
                                                 onerror="this.src='https://placehold.co/50x35/222/fff?text=No+Img'">
                                            <div>
                                                <strong>${detail.video.title}</strong>
                                                <small class="text-muted d-block font-monospace">Mã: ${detail.video.videoId}</small>
                                            </div>
                                        </c:if>
                                    </div>
                                </td>
                                <td class="text-center">${detail.formattedPrice}</td>
                                <td class="text-center fw-bold">x${detail.quantity}</td>
                                <td class="text-end fw-bold">${detail.formattedTotal}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="4" class="text-end fw-semibold text-muted">Tạm tính tiền hàng:</td>
                            <td class="text-end fw-bold text-dark">${order.formattedSubtotal}</td>
                        </tr>
                        <tr>
                            <td colspan="4" class="text-end fw-semibold text-muted">Phí vận chuyển COD:</td>
                            <td class="text-end fw-bold text-success">${order.formattedShippingFee}</td>
                        </tr>
                        <tr class="table-light">
                            <td colspan="4" class="text-end fs-5 fw-bold text-dark">TỔNG TIỀN PHẢI THANH TOÁN (COD):</td>
                            <td class="text-end fs-4 fw-bold text-danger">${order.formattedTotalAmount}</td>
                        </tr>
                    </tfoot>
                </table>
            </div>

            <!-- Lời nhắc nhở nhận hàng -->
            <div class="alert alert-warning py-3 mb-0">
                <i class="fa-solid fa-circle-exclamation me-2 fs-5"></i>
                <strong>Lưu ý quan trọng cho đơn COD:</strong> Quý khách vui lòng để ý điện thoại để shipper gọi giao hàng và chuẩn bị đúng <strong>${order.formattedTotalAmount}</strong> tiền mặt để thanh toán thuận tiện nhất!
            </div>
        </div>

        <!-- Các nút điều hướng thao tác -->
        <div class="d-flex flex-wrap justify-content-between align-items-center gap-2 no-print">
            <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary btn-lg">
                <i class="fa-solid fa-arrow-left me-1"></i> Tiếp tục xem sản phẩm
            </a>
            <div class="d-flex gap-2">
                <button type="button" class="btn btn-secondary btn-lg" onclick="window.print()">
                    <i class="fa-solid fa-print me-1"></i> In Hóa Đơn
                </button>
                <a href="${pageContext.request.contextPath}/my-orders" class="btn btn-primary btn-lg">
                    <i class="fa-solid fa-clock-rotate-left me-1"></i> Đơn Hàng Của Tôi
                </a>
            </div>
        </div>
    </div>
</body>
</html>

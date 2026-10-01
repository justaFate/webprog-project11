package webprog.anhphat_24162091.entities;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "Orders")
@NamedQuery(name = "Order_24162091.findAll", query = "SELECT o FROM Order_24162091 o ORDER BY o.orderDate DESC")
public class Order_24162091 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "OrderId", length = 50, nullable = false)
    private String orderId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OrderDate")
    private Date orderDate;

    @Column(name = "Fullname", length = 100, columnDefinition = "NVARCHAR(100)")
    private String fullname;

    @Column(name = "Phone", length = 20)
    private String phone;

    @Column(name = "Email", length = 150)
    private String email;

    @Column(name = "Address", length = 500, columnDefinition = "NVARCHAR(500)")
    private String address;

    @Column(name = "Note", length = 500, columnDefinition = "NVARCHAR(500)")
    private String note;

    @Column(name = "PaymentMethod", length = 50, columnDefinition = "NVARCHAR(50)")
    private String paymentMethod; // "COD"

    @Column(name = "ShippingFee")
    private Double shippingFee;

    @Column(name = "TotalAmount")
    private Double totalAmount;

    @Column(name = "Status", length = 50, columnDefinition = "NVARCHAR(50)")
    private String status; // "Chờ xác nhận (COD)", "Đang giao hàng", "Đã giao thành công", "Đã hủy"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Username")
    private User_24162091 user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetail_24162091> orderDetails = new ArrayList<>();

    public Order_24162091() {
        this.orderDate = new Date();
        this.paymentMethod = "COD";
        this.status = "Chờ xác nhận (COD)";
        this.shippingFee = 0.0;
        this.totalAmount = 0.0;
    }

    public Order_24162091(String orderId, String fullname, String phone, String email, String address, String note,
            String paymentMethod, Double shippingFee, Double totalAmount, String status, User_24162091 user) {
        this.orderId = orderId;
        this.orderDate = new Date();
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.note = note;
        this.paymentMethod = paymentMethod != null ? paymentMethod : "COD";
        this.shippingFee = shippingFee != null ? shippingFee : 0.0;
        this.totalAmount = totalAmount != null ? totalAmount : 0.0;
        this.status = status != null ? status : "Chờ xác nhận (COD)";
        this.user = user;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getShippingFee() {
        return shippingFee != null ? shippingFee : 0.0;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Double getTotalAmount() {
        return totalAmount != null ? totalAmount : 0.0;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User_24162091 getUser() {
        return user;
    }

    public void setUser(User_24162091 user) {
        this.user = user;
    }

    public List<OrderDetail_24162091> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail_24162091> orderDetails) {
        this.orderDetails = orderDetails;
    }

    // Helper format methods
    public String getFormattedTotalAmount() {
        DecimalFormat df = new DecimalFormat("###,###,### ₫");
        return df.format(getTotalAmount());
    }

    public String getFormattedShippingFee() {
        if (shippingFee == null || shippingFee <= 0) {
            return "Miễn phí";
        }
        DecimalFormat df = new DecimalFormat("###,###,### ₫");
        return df.format(shippingFee);
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        if (orderDetails != null) {
            for (OrderDetail_24162091 d : orderDetails) {
                subtotal += d.getTotal();
            }
        }
        return subtotal;
    }

    public String getFormattedSubtotal() {
        DecimalFormat df = new DecimalFormat("###,###,### ₫");
        return df.format(getSubtotal());
    }

    public String getFormattedOrderDate() {
        if (orderDate == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(orderDate);
    }

    public int getTotalQuantity() {
        int qty = 0;
        if (orderDetails != null) {
            for (OrderDetail_24162091 d : orderDetails) {
                qty += (d.getQuantity() != null ? d.getQuantity() : 0);
            }
        }
        return qty;
    }
}

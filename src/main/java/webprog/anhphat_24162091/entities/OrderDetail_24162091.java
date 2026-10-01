package webprog.anhphat_24162091.entities;

import java.io.Serializable;
import java.text.DecimalFormat;
import jakarta.persistence.*;

@Entity
@Table(name = "OrderDetails")
public class OrderDetail_24162091 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailId")
    private Integer orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderId", nullable = false)
    private Order_24162091 order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VideoId")
    private Video_24162091 video;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Price")
    private Double price;

    public OrderDetail_24162091() {
        this.quantity = 1;
        this.price = 0.0;
    }

    public OrderDetail_24162091(Order_24162091 order, Video_24162091 video, Integer quantity, Double price) {
        this.order = order;
        this.video = video;
        this.quantity = quantity != null ? quantity : 1;
        this.price = price != null ? price : 0.0;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Order_24162091 getOrder() {
        return order;
    }

    public void setOrder(Order_24162091 order) {
        this.order = order;
    }

    public Video_24162091 getVideo() {
        return video;
    }

    public void setVideo(Video_24162091 video) {
        this.video = video;
    }

    public Integer getQuantity() {
        return quantity != null ? quantity : 1;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price != null ? price : 0.0;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public double getTotal() {
        return getPrice() * getQuantity();
    }

    public String getFormattedPrice() {
        DecimalFormat df = new DecimalFormat("###,###,### ₫");
        return df.format(getPrice());
    }

    public String getFormattedTotal() {
        DecimalFormat df = new DecimalFormat("###,###,### ₫");
        return df.format(getTotal());
    }
}

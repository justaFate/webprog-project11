package webprog.anhphat_24162091.entities;

import java.io.Serializable;
import java.text.DecimalFormat;

public class CartItem_24162091 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Video_24162091 video;
    private int quantity;
    private double price;

    public CartItem_24162091() {
        this.quantity = 1;
        this.price = 150000.0;
    }

    public CartItem_24162091(Video_24162091 video, int quantity, double price) {
        this.video = video;
        this.quantity = quantity;
        this.price = price;
    }

    public Video_24162091 getVideo() {
        return video;
    }

    public void setVideo(Video_24162091 video) {
        this.video = video;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return this.price * this.quantity;
    }

    public String getFormattedPrice() {
        DecimalFormat df = new DecimalFormat("###,###,### ₫");
        return df.format(this.price);
    }

    public String getFormattedTotal() {
        DecimalFormat df = new DecimalFormat("###,###,### ₫");
        return df.format(getTotal());
    }
}


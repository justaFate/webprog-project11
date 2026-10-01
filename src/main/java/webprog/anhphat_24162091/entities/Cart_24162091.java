package webprog.anhphat_24162091.entities;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import webprog.anhphat_24162091.configs.Constant_24162091;

public class Cart_24162091 implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int MIN_QUANTITY = Constant_24162091.CART_MIN_QUANTITY;
    public static final int MAX_QUANTITY = Constant_24162091.CART_MAX_QUANTITY;

    private final Map<String, CartItem_24162091> items = new LinkedHashMap<>();

    public Cart_24162091() {
    }

    public Map<String, CartItem_24162091> getItems() {
        return items;
    }

    public Collection<CartItem_24162091> getItemList() {
        return items.values();
    }

    /**
     * Thêm sản phẩm vào giỏ hàng với số lượng trong giới hạn
     * @param video Đối tượng sản phẩm
     * @param quantity Số lượng muốn thêm
     * @return Thông báo kết quả thao tác
     */
    public String add(Video_24162091 video, int quantity) {
        if (video == null) {
            return "Sản phẩm không hợp lệ!";
        }

        if (quantity < MIN_QUANTITY) {
            quantity = MIN_QUANTITY;
        }

        String videoId = video.getVideoId();
        String message;

        if (items.containsKey(videoId)) {
            CartItem_24162091 existingItem = items.get(videoId);
            int currentQty = existingItem.getQuantity();
            int newQty = currentQty + quantity;

            if (newQty > MAX_QUANTITY) {
                existingItem.setQuantity(MAX_QUANTITY);
                message = "Sản phẩm \"" + video.getTitle() + "\" đã đạt số lượng tối đa cho phép (" + MAX_QUANTITY + " sản phẩm) trong giỏ hàng!";
            } else {
                existingItem.setQuantity(newQty);
                message = "Đã tăng số lượng \"" + video.getTitle() + "\" lên " + newQty + " trong giỏ hàng!";
            }
        } else {
            int initialQty = quantity;
            if (initialQty > MAX_QUANTITY) {
                initialQty = MAX_QUANTITY;
                message = "Số lượng thêm vượt quá giới hạn! Đã thêm \"" + video.getTitle() + "\" với số lượng tối đa là " + MAX_QUANTITY + ".";
            } else {
                message = "Đã thêm \"" + video.getTitle() + "\" vào giỏ hàng thành công!";
            }
            double price = (video.getPrice() != null && video.getPrice() > 0) ? video.getPrice() : 150000.0;
            items.put(videoId, new CartItem_24162091(video, initialQty, price));
        }

        return message;
    }

    /**
     * Sửa / cập nhật số lượng sản phẩm trong giỏ hàng với kiểm tra giới hạn nghiêm ngặt
     * @param videoId Mã sản phẩm
     * @param quantity Số lượng mới
     * @return Thông báo kết quả
     */
    public String update(String videoId, int quantity) {
        if (!items.containsKey(videoId)) {
            return "Sản phẩm không tồn tại trong giỏ hàng!";
        }

        CartItem_24162091 item = items.get(videoId);
        String title = (item.getVideo() != null && item.getVideo().getTitle() != null) ? item.getVideo().getTitle() : videoId;

        if (quantity <= 0) {
            items.remove(videoId);
            return "Đã xóa \"" + title + "\" khỏi giỏ hàng do số lượng bằng 0.";
        }

        if (quantity > MAX_QUANTITY) {
            item.setQuantity(MAX_QUANTITY);
            return "Số lượng tối đa cho mỗi sản phẩm là " + MAX_QUANTITY + ". Đã điều chỉnh \"" + title + "\" về " + MAX_QUANTITY + " sản phẩm!";
        }

        item.setQuantity(quantity);
        return "Đã cập nhật số lượng của \"" + title + "\" thành " + quantity + " thành công!";
    }

    /**
     * Tăng số lượng sản phẩm thêm 1 (không vượt quá MAX_QUANTITY)
     */
    public String increase(String videoId) {
        if (!items.containsKey(videoId)) {
            return "Sản phẩm không tồn tại trong giỏ hàng!";
        }
        CartItem_24162091 item = items.get(videoId);
        if (item.getQuantity() >= MAX_QUANTITY) {
            return "Đã đạt số lượng tối đa (" + MAX_QUANTITY + " sản phẩm) cho mặt hàng này!";
        }
        item.setQuantity(item.getQuantity() + 1);
        return "Đã tăng số lượng thành công!";
    }

    /**
     * Giảm số lượng sản phẩm đi 1 (không thấp hơn MIN_QUANTITY)
     */
    public String decrease(String videoId) {
        if (!items.containsKey(videoId)) {
            return "Sản phẩm không tồn tại trong giỏ hàng!";
        }
        CartItem_24162091 item = items.get(videoId);
        if (item.getQuantity() <= MIN_QUANTITY) {
            return "Số lượng tối thiểu là " + MIN_QUANTITY + ". Nếu muốn xóa sản phẩm, vui lòng chọn nút Xóa!";
        }
        item.setQuantity(item.getQuantity() - 1);
        return "Đã giảm số lượng thành công!";
    }

    /**
     * Xóa 1 sản phẩm khỏi giỏ hàng
     */
    public boolean remove(String videoId) {
        return items.remove(videoId) != null;
    }

    /**
     * Xóa sạch toàn bộ giỏ hàng
     */
    public void clear() {
        items.clear();
    }

    /**
     * Tổng số lượng các sản phẩm trong giỏ
     */
    public int getTotalQuantity() {
        int total = 0;
        for (CartItem_24162091 item : items.values()) {
            total += item.getQuantity();
        }
        return total;
    }

    /**
     * Số loại sản phẩm khác nhau trong giỏ
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * Tổng giá trị tiền của giỏ hàng
     */
    public double getTotalAmount() {
        double total = 0;
        for (CartItem_24162091 item : items.values()) {
            total += item.getTotal();
        }
        return total;
    }

    public String getFormattedTotalAmount() {
        DecimalFormat df = new DecimalFormat("###,###,### ₫");
        return df.format(getTotalAmount());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}


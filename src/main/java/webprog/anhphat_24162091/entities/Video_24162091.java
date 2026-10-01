package webprog.anhphat_24162091.entities;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "Videos")
@NamedQuery(name = "Video_24162091.findAll", query = "SELECT v FROM Video_24162091 v")
public class Video_24162091 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "VideoId", length = 50, nullable = false)
    private String videoId;

    @Column(name = "Title", length = 200, columnDefinition = "NVARCHAR(200)")
    private String title;

    @Column(name = "Poster", length = 50)
    private String poster;

    @Column(name = "Views")
    private Integer views;

    @Column(name = "Description", length = 500, columnDefinition = "NVARCHAR(500)")
    private String description;

    @Column(name = "Active")
    private Boolean active;

    @Column(name = "Price")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryId")
    private Category_24162091 category;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Favorite_24162091> favorites;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Share_24162091> shares;

    @Transient
    private int likeCount;

    @Transient
    private int shareCount;

    public Video_24162091() {
        this.active = true;
        this.views = 0;
        this.price = 150000.0;
    }

    public Video_24162091(String videoId, String title, String poster, Integer views, String description,
            Boolean active, Category_24162091 category) {
        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
        this.views = views != null ? views : 0;
        this.description = description;
        this.active = active != null ? active : true;
        this.category = category;
        this.price = 150000.0;
    }

    public Video_24162091(String videoId, String title, String poster, Integer views, String description,
            Boolean active, Category_24162091 category, Double price) {
        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
        this.views = views != null ? views : 0;
        this.description = description;
        this.active = active != null ? active : true;
        this.category = category;
        this.price = (price != null && price > 0) ? price : 150000.0;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getViews() {
        return views != null ? views : 0;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active != null ? active : true;
    }

    public boolean isActive() {
        return active != null && active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Category_24162091 getCategory() {
        return category;
    }

    public void setCategory(Category_24162091 category) {
        this.category = category;
    }

    public List<Favorite_24162091> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite_24162091> favorites) {
        this.favorites = favorites;
    }

    public List<Share_24162091> getShares() {
        return shares;
    }

    public void setShares(List<Share_24162091> shares) {
        this.shares = shares;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public Double getPrice() {
        if (price == null || price <= 0) {
            return 150000.0;
        }
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFormattedPrice() {
        java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,### ₫");
        return df.format(getPrice());
    }
}


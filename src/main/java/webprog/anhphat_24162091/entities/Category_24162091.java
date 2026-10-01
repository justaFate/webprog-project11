package webprog.anhphat_24162091.entities;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "Category")
@NamedQuery(name = "Category_24162091.findAll", query = "SELECT c FROM Category_24162091 c")
public class Category_24162091 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private int categoryId;

    @Column(name = "Categoryname", length = 100, columnDefinition = "NVARCHAR(100)")
    private String categoryname;

    @Column(name = "Categorycode", length = 100, columnDefinition = "NVARCHAR(100)")
    private String categorycode;

    @Column(name = "Images", length = 500)
    private String images;

    @Column(name = "Status")
    private Boolean status;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Video_24162091> videos;

    @Transient
    private int videoCount;

    public Category_24162091() {
        this.status = true;
    }

    public Category_24162091(String categoryname, String categorycode, String images, Boolean status) {
        this.categoryname = categoryname;
        this.categorycode = categorycode;
        this.images = images;
        this.status = status;
    }

    public Category_24162091(int categoryId, String categoryname, String categorycode, String images, Boolean status) {
        this.categoryId = categoryId;
        this.categoryname = categoryname;
        this.categorycode = categorycode;
        this.images = images;
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCategorycode() {
        return categorycode;
    }

    public void setCategorycode(String categorycode) {
        this.categorycode = categorycode;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Boolean getStatus() {
        return status != null ? status : true;
    }

    public boolean isStatus() {
        return status != null && status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Video_24162091> getVideos() {
        return videos;
    }

    public void setVideos(List<Video_24162091> videos) {
        this.videos = videos;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }
}


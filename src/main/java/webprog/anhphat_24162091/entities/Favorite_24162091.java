package webprog.anhphat_24162091.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "Favorites")
public class Favorite_24162091 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FavoriteId")
    private int favoriteId;

    @Column(name = "LikedDate")
    @Temporal(TemporalType.DATE)
    private Date likedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VideoId")
    private Video_24162091 video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Username")
    private User_24162091 user;

    public Favorite_24162091() {
        this.likedDate = new Date();
    }

    public Favorite_24162091(Date likedDate, Video_24162091 video, User_24162091 user) {
        this.likedDate = likedDate != null ? likedDate : new Date();
        this.video = video;
        this.user = user;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Date getLikedDate() {
        return likedDate;
    }

    public void setLikedDate(Date likedDate) {
        this.likedDate = likedDate;
    }

    public Video_24162091 getVideo() {
        return video;
    }

    public void setVideo(Video_24162091 video) {
        this.video = video;
    }

    public User_24162091 getUser() {
        return user;
    }

    public void setUser(User_24162091 user) {
        this.user = user;
    }
}


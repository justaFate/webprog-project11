package webprog.anhphat_24162091.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "Shares")
public class Share_24162091 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShareId")
    private int shareId;

    @Column(name = "Emails", length = 50)
    private String emails;

    @Column(name = "SharedDate")
    @Temporal(TemporalType.DATE)
    private Date sharedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Username")
    private User_24162091 user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VideoId")
    private Video_24162091 video;

    public Share_24162091() {
        this.sharedDate = new Date();
    }

    public Share_24162091(String emails, Date sharedDate, User_24162091 user, Video_24162091 video) {
        this.emails = emails;
        this.sharedDate = sharedDate != null ? sharedDate : new Date();
        this.user = user;
        this.video = video;
    }

    public int getShareId() {
        return shareId;
    }

    public void setShareId(int shareId) {
        this.shareId = shareId;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public Date getSharedDate() {
        return sharedDate;
    }

    public void setSharedDate(Date sharedDate) {
        this.sharedDate = sharedDate;
    }

    public User_24162091 getUser() {
        return user;
    }

    public void setUser(User_24162091 user) {
        this.user = user;
    }

    public Video_24162091 getVideo() {
        return video;
    }

    public void setVideo(Video_24162091 video) {
        this.video = video;
    }
}


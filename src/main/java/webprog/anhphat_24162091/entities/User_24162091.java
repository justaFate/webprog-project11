package webprog.anhphat_24162091.entities;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "Users")
@NamedQuery(name = "User_24162091.findAll", query = "SELECT u FROM User_24162091 u")
public class User_24162091 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Username", length = 50, nullable = false)
    private String username;

    @Column(name = "Password", length = 50)
    private String password;

    @Column(name = "Phone", length = 15)
    private String phone;

    @Column(name = "Fullname", length = 50, columnDefinition = "NVARCHAR(50)")
    private String fullname;

    @Column(name = "Email", length = 150)
    private String email;

    @Column(name = "Admin")
    private Boolean admin;

    @Column(name = "Active")
    private Boolean active;

    @Column(name = "Images", length = 500)
    private String images;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Favorite_24162091> favorites;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Share_24162091> shares;

    public User_24162091() {
        this.admin = false;
        this.active = true;
    }

    public User_24162091(String username, String password, String phone, String fullname, String email, Boolean admin,
            Boolean active, String images) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.fullname = fullname;
        this.email = email;
        this.admin = admin;
        this.active = active;
        this.images = images;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return admin != null ? admin : false;
    }

    public boolean isAdmin() {
        return admin != null && admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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
}


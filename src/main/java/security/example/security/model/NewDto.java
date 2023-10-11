package security.example.security.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "news")
public class NewDto {
    @PrePersist
    protected void onCreate() {
        this.create_At = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.update_At = new Date(System.currentTimeMillis());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10000)
    private String title;
    @Column
    private String image;
    private Date create_At;
    private Date update_At;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", nullable = false)
    private User user;

    public NewDto() {
    }

    public NewDto(Long id, String title, String image, Date create_At, Date update_At, User user) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.create_At = create_At;
        this.update_At = update_At;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreate_At() {
        return create_At;
    }

    public void setCreate_At(Date create_At) {
        this.create_At = create_At;
    }

    public Date getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(Date update_At) {
        this.update_At = update_At;
    }
}

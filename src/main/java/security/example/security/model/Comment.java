package security.example.security.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {
    @PrePersist
    protected void onCreate() {
        this.create_At = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.update_At = new Date(System.currentTimeMillis());
    }
    private Date create_At;
    private Date update_At;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newdto_id", nullable = false)
    private NewDto newDto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", nullable = false)
    private User user;

    public Comment() {
    }

    public Comment(Date create_At, Date update_At, Long id, String body, NewDto newDto, User user) {
        this.create_At = create_At;
        this.update_At = update_At;
        this.id = id;
        this.body = body;
        this.newDto = newDto;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NewDto getNewDto() {
        return newDto;
    }

    public void setNewDto(NewDto newDto) {
        this.newDto = newDto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

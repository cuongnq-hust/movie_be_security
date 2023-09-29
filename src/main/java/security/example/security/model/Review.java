package security.example.security.model;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "reviews")
public class Review {
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

    private String body;

    private Date create_At;
    private Date update_At;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    public Review() {
    }

    public Review(Long id, String body, Date create_At, Date update_At, Movie movie) {
        this.id = id;
        this.body = body;
        this.create_At = create_At;
        this.update_At = update_At;
        this.movie = movie;
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

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}

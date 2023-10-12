package security.example.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {
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

    private String title;
    private String trailerLink;
    private String poster;
    private String avatar;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryMovie categoryMovie;
    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private List<Review> reviews = new ArrayList<>();
    private Date create_At;
    private Date update_At;

    public Movie() {
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Movie(Long id, String title, String trailerLink, String poster, String avatar, CategoryMovie categoryMovie, List<Review> reviews, Date create_At, Date update_At) {
        this.id = id;
        this.title = title;
        this.trailerLink = trailerLink;
        this.poster = poster;
        this.avatar = avatar;
        this.categoryMovie = categoryMovie;
        this.reviews = reviews;
        this.create_At = create_At;
        this.update_At = update_At;
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

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public CategoryMovie getCategoryMovie() {
        return categoryMovie;
    }

    public void setCategoryMovie(CategoryMovie categoryMovie) {
        this.categoryMovie = categoryMovie;
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
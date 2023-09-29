package security.example.security.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    private String backdrops;
    private String genres;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews = new ArrayList<>();

    private Date create_At;
    private Date update_At;

    public Movie(Long id, String title, String trailerLink, String poster, String backdrops, String genres, List<Review> reviews, Date create_At, Date update_At) {
        this.id = id;
        this.title = title;
        this.trailerLink = trailerLink;
        this.poster = poster;
        this.backdrops = backdrops;
        this.genres = genres;
        this.reviews = reviews;
        this.create_At = create_At;
        this.update_At = update_At;
    }

    public Movie() {
    }
    public List<String> getBackdrops() {
        return Arrays.asList(backdrops.split(";"));
    }

    public void setBackdrops(List<String> backdrops) {
        this.backdrops = String.join(";", backdrops);
    }

    public List<String> getGenres() {
        return Arrays.asList(genres.split(";"));
    }

    public void setGenres(List<String> genres) {
        this.genres = String.join(";", genres);
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
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

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", trailerLink='" + trailerLink + '\'' +
                ", poster='" + poster + '\'' +
                ", genres=" + genres +
                ", backdrops=" + backdrops +
                ", reviews=" + reviews +
                ", create_At=" + create_At +
                ", update_At=" + update_At +
                '}';
    }
}
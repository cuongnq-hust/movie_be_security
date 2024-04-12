package security.example.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MOVIE")
@Getter
@Setter
public class Movie extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private CategoryEntity categoryMovie;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TRAILER_LINK")
    private String trailerLink;

    @Column(name = "POSTER")
    private String poster;

    @Column(name = "AVATAR")
    private String avatar;

    @Column(name = "PRICE")
    private float price;

    @Column(name = "DELETE_FLAG")
    private int deleteFlag;

    @JsonIgnore
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private List<Review> reviews = new ArrayList<>();

}
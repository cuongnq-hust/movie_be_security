package security.example.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "REVIEW")
@Getter
@Setter
public class Review extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BODY")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVIE_ID", nullable = false)
    private Movie movie;


    @Column(name = "DELETE_FLAG")
    private int deleteFlag;

    @JsonIgnore
    @OneToMany(mappedBy = "review")
    private List<CommentEntity> commentEntityList = new ArrayList<>();

}

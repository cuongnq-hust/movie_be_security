package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import security.example.security.entity.CommentEntity;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    CommentEntity findCommentById(Long id);

    @Query(value = """
            SELECT * from COMMENT cmt WHERE cmt.REVIEW_ID = :id
            AND cmt.DELETE_FLAG = 0
            """, nativeQuery = true)
    List<CommentEntity> findCommentsByReviewId(@Param("id") Long id);
}

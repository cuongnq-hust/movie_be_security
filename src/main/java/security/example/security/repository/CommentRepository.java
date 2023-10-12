package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import security.example.security.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long id);
    @Query(value = """
        select * from comments cmt where cmt.review_id = :id
        """
            , countQuery = """
        select count(cmt.id) from comments rv where cmt.review_id = :id
        """, nativeQuery = true)
    List<Comment> findCommentsByReviewId(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM comments cmt WHERE cmt.review_id = :id", nativeQuery = true)
    void deleteCommentsByReviewId(@Param("id") Long id);
}

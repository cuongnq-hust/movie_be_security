package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import security.example.security.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = """
            select * from comments cm where cm.newdto_id = :newId
             """
            , countQuery = """
             select count(cm.id) from comments cm
             where 1=1
             """
            , nativeQuery = true)
    List<Comment> findListCommentByNewId(Long newId);
}

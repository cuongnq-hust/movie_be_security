package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import security.example.security.model.Movie;
import security.example.security.model.NewDto;

import java.util.List;

@Repository
public interface NewRepository extends JpaRepository<NewDto, Long> {
    @Query(value = """
            select * from news new
             """
            , countQuery = """
             select count(new.id) from news new
             where 1=1
             """
            , nativeQuery = true)
    List<NewDto> findListNew();

    NewDto findNewDtoById(Long id);
}

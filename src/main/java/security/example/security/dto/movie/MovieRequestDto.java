package security.example.security.dto.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDto {
    private Long id;
    @JsonProperty("category_id")
    private Long categoryId;
    private String title;
    private String trailerLink;
    private String poster;
    private String avatar;
    private float price;
}

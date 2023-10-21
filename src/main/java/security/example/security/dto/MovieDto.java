package security.example.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private String title;
    private String trailerLink;
    private String poster;
    private String avatar;
    private float price;
    private Long category_id;
}

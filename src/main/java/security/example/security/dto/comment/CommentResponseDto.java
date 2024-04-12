package security.example.security.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String body;
    @JsonProperty("REVIEW_ID")
    private Long reviewId;
    private String createdDate;
    private String createdBy;
}

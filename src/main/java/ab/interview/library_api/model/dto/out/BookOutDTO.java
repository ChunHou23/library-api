package ab.interview.library_api.model.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookOutDTO {
    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "isbn_number")
    private String isbnNumber;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "author")
    private String author;

    @JsonProperty(value = "status")
    private Integer status;

    @JsonProperty(value = "created_at")
    private LocalDate createdAt;

    @JsonProperty(value = "updated_at")
    private LocalDate updatedAt;
}

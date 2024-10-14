package ab.interview.library_api.model.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddBookInDTO {
    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "author")
    private String author;

    @JsonProperty(value = "book_type")
    private Long bookType;

    @JsonProperty(value = "isbn_number")
    private String isbnNumber;
}

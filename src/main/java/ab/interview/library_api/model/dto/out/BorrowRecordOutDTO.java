package ab.interview.library_api.model.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowRecordOutDTO {
    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "book")
    private BookOutDTO book;

    @JsonProperty(value = "borrower")
    private BorrowerOutDTO borrower;

    @JsonProperty(value = "status")
    private Integer status;

    @JsonProperty(value = "created_at")
    private LocalDate createdAt;

    @JsonProperty(value = "updated_at")
    private LocalDate updatedAt;
}

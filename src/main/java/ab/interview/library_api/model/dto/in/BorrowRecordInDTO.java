package ab.interview.library_api.model.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BorrowRecordInDTO {
    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "resource_id")
    private Long resourceId;

    @JsonProperty(value = "borrower_id")
    private Long borrowerId;
}

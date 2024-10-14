package ab.interview.library_api.model.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowerOutDTO {
    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "status")
    private Integer status;

    @JsonProperty(value = "created_at")
    private LocalDate createdAt;

    @JsonProperty(value = "updated_at")
    private LocalDate updatedAt;
}
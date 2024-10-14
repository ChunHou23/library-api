package ab.interview.library_api.model.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddBorrowerInDTO {
    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;
}

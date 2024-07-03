package ag.develop.patient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class PatientDTO {
    @JsonProperty("PersonFirstName")
    private String personFirstName;
    @JsonProperty("PersonLastName")
    private String personLastName;
    @JsonProperty("PersonDOB")
    private String personDOB;

    public String toJSon() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

}

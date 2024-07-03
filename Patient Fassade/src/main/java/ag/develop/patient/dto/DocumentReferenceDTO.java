package ag.develop.patient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class DocumentReferenceDTO {

    @JsonProperty("kdlCode")
    private String kdlCode;
    @JsonProperty("patientId")
    private String patientId;
    @JsonProperty("visitNumber")
    private String visitNumber;
    @JsonProperty("dateCreated")
    private String dateCreated;
    @JsonProperty("contentB64")
    private String contentB64;

    public String toJSon() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}

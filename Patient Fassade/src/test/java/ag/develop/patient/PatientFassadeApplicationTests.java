package ag.develop.patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = PatientFassade.class)
@AutoConfigureMockMvc
class PatientFassadeApplicationTests {

	@Autowired
	private MockMvc mvc;

	// Erzeugt ein FhirContext-Objekt für FHIR R4
	private final FhirContext fhirContext = FhirContext.forR4();

	@Test
	public void createPatient()
			throws Exception {

        //Neue Patient Resource
		final Patient patient = new Patient();
		patient.getName().add(new HumanName().setFamily("bob"));

		// Instantiate neue JSON parser
		IParser parser = fhirContext.newJsonParser();

		// Serialize it
		String serialized = parser.encodeResourceToString(patient);
		mvc.perform(post("/patient").contentType(MediaType.APPLICATION_JSON).content(serialized));

	}

}



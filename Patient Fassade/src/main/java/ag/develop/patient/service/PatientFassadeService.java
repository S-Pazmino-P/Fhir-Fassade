package ag.develop.patient.service;

import ag.develop.patient.dto.PatientDTO;
import ag.develop.patient.utils.DateUtil;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service // Kennzeichnet diese Klasse als Spring Service-Komponente
public class PatientFassadeService {

    // Erzeugt ein FhirContext-Objekt für FHIR R4
    private final FhirContext fhirContext = FhirContext.forR4();

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private ProprietaryApiService proprietaryApiService;

    public Boolean processPatient(String patientResource) throws ParseException {
        PatientDTO patientDTO = new PatientDTO();

        // Erzeugt einen JSON-Parser für FHIR
        IParser parser = fhirContext.newJsonParser();
        // Parsen des Patient-Ressource-Strings in ein Patient-Objekt
        Patient patient = parser.parseResource(Patient.class, patientResource);

        // Extrahieren des Vornamens aus der Patient-Ressource
        patientDTO.setPersonFirstName(patient.getName().get(0).getGiven().stream()
                .map(namePart -> namePart.getValue())
                .collect(Collectors.joining(" ")));
        // Extrahieren des Nachnamens aus der Patient-Ressource
        patientDTO.setPersonLastName(patient.getName().get(0).getFamily());
        // Extrahieren des Geburtsdatums aus der Patient-Ressource
        String birthDate = patient.getBirthDateElement().getValueAsString();
        // Konvertierung des Geburtsdatums in das gewünschte Format
        birthDate = dateUtil.convertDate(birthDate);
        patientDTO.setPersonDOB(birthDate);

        return proprietaryApiService.sendPatientData(patientDTO);
    }

}

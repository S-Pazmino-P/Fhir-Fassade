
package ag.develop.patient.service;
import ag.develop.patient.dto.DocumentReferenceDTO;
import ag.develop.patient.dto.PatientDTO;
import ag.develop.patient.utils.DateUtil;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DocumentReference;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service // Kennzeichnet diese Klasse als Spring Service-Komponente
public class DocumentReferenceFassadeService {

    // Erzeugt ein FhirContext-Objekt für FHIR R4
    private final FhirContext fhirContext = FhirContext.forR4();

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private ProprietaryApiService proprietaryApiService;

    public Boolean processDocumentReference(String documentReferenceResource) throws ParseException {
        DocumentReferenceDTO documentReferenceDTO = new DocumentReferenceDTO();

        // Erzeugt einen JSON-Parser für FHIR
        IParser parser = fhirContext.newJsonParser();
        // Parsen des Patient-Ressource-Strings in ein Patient-Objekt
        DocumentReference documentReference = parser.parseResource(DocumentReference.class, documentReferenceResource);

        // Extrahieren des KDL code
        documentReference.getType().getCoding().stream()
                .filter(code -> "http://dvmd.de/fhir/CodeSystem/kdl"
                        .equals(code.getSystem()))
                .findAny()
                .ifPresent(kdl -> documentReferenceDTO.setKdlCode(kdl.getCode()));
        // Extrahieren des Patient ID aus der DocumentReference-Ressource
        documentReferenceDTO.setPatientId(documentReference.getSubject().getId());
        // Extrahieren des Abrechnungsfall-Nummer aus der DocumentReference-Ressource
        if (documentReference.getContext().getEncounter().get(0).hasId())
            documentReferenceDTO.setVisitNumber(documentReference.getContext().getEncounter().get(0).getId());
        // Extrahieren des Date aus der DocumentReference-Ressource
        if (documentReference.hasDate())
            documentReferenceDTO.setDateCreated(documentReference.getDate().toString());
        // Extrahieren des Content aus der DocumentReference-Ressource
        if (documentReference.hasContent())
            documentReferenceDTO.setContentB64(documentReference.getContent().toString());

        return proprietaryApiService.sendDocumentReferenceData(documentReferenceDTO);
    }


}
package ag.develop.patient.controller;

import ag.develop.patient.service.PatientFassadeService;
import ag.develop.patient.service.ProprietaryApiService;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController // Kennzeichnet diese Klasse als Spring REST Controller
@RequestMapping("/patient") // Basis-URL für alle Endpunkte in dieser Klasse
public class PatientFassadeController {

    private static final Logger logger = Logger.getLogger(PatientFassadeController.class.getName());

    //Patient Service
    @Autowired
    PatientFassadeService patientFassadeService;

    @PostMapping(produces = "application/json", consumes = "application/json")
    // Mapped HTTP POST-Anfragen auf diesen Endpunkt
    public ResponseEntity<String> createPatient(@RequestBody String patientResource) {
        try {
            // Loggt die erhaltene Anfrage
            logger.info("Received request to create patient");

            // Sendet die Patientendaten an die proprietäre API
            boolean apiSuccess = patientFassadeService.processPatient(patientResource);

            if (apiSuccess) {
                // Loggt und gibt eine Erfolgsantwort zurück, wenn die API-Anfrage erfolgreich war
                logger.info("Patient data sent successfully.");
                return ResponseEntity.status(HttpStatus.CREATED).body("Patient created successfully.");
            } else {
                // Loggt und gibt eine Fehlerantwort zurück, wenn die API-Anfrage fehlschlägt
                logger.severe("Failed to send patient data to proprietary API.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
            }
        } catch (ParseException e) {
            // Loggt und gibt eine Fehlerantwort zurück, wenn eine Ausnahme auftritt
            logger.log(Level.SEVERE, "Exception occurred while creating patient", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
        }
    }

}
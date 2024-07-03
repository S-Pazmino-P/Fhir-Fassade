package ag.develop.patient.service;

import ag.develop.patient.dto.PatientDTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service // Kennzeichnet diese Klasse als Spring Service-Komponente
public class ProprietaryApiService {

    private static final Logger logger = Logger.getLogger(ProprietaryApiService.class.getName());

    // Erzeugt ein RestTemplate-Objekt für HTTP-Anfragen
    private final RestTemplate restTemplate = new RestTemplate();

    // URL der proprietären API
    @Value("${url}")
    private String url;

     /**
     * Sendet Patientendaten an eine proprietäre API.
     *
     * @param patientDTO Das DTO des Patienten
     * @return true, wenn die API-Anfrage erfolgreich war; false, wenn ein Fehler aufgetreten ist
     */

    public boolean sendPatientData(PatientDTO patientDTO) {
        try {

            // Loggt die URL und den Anfragekörper
            logger.info("Sending request to proprietary API: " + url);

            // Sendet eine POST-Anfrage an die proprietäre API
            ResponseEntity<String> response = restTemplate.postForEntity(url, patientDTO.toJSon(), String.class);

            // Loggt den Statuscode der Antwort
            logger.info("Response from proprietary API: " + response.getStatusCode());
            HttpStatus statusCode = response.getStatusCode();

            // Akzeptiere sowohl 200 (OK) als auch 201 (Created) als erfolgreichen Status
            if (statusCode == HttpStatus.OK || statusCode == HttpStatus.CREATED) {
                return true;
            } else {
                // Loggt einen Fehler, wenn der Statuscode nicht 200 oder 201 ist
                logger.severe("Proprietary API returned an error: " + response.getBody());
                return false;
            }
        } catch (RestClientException e) {
            // Loggt eine Ausnahme, falls eine auftritt
            logger.log(Level.SEVERE, "Exception occurred while sending patient data", e);
            return false;
        }catch (JsonProcessingException e){
            // Loggt eine Ausnahme, falls eine auftritt
            logger.log(Level.SEVERE, "Exception occurred while serializing the data", e);
            return false;
        }
    }
}
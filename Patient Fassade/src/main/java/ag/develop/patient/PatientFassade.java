package ag.develop.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication // Kennzeichnet die Hauptklasse einer Spring Boot-Anwendung
public class PatientFassade {

	// Hauptmethode - der Einstiegspunkt der Spring Boot-Anwendung
	public static void main(String[] args) {
		 // Startet die Spring Boot-Anwendung
		SpringApplication.run(PatientFassade.class, args);
	}

}

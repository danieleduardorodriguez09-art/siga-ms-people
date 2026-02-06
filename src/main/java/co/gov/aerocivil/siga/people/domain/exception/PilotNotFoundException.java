package co.gov.aerocivil.siga.people.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Pilot not found")
public class PilotNotFoundException extends RuntimeException {
    public PilotNotFoundException(String licencia) {
        super("Pilot not found: " + licencia);
    }
}

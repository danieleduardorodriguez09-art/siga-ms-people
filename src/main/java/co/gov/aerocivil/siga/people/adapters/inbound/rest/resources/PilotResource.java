package co.gov.aerocivil.siga.people.adapters.inbound.rest.resources;

import co.gov.aerocivil.siga.commons.utils.ResponseEntityUtils;
import co.gov.aerocivil.siga.people.adapters.inbound.rest.dto.PilotResponse;
import co.gov.aerocivil.siga.people.adapters.inbound.rest.mapper.PilotResponseMapper;
import co.gov.aerocivil.siga.people.application.port.in.FindPilotUseCase;
import co.gov.aerocivil.siga.people.domain.model.person.Pilot;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pilots")
@RequiredArgsConstructor
public class PilotResource {

    private final FindPilotUseCase findPilotUseCase;
    private final PilotResponseMapper pilotResponseMapper;

    @Timed
    @Observed
    @GetMapping("/by-license/{licenseNumber}")
    public ResponseEntity<PilotResponse> findByLicense(@PathVariable String licenseNumber) {
        Pilot pilot = findPilotUseCase.findByLicense(licenseNumber);
        return ResponseEntityUtils.wrapOrNotFound(pilotResponseMapper.toDto(pilot));
    }
}

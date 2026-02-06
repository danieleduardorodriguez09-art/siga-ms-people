package co.gov.aerocivil.siga.people.application.service;

import co.gov.aerocivil.siga.people.application.port.in.FindPilotUseCase;
import co.gov.aerocivil.siga.people.application.port.out.data.PilotDataPort;
import co.gov.aerocivil.siga.people.domain.exception.PilotNotFoundException;
import co.gov.aerocivil.siga.people.domain.model.person.Pilot;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindPilotService implements FindPilotUseCase {

    private final PilotDataPort pilotDataPort;

    @Timed
    @Observed
    @Override
    public Pilot findByLicense(String licenseNumber) {
        return pilotDataPort.findByLicense(licenseNumber)
            .orElseThrow(() -> new PilotNotFoundException(licenseNumber));
    }
}

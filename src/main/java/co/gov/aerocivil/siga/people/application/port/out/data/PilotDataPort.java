package co.gov.aerocivil.siga.people.application.port.out.data;

import co.gov.aerocivil.siga.people.domain.model.person.Pilot;

import java.util.Optional;

public interface PilotDataPort {
    Optional<Pilot> findByLicense(String licenseNumber);
}

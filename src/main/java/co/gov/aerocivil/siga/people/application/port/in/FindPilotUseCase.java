package co.gov.aerocivil.siga.people.application.port.in;

import co.gov.aerocivil.siga.people.domain.model.person.Pilot;

public interface FindPilotUseCase {
    Pilot findByLicense(String licenseNumber);
}

package co.gov.aerocivil.siga.people.adapters.inbound.rest.mapper;

import co.gov.aerocivil.siga.people.adapters.inbound.rest.dto.PilotResponse;
import co.gov.aerocivil.siga.people.domain.model.person.Pilot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PilotResponseMapper {
    PilotResponse toDto(Pilot domain);
}

package co.gov.aerocivil.siga.people.adapters.inbound.rest.dto;

import co.gov.aerocivil.siga.core.data.constants.State;
import co.gov.aerocivil.siga.core.data.dto.type.TypeResponse;

public record UserResponse(
    Long id,
    String jobArea,
    String jobName,
    State state,
    PersonResponse person,
    TypeResponse userType
) {
}

package co.gov.aerocivil.siga.people.adapters.inbound.rest.mapper;

import co.gov.aerocivil.siga.people.adapters.inbound.rest.dto.PersonResponse;
import co.gov.aerocivil.siga.people.domain.model.person.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonResponseMapper {

    @Mapping(source = "documentType.code", target = "documentTypeCode")
    @Mapping(source = "documentType.approvedCode", target = "documentTypeApprovedCode")
    PersonResponse toDto(Person domain);

}

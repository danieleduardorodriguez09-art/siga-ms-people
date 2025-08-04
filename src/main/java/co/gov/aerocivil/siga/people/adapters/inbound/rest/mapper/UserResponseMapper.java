package co.gov.aerocivil.siga.people.adapters.inbound.rest.mapper;

import co.gov.aerocivil.siga.core.data.mapper.DomainDtoMapper;
import co.gov.aerocivil.siga.people.adapters.inbound.rest.dto.UserResponse;
import co.gov.aerocivil.siga.people.domain.model.person.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PersonResponseMapper.class})
public interface UserResponseMapper extends DomainDtoMapper<User, UserResponse> {
}

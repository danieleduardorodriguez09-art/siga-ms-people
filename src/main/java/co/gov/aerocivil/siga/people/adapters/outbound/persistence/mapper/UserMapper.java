package co.gov.aerocivil.siga.people.adapters.outbound.persistence.mapper;

import co.gov.aerocivil.siga.core.data.entity.person.UserEntity;
import co.gov.aerocivil.siga.people.domain.model.person.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TypeMapper.class, PersonMapper.class})
public interface UserMapper {

    User toDomain(UserEntity entity);

}

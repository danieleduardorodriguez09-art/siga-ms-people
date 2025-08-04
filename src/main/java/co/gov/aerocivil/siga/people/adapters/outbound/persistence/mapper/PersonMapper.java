package co.gov.aerocivil.siga.people.adapters.outbound.persistence.mapper;

import co.gov.aerocivil.siga.core.data.entity.person.PersonEntity;
import co.gov.aerocivil.siga.people.domain.model.person.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TypeMapper.class, CityMapper.class})
public interface PersonMapper {

    Person toDomain(PersonEntity entity);

}

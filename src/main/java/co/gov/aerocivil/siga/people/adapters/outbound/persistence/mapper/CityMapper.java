package co.gov.aerocivil.siga.people.adapters.outbound.persistence.mapper;

import co.gov.aerocivil.siga.core.data.entity.admin.CityEntity;
import co.gov.aerocivil.siga.core.data.entity.admin.CountryEntity;
import co.gov.aerocivil.siga.core.data.entity.admin.DepartmentEntity;
import co.gov.aerocivil.siga.people.domain.model.admin.City;
import co.gov.aerocivil.siga.people.domain.model.admin.Country;
import co.gov.aerocivil.siga.people.domain.model.admin.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    Country toDomain(CountryEntity entity);

    Department toDomain(DepartmentEntity entity);

    City toDomain(CityEntity entity);

}

package co.gov.aerocivil.siga.people.adapters.outbound.persistence.mapper;

import co.gov.aerocivil.siga.core.data.entity.type.BloodTypeEntity;
import co.gov.aerocivil.siga.core.data.entity.type.DocumentTypeEntity;
import co.gov.aerocivil.siga.core.data.entity.type.UserTypeEntity;
import co.gov.aerocivil.siga.people.domain.model.admin.DocumentType;
import co.gov.aerocivil.siga.people.domain.model.admin.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    Type toDomain(UserTypeEntity entity);

    Type toDomain(BloodTypeEntity entity);

    @Mapping(source = "approvedCode.code", target = "approvedCode")
    DocumentType toDomain(DocumentTypeEntity entity);

}

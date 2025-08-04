package co.gov.aerocivil.siga.people.adapters.outbound.persistence.repository;

import co.gov.aerocivil.siga.core.data.entity.person.PersonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonJpaRepository extends JpaRepositoryImplementation<PersonEntity, Long> {

    @Query("SELECT p FROM PersonEntity p JOIN p.documentType dt WHERE dt.approvedCode.code = :code AND p.documentNumber = :documentNumber")
    Optional<PersonEntity> findOneByIdentification(@Param("code") String code, @Param("documentNumber") String documentNumber);

}

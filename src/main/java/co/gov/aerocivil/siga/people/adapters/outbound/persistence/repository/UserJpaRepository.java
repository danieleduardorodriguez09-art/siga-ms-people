package co.gov.aerocivil.siga.people.adapters.outbound.persistence.repository;

import co.gov.aerocivil.siga.core.data.entity.person.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpaRepository extends JpaRepositoryImplementation<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u JOIN u.person p JOIN p.documentType dt " +
        "WHERE dt.approvedCode.code = :code AND p.documentNumber = :documentNumber")
    List<UserEntity> findAllByIdentification(@Param("code") String code, @Param("documentNumber") String documentNumber);

}

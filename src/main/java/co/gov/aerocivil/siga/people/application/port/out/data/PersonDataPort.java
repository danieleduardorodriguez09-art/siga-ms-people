package co.gov.aerocivil.siga.people.application.port.out.data;

import co.gov.aerocivil.siga.people.domain.model.person.Person;

import java.util.Optional;

public interface PersonDataPort {

    Optional<Person> findById(Long id);

    Optional<Person> findByIdentification(String code, String documentNumber);

}

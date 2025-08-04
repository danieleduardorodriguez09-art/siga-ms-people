package co.gov.aerocivil.siga.people.application.port.in;

import co.gov.aerocivil.siga.people.domain.model.person.Person;

import java.util.List;

public interface FindPersonUseCase {

    List<Person> findAll();

    Person findById(Long id);

    Person findByIdentification(String code, String documentNumber);

}

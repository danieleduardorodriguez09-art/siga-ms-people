package co.gov.aerocivil.siga.people.application.port.in;

import co.gov.aerocivil.siga.people.domain.model.person.User;

import java.util.List;

public interface FindUserUseCase {

    List<User> findByIdentification(String code, String documentNumber);

}

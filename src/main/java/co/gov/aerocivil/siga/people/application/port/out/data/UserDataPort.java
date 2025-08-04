package co.gov.aerocivil.siga.people.application.port.out.data;

import co.gov.aerocivil.siga.people.domain.model.person.User;

import java.util.List;

public interface UserDataPort {

    List<User> findByIdentification(String code, String documentNumber);

}

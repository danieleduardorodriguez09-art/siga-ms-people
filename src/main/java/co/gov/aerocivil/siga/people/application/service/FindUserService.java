package co.gov.aerocivil.siga.people.application.service;

import co.gov.aerocivil.siga.people.adapters.outbound.persistence.service.UserDataAdapter;
import co.gov.aerocivil.siga.people.application.port.in.FindUserUseCase;
import co.gov.aerocivil.siga.people.domain.model.person.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindUserService implements FindUserUseCase {

    private final UserDataAdapter userDataAdapter;

    @Override
    public List<User> findByIdentification(String code, String documentNumber) {
        return userDataAdapter.findByIdentification(code, documentNumber);
    }
}

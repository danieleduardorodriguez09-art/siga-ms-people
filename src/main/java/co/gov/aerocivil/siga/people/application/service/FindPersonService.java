package co.gov.aerocivil.siga.people.application.service;

import co.gov.aerocivil.siga.people.domain.exception.PersonNotFoundException;
import co.gov.aerocivil.siga.people.application.port.in.FindPersonUseCase;
import co.gov.aerocivil.siga.people.application.port.out.data.PersonDataPort;
import co.gov.aerocivil.siga.people.domain.model.person.Person;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindPersonService implements FindPersonUseCase {

    private final PersonDataPort personDataPort;

    @Override
    public List<Person> findAll() {
        return List.of();
    }

    @Timed
    @Observed
    @Override
    public Person findById(Long id) {
        return personDataPort.findById(id)
            .orElseThrow(() -> new PersonNotFoundException(null, String.valueOf(id)));
    }

    @Timed
    @Observed
    @Override
    public Person findByIdentification(String code, String documentNumber) {
        return personDataPort.findByIdentification(code, documentNumber)
            .orElseThrow(() -> new PersonNotFoundException(code, documentNumber));
    }
}

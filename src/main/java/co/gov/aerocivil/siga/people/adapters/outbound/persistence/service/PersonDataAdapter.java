package co.gov.aerocivil.siga.people.adapters.outbound.persistence.service;

import co.gov.aerocivil.siga.people.adapters.outbound.persistence.mapper.PersonMapper;
import co.gov.aerocivil.siga.people.adapters.outbound.persistence.repository.PersonJpaRepository;
import co.gov.aerocivil.siga.people.application.port.out.data.PersonDataPort;
import co.gov.aerocivil.siga.people.domain.model.person.Person;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonDataAdapter implements PersonDataPort {

    private final PersonMapper personMapper;

    private final PersonJpaRepository personJpaRepository;

    @Timed
    @Observed
    @Override
    public Optional<Person> findByIdentification(String code, String documentNumber) {
        return personJpaRepository.findOneByIdentification(code, documentNumber)
            .map(personMapper::toDomain);
    }

}

package co.gov.aerocivil.siga.people.adapters.outbound.persistence.service;

import co.gov.aerocivil.siga.people.adapters.outbound.persistence.mapper.UserMapper;
import co.gov.aerocivil.siga.people.adapters.outbound.persistence.repository.UserJpaRepository;
import co.gov.aerocivil.siga.people.application.port.out.data.UserDataPort;
import co.gov.aerocivil.siga.people.domain.model.person.User;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserDataAdapter implements UserDataPort {

    private final UserMapper userMapper;

    private final UserJpaRepository userJpaRepository;

    @Timed
    @Observed
    @Override
    public List<User> findByIdentification(String code, String documentNumber) {
        return userJpaRepository.findAllByIdentification(code, documentNumber)
            .stream()
            .map(userMapper::toDomain)
            .toList();
    }
}

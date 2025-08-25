package co.gov.aerocivil.siga.people.adapters.inbound.rest.resources;

import co.gov.aerocivil.siga.commons.utils.ResponseEntityUtils;
import co.gov.aerocivil.siga.people.adapters.inbound.rest.dto.PersonResponse;
import co.gov.aerocivil.siga.people.adapters.inbound.rest.dto.UserResponse;
import co.gov.aerocivil.siga.people.adapters.inbound.rest.mapper.PersonResponseMapper;
import co.gov.aerocivil.siga.people.adapters.inbound.rest.mapper.UserResponseMapper;
import co.gov.aerocivil.siga.people.application.port.in.FindPersonUseCase;
import co.gov.aerocivil.siga.people.application.port.in.FindUserUseCase;
import co.gov.aerocivil.siga.people.domain.model.person.Person;
import co.gov.aerocivil.siga.people.domain.model.person.User;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing Person-related operations, offering API endpoints
 * for person data retrieval based on specified criteria.
 * <p>
 * This class serves as a bridge between the frontend and backend, handling requests
 * and returning suitable responses. It uses necessary dependencies to perform
 * the delegated business logic and data transformation operations.
 * <p>
 * Features:
 * <ul>
 *     <li>Exposes endpoints related to Person resource.</li>
 *     <li>Logs initialization details of the resource.</li>
 *     <li>Supports operations timed and observed for monitoring purposes.</li>
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PersonResource {

    private static final String ENTITY_NAME = "person";

    private final UserResponseMapper userMapper;

    private final FindUserUseCase findUserUseCase;

    private final PersonResponseMapper personMapper;

    private final FindPersonUseCase findPersonUseCase;

    @PostConstruct
    public void init() {
        log.debug("PersonResource initialized");
    }

    @Timed
    @Observed
    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonResponse> findById(@PathVariable Long id) {
        Person person = findPersonUseCase.findById(id);
        return ResponseEntityUtils.wrapOrNotFound(personMapper.toDto(person));
    }

    @Timed
    @Observed
    @GetMapping(value = "/{documentTypeCode}/{documentNumber}")
    public ResponseEntity<PersonResponse> findByIdentification(@PathVariable String documentTypeCode, @PathVariable String documentNumber) {
        Person person = findPersonUseCase.findByIdentification(documentTypeCode, documentNumber);
        return ResponseEntityUtils.wrapOrNotFound(personMapper.toDto(person));
    }

    @Timed
    @Observed
    @GetMapping(value = "/{documentTypeCode}/{documentNumber}/users")
    public ResponseEntity<List<UserResponse>> findUserByIdentification(@PathVariable String documentTypeCode, @PathVariable String documentNumber) {
        List<User> users = findUserUseCase.findByIdentification(documentTypeCode, documentNumber);
        return ResponseEntity.ok(userMapper.toDto(users));
    }

}

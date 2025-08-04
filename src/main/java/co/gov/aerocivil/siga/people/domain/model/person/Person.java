package co.gov.aerocivil.siga.people.domain.model.person;

import co.gov.aerocivil.siga.core.data.constants.State;
import co.gov.aerocivil.siga.people.domain.model.admin.City;
import co.gov.aerocivil.siga.people.domain.model.admin.DocumentType;
import co.gov.aerocivil.siga.people.domain.model.admin.Type;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Comparable<Person> {

    private Long id;

    private String documentNumber;

    private DocumentType documentType;

    private String firstSurname;

    private String secondSurname;

    private String firstName;

    private String middleName;

    private String email;

    private String secondaryEmail;

    private String phoneNumber;

    private String mobilePhoneNumber;

    private String address;

    private LocalDate birthdate;

    private ZonedDateTime examDate;

    private Boolean rh;

    private String bloodType;

    private State state;

    private boolean digitalSignatureEnabled = Boolean.FALSE;

    private String certificateId;

    private City city;

    private Type bloodGroupType;

    @Override
    public int compareTo(@Nonnull Person o) {
        return ObjectUtils.compare(this.getId(), o.getId(), true);
    }
}

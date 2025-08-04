package co.gov.aerocivil.siga.people.adapters.inbound.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {

    private Long id;

    private String documentNumber;

    private String documentTypeCode;

    private String documentTypeApprovedCode;

    private String firstSurname;

    private String secondSurname;

    private String firstName;

    private String middleName;

    private String email;

    private String secondaryEmail;

    private String phoneNumber;

    private String mobilePhoneNumber;

    private String address;

}

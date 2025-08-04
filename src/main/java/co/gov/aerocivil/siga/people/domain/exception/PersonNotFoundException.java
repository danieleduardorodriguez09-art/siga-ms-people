package co.gov.aerocivil.siga.people.domain.exception;

import lombok.Getter;

/**
 * Exception thrown to indicate that a person could not be found based on the provided identification details.
 */
@Getter
public class PersonNotFoundException extends RuntimeException {

    /**
     * Email address associated with the person that could not be found.
     * This field may be null if an email address is not provided.
     */
    private final String email;

    /**
     * Represents the code that identifies the type of document used for the identification of the person.
     * This could correspond to various document categories such as a national ID, passport, or other forms of identification.
     */
    private final String documentTypeCode;

    /**
     * Represents the document number used to identify the person.
     * This is typically used in conjunction with the document type code
     * and may correspond to an identification number, passport number,
     * or other unique identifier associated with an official document.
     */
    private final String documentNumber;

    /**
     * Constructs a new PersonNotFoundException with the specified document type code
     * and document number, initializing the exception to indicate that a person could
     * not be found based on these details.
     *
     * @param documentTypeCode the code representing the type of document used for identification
     * @param documentNumber   the document number associated with the person
     */
    public PersonNotFoundException(String documentTypeCode, String documentNumber) {
        super("Person not found");
        this.email = null;
        this.documentTypeCode = documentTypeCode;
        this.documentNumber = documentNumber;
    }

    /**
     * Constructs a new PersonNotFoundException with the specified document type code,
     * document number, and email address, initializing the exception to indicate that
     * a person could not be found based on these details.
     *
     * @param documentTypeCode the code representing the type of document used for identification
     * @param documentNumber   the document number associated with the person
     * @param email            the email address associated with the person, which may be null if not provided
     */
    public PersonNotFoundException(String documentTypeCode, String documentNumber, String email) {
        super("Person not found");
        this.email = email;
        this.documentTypeCode = documentTypeCode;
        this.documentNumber = documentNumber;
    }
}

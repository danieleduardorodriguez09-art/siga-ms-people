package co.gov.aerocivil.siga.people.adapters.inbound.rest.dto;

/**
 * A record that defines the filtering criteria for searching persons.
 * <p>
 * This class provides parameters to filter persons based on their document attributes.
 * The filtering can be applied using the document number and the type of document code.
 * <p>
 * This is a compact and immutable representation of search criteria.
 * <p>
 * Fields:
 * <ul>
 *     <li>documentNumber: The unique identification number of the document.</li>
 *     <li>documentTypeCode: The code representing the type of document.</li>
 * </ul>
 */
public record PersonFilter(
    String documentNumber,
    String documentTypeCode
) {
}

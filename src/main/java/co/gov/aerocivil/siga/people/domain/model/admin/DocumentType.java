package co.gov.aerocivil.siga.people.domain.model.admin;

import co.gov.aerocivil.siga.core.data.constants.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Represents a specific type of document by extending the generic {@code Type} class.
 * In addition to the attributes inherited from {@code Type}, this class introduces an attribute
 * for managing additional identification details relevant to document types.
 * <p>
 * The {@code DocumentType} class is designed to be used in scenarios where differentiation
 * between various document classifications or categories is required.
 *
 * <ul>
 *     <li>{@code approvedCode}: A code used to signify approval status or classification
 *         specific to the document type. This field may serve as an additional identifier
 *         or as metadata for operational purposes.</li>
 * </ul>
 * <p>
 * This class also inherits attributes such as {@code id}, {@code code}, {@code name},
 * {@code state}, and {@code parentType}, along with supporting methods, from the
 * {@code Type} superclass.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DocumentType extends Type {

    /**
     * A code used to signify approval status or classification specific to the document type.
     * This field may be used as an additional identifier or as metadata to represent
     * the approval state or category of the document type in various operational contexts.
     */
    private String approvedCode;

    @Builder
    public DocumentType(Long id, String code, String name, State state, Type parentType, String approvedCode) {
        super(id, code, name, state, parentType);
        this.approvedCode = approvedCode;
    }

    @Override
    public String toString() {
        return StringUtils.trim(getName());
    }

}

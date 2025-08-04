package co.gov.aerocivil.siga.people.domain.model.admin;

import co.gov.aerocivil.siga.core.data.constants.State;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Represents a generic type entity with identification, descriptive, and hierarchical attributes.
 * This class also implements Comparable for comparing Type objects based on their ID.
 *
 * <ul>
 *     <li>{@code id}: Unique identifier for the type.</li>
 *     <li>{@code code}: A short code representation for the type.</li>
 *     <li>{@code name}: Descriptive name of the type.</li>
 *     <li>{@code state}: Represents the state of the type (e.g., active, inactive).</li>
 *     <li>{@code parentType}: References to another Type object acting as a parent in a hierarchy.</li>
 * </ul>
 * <p>
 * The class provides equality and hash code implementations based on the 'id' field.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Type implements Comparable<Type> {

    /**
     * Unique identifier for the type.
     * This field is primarily used to identify and differentiate instances of the Type class.
     */
    private Long id;

    /**
     * A short code representation for the type.
     * This code is typically used as an abbreviated or symbolic identifier
     * for the type in various contexts, such as UI or configuration settings.
     */
    private String code;

    /**
     * The descriptive name of the type.
     * This field provides a human-readable title or label associated with the type,
     * intended to offer clarity and meaning in contexts where the type is referenced.
     */
    private String name;

    /**
     * Represents the state of the type.
     * This field is used to indicate the current status or condition of the type (e.g., active, inactive).
     * The state can be utilized for operational decisions or filtering within the application logic.
     */
    private State state;

    /**
     * References another Type object acting as a parent in a hierarchical structure.
     * This field is used to establish a relationship between the current Type instance and its parent Type,
     * enabling representation of hierarchical relationships or classifications.
     */
    private Type parentType;

    /**
     * Compares this {@code Type} object with the specified object for order based on their IDs.
     * The comparison is case-aware and orders objects in ascending numerical order of their IDs.
     *
     * @param o the {@code Type} object to be compared. It must not be null.
     * @return a negative integer, zero, or a positive integer as this object's ID
     * is less than, equal to, or greater than the specified object's ID.
     */
    @Override
    public int compareTo(@Nonnull Type o) {
        return ObjectUtils.compare(this.getId(), o.getId(), true);
    }
}

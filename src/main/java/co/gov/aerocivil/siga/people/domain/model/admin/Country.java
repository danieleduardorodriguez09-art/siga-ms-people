package co.gov.aerocivil.siga.people.domain.model.admin;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Country implements Comparable<Country> {

    private Long id;

    private String code;

    private String description;

    @Override
    public int compareTo(@Nonnull Country o) {
        return ObjectUtils.compare(this.getId(), o.getId(), true);
    }
}

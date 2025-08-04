package co.gov.aerocivil.siga.people.domain.model.admin;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.ObjectUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"description"}, includeFieldNames = false)
public class City implements Comparable<City> {

    private Long id;

    private String code;

    private String description;

    private Department department;

    @Override
    public int compareTo(@Nonnull City o) {
        return ObjectUtils.compare(this.getId(), o.getId(), true);
    }
}

package co.gov.aerocivil.siga.people.domain.model.person;

import co.gov.aerocivil.siga.core.data.constants.State;
import co.gov.aerocivil.siga.people.domain.model.admin.Type;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Comparable<User> {

    private Long id;

    private String jobArea;

    private String jobName;

    private State state;

    private Person person;

    private Type userType;

    @Override
    public int compareTo(@Nonnull User o) {
        return ObjectUtils.compare(this.getId(), o.getId(), true);
    }
}

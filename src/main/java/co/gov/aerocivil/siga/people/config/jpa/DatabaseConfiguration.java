package co.gov.aerocivil.siga.people.config.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for setting up database properties and JPA repository scanning.
 * This class enables transaction management and configures entity scanning for the specified packages.
 * It serves as a centralized configuration for the persistence layer.
 * <p>
 * Features:
 * <ul>
 *     <li>Enables transaction management for managing database transactions.</li>
 *     <li>Scans specified packages for JPA entities.</li>
 *     <li>Configures JPA repositories in the defined packages.</li>
 * </ul>
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories({"co.gov.aerocivil.siga.people.adapters.outbound.persistence.repository"})
@EntityScan({"co.gov.aerocivil.siga.core.data.entity", "co.gov.aerocivil.siga.people.adapters.outbound.persistence.entity"})
public class DatabaseConfiguration {
}

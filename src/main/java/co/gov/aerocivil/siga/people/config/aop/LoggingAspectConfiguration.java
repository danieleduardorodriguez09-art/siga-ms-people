package co.gov.aerocivil.siga.people.config.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import static co.gov.aerocivil.siga.commons.constants.Constants.SPRING_PROFILE_DEVELOPMENT;
import static co.gov.aerocivil.siga.commons.constants.Constants.SPRING_PROFILE_PREPRODUCTION;

/**
 * Configuration class for setting up the logging aspect in the application.
 * This configuration enables AspectJ auto-proxying and registers a {@code LoggingAspect} bean
 * to be used for handling application logging concerns.
 * <p>
 * The {@code LoggingAspect} bean is only activated for specific Spring profiles
 * such as "development" and "preproduction" to facilitate logging during these
 * application lifecycle stages.
 * <p>
 * The logging aspect is designed to intercept and log application events,
 * such as method executions or other relevant behaviors, based on pointcuts
 * defined within the {@code LoggingAspect} class.
 */
@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    /**
     * Creates and configures a {@code LoggingAspect} bean for use within the application.
     * The bean is enabled only for specific Spring profiles such as "development" and "preproduction".
     * This aspect is used to handle cross-cutting concerns related to logging.
     *
     * @param env The {@code Environment} object providing access to application properties and configuration
     *            required to initialize the logging aspect.
     * @return A configured instance of {@code LoggingAspect} for use in the application.
     */
    @Bean
    @Profile({SPRING_PROFILE_DEVELOPMENT, SPRING_PROFILE_PREPRODUCTION})
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }

}

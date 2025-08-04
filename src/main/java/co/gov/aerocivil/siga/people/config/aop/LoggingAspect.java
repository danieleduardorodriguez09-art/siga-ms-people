package co.gov.aerocivil.siga.people.config.aop;

import co.gov.aerocivil.siga.boot.autoconfigure.aspect.AbstractLoggingAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.env.Environment;

/**
 * Aspect for logging purposes in the application.
 * This class extends {@code AbstractLoggingAspect} to inherit logging functionalities
 * and can be used to define additional logging behaviors using aspect-oriented programming.
 * <p>
 * This class is annotated with {@code @Aspect} to mark it as a Spring AOP Aspect.
 * <p>
 * It can be used to intercept application methods behavior for logging details such
 * as method execution, requests' processing, and any necessary logging configuration.
 */
@Aspect
public class LoggingAspect extends AbstractLoggingAspect {

    /**
     * Constructor for the {@code LoggingAspect}.
     * Initializes the aspect with required environment configuration for logging purposes.
     *
     * @param env The {@code Environment} object that provides access to application properties
     *            and configuration settings needed for logging behavior.
     */
    public LoggingAspect(Environment env) {
        super(env);
    }

    /**
     * Defines a Pointcut to target application package layers for aspect-oriented programming.
     * This method serves as a marker for specific packages within the application,
     * where cross-cutting concerns like logging, authentication, or monitoring may be applied.
     * <p>
     * It does not contain any implementation logic as Pointcuts act as selectors
     * for join points (specific points in the program execution).
     */
    @Override
    @Pointcut(
        "within(co.gov.aerocivil.siga.core..*)"
    )
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Defines a Pointcut to target specific packages in the application for applying
     * cross-cutting concerns such as logging, security, or monitoring.
     * <p>
     * The targeted packages include:
     * <ul>
     *     <li>Repository layer: `co.gov.aerocivil.siga.people.adapters.repository`</li>
     *     <li>REST layer: `co.gov.aerocivil.siga.people.adapters.rest`</li>
     *     <li>Service layer: `co.gov.aerocivil.siga.people.application.service`</li>
     * </ul>
     * <p>
     * This method does not contain any implementation logic as it only serves as a
     * selector for join points within the specified packages. The actual behavior to
     * be applied at these join points is defined in associated advice.
     */
    @Override
    @Pointcut(
        "within(co.gov.aerocivil.siga.people.domain..*)" +
            " || within(co.gov.aerocivil.siga.people.adapters..*)" +
            " || within(co.gov.aerocivil.siga.people.application..*)"
    )
    public void servicePackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }
}

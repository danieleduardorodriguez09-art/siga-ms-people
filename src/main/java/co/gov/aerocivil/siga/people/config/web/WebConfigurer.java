package co.gov.aerocivil.siga.people.config.web;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * The WebConfigurer class is a configuration class that implements the
 * ServletContextInitializer interface. It is responsible for customizing the
 * initialization of the ServletContext during the startup of the web application.
 * <p>
 * This class logs the active profiles being used by the application, if any, as
 * well as a confirmation message once the application is fully configured.
 * <p>
 * Dependencies:
 * - Environment: Used to retrieve the active profiles of the application.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfigurer implements ServletContextInitializer {

    /**
     * Represents the current application environment and provides access to
     * environment properties, such as profile information and configuration sources.
     * This variable is used to retrieve active profiles of the application during
     * the servlet context initialization process.
     */
    private final Environment env;

    /**
     * Customizes the initialization of the {@link ServletContext} during the startup
     * of the web application. This method logs the active profiles being used by
     * the application, if any, and a confirmation message when the application is
     * fully configured.
     *
     * @param servletContext the {@link ServletContext} to be initialized, allowing
     *                       for configuration of servlets, filters, and other
     *                       initialization parameters necessary for the application
     *                       startup.
     */
    @Override
    public void onStartup(ServletContext servletContext) {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }
        log.info("Web application fully configured");
    }

}

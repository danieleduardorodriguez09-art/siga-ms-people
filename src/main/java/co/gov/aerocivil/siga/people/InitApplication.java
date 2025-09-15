package co.gov.aerocivil.siga.people;

import co.gov.aerocivil.siga.commons.config.DefaultProfileUtil;
import co.gov.aerocivil.siga.commons.constants.Constants;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Entry point for the application initialization and configuration.
 * This class contains the main method to bootstrap the Spring Boot application and manages application-specific initialization and logging.
 */
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class InitApplication {

    /**
     * The Spring Environment abstraction provides access to the application's environment properties,
     * profile-related information, and configuration settings.
     * This variable is used to manage active profiles, retrieve properties, and handle application
     * environment-specific configurations.
     */
    private final Environment environment;

    /**
     * The main method serves as the entry point for running the Spring Boot application.
     * It initializes the application, sets a default profile, and starts the application context.
     * Additionally, it logs application startup information, including the active profiles.
     *
     * @param args command-line arguments passed during the application startup. These arguments
     *             can be used to configure application properties or specify active profiles.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(InitApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    /**
     * Logs application startup information, including the application name and active profiles.
     * This method is used during the application initialization process to provide
     * contextual information about the current environment configuration.
     *
     * @param env the Spring Environment object containing configuration properties and profiles.
     *            It is used to retrieve the application name and active profiles.
     */
    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("----------------------------------------------------------");
        log.info("Application '{}' is running!:", env.getProperty("spring.application.name"));
        log.info("Profile(s): \t{}", Arrays.toString(env.getActiveProfiles()));
        log.info("Local: \t\t{}://localhost:{}{}\t", protocol, serverPort, contextPath);
        log.info("External:\t\t{}://{}:{}{}\t", protocol, hostAddress, serverPort, contextPath);
        log.info("----------------------------------------------------------");
    }

    /**
     * Initializes the application after the Spring context has been constructed.
     * <p>
     * This method performs a validation of active Spring profiles by checking the
     * configuration of development, production, and cloud profiles. It ensures
     * that mutually incompatible profiles, such as 'dev' and 'prod' or 'dev' and 'cloud',
     * are not active simultaneously. If such a misconfiguration is detected, an
     * error message is logged to help diagnose and resolve the issue.
     * <p>
     * The method is annotated with {@code @PostConstruct}, indicating that it will
     * be executed automatically after the dependency injection is complete and the
     * application is fully initialized.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }
}

package co.gov.aerocivil.siga.people.adapters.inbound.rest.problem;

import co.gov.aerocivil.siga.commons.constants.Constants;
import co.gov.aerocivil.siga.commons.constants.ErrorConstants;
import co.gov.aerocivil.siga.commons.error.InvalidArgumentsException;
import co.gov.aerocivil.siga.commons.utils.HeaderUtils;
import co.gov.aerocivil.siga.core.problem.FieldErrorVM;
import co.gov.aerocivil.siga.core.problem.ProblemDetailWithCause;
import co.gov.aerocivil.siga.core.problem.exception.BadRequestAlertException;
import co.gov.aerocivil.siga.core.problem.exception.PersonNotFoundAlertException;
import co.gov.aerocivil.siga.people.domain.exception.PersonNotFoundException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

/**
 * Global exception handler that translates exceptions into structured error responses.
 * This class extends {@link ResponseEntityExceptionHandler} and provides additional
 * functionality to handle and customize exceptions, enabling the production of
 * precise and meaningful error responses for REST APIs.
 * <p>
 * Key features:
 * <ul>
 *     <li>Customization of exception responses based on exception types and properties.</li>
 *     <li>Conversion of exceptions into {@link ProblemDetailWithCause}, representing
 *     structured problem detail objects.</li>
 *     <li>Dynamically adjusts error titles, status codes, messages, and additional properties
 *     to create a user-friendly and standardized error structure.</li>
 * </ul>
 * <p>
 * Annotations:
 * <ul>
 *     <li>{@code @Slf4j}: Enables logging within the class.</li>
 *     <li>{@code @ControllerAdvice}: Marks the class as a global exception handler for Spring.</li>
 *     <li>{@code @RequiredArgsConstructor}: Automatically generates a constructor for fields marked
 *     {@code final} or {@code @NonNull}.</li>
 * </ul>
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    private static final String FIELD_ERRORS_KEY = "fieldErrors";
    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final boolean CASUAL_CHAIN_ENABLED = Boolean.FALSE;

    private final Environment environment;

    @Value("${siga.clientApp.name}")
    private String applicationName;

    @ExceptionHandler
    public ResponseEntity<Object> handleAnyException(Throwable ex, NativeWebRequest request) {
        ProblemDetailWithCause cause = wrapAndCustomizeProblem(ex, request);
        return handleExceptionInternal((Exception) ex, cause, buildHeaders(ex), HttpStatusCode.valueOf(cause.getStatus()), request);
    }

    @Nullable
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
        @Nonnull Exception ex,
        @Nullable Object body,
        @Nonnull HttpHeaders headers,
        @Nonnull HttpStatusCode statusCode,
        @Nonnull WebRequest request
    ) {
        body = body == null ? wrapAndCustomizeProblem(ex, (NativeWebRequest) request) : body;
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private ProblemDetailWithCause wrapAndCustomizeProblem(Throwable throwable, NativeWebRequest request) {
        return customizeProblem(getProblemDetailWithCause(throwable), throwable, request);
    }

    private ProblemDetailWithCause getProblemDetailWithCause(Throwable throwable) {
        if (throwable instanceof ErrorResponseException exp && exp.getBody() instanceof ProblemDetailWithCause problemDetailWithCause) {
            return problemDetailWithCause;
        }
        if (throwable instanceof InvalidArgumentsException ex) {
            return (ProblemDetailWithCause) new BadRequestAlertException(ex.getMessage(), ex.getEntityName(), ex.getErrorKey()).getBody();
        }
        ProblemDetailWithCause checkRequestProblems = getProblemDetailWithCauseForCheckRequest(throwable);
        return Objects.requireNonNullElseGet(checkRequestProblems, () -> ProblemDetailWithCause.builderForStatus(toStatus(throwable)).build());
    }

    private ProblemDetailWithCause getProblemDetailWithCauseForCheckRequest(Throwable throwable) {
        if (throwable instanceof PersonNotFoundException ex) {
            return (ProblemDetailWithCause) new PersonNotFoundAlertException(ex.getDocumentTypeCode(), ex.getDocumentNumber()).getBody();
        }
        return null;
    }

    private ProblemDetailWithCause customizeProblem(ProblemDetailWithCause problem, Throwable err, NativeWebRequest request) {
        if (problem.getStatus() <= 0) {
            problem.setStatus(toStatus(err));
        }

        if (ErrorConstants.BLANK_TYPE.equals(problem.getType())) {
            problem.setType(getMappedType(err));
        }

        String title = extractTitle(err, problem.getStatus());
        String problemTitle = problem.getTitle();
        if (problemTitle == null || !problemTitle.equals(title)) {
            problem.setTitle(title);
        }

        if (problem.getDetail() == null) {
            problem.setDetail(getCustomizedErrorDetails(err));
        }

        Map<String, Object> problemProperties = problem.getProperties();
        if (problemProperties == null || !problemProperties.containsKey(MESSAGE_KEY)) {
            problem.setProperty(MESSAGE_KEY, StringUtils.defaultIfBlank(getMappedMessageKey(err), "error.http." + problem.getStatus()));
        }
        if (problemProperties == null || !problemProperties.containsKey(PATH_KEY)) {
            problem.setProperty(PATH_KEY, getPathValue(request));
        }

        if ((err instanceof MethodArgumentNotValidException fieldException) &&
            (problemProperties == null || !problemProperties.containsKey(FIELD_ERRORS_KEY))) {
            problem.setProperty(FIELD_ERRORS_KEY, getFieldErrors(fieldException));
        }

        problem.setCause(buildCause(err.getCause(), request).orElse(null));

        return problem;
    }

    private HttpHeaders buildHeaders(Throwable throwable) {
        return throwable instanceof BadRequestAlertException badRequestAlertException ?
            HeaderUtils.createFailureAlert(
                applicationName,
                Boolean.TRUE,
                badRequestAlertException.getEntityName(),
                badRequestAlertException.getErrorKey(),
                badRequestAlertException.getMessage()
            )
            : null;
    }

    private HttpStatus toStatus(final Throwable throwable) {
        if (throwable instanceof ErrorResponse err) {
            return HttpStatus.valueOf(err.getBody().getStatus());
        }
        return Optional.ofNullable(getMappedStatus(throwable))
            .orElse(
                Optional.ofNullable(resolveResponseStatus(throwable))
                    .map(ResponseStatus::value)
                    .orElse(HttpStatus.INTERNAL_SERVER_ERROR)
            );
    }

    private HttpStatus getMappedStatus(Throwable throwable) {
        if (throwable instanceof AuthorizationDeniedException) {
            return HttpStatus.FORBIDDEN;
        }
        if (throwable instanceof AccessDeniedException) {
            return HttpStatus.FORBIDDEN;
        }
        if (throwable instanceof ConcurrencyFailureException) {
            return HttpStatus.CONFLICT;
        }
        if (throwable instanceof BadCredentialsException) {
            return HttpStatus.UNAUTHORIZED;
        }
        return null;
    }

    private ResponseStatus resolveResponseStatus(final Throwable throwable) {
        final ResponseStatus candidate = findMergedAnnotation(throwable.getClass(), ResponseStatus.class);
        return candidate == null && throwable.getCause() != null ? resolveResponseStatus(throwable.getCause()) : candidate;
    }

    private URI getMappedType(Throwable throwable) {
        if (throwable instanceof MethodArgumentNotValidException) {
            return ErrorConstants.CONSTRAINT_VIOLATION_TYPE;
        }
        return ErrorConstants.DEFAULT_TYPE;
    }

    private String extractTitle(Throwable throwable, int statusCode) {
        String customizedTitle = getCustomizedTitle(throwable);
        return StringUtils.defaultIfBlank(customizedTitle, extractTitleForResponseStatus(throwable, statusCode));
    }

    private String getCustomizedTitle(Throwable throwable) {
        if (throwable instanceof MethodArgumentNotValidException) {
            return "Method argument not valid";
        }
        return null;
    }

    private String extractTitleForResponseStatus(Throwable throwable, int statusCode) {
        ResponseStatus specialStatus = resolveResponseStatus(throwable);
        return specialStatus == null ? HttpStatus.valueOf(statusCode).getReasonPhrase() : specialStatus.reason();
    }

    private String getCustomizedErrorDetails(Throwable throwable) {
        Collection<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
        if (activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
            if (throwable instanceof HttpMessageConversionException) {
                return "Unable to convert http message.";
            }
            if (throwable instanceof DataAccessException) {
                return "Failure during jpa access.";
            }
            if (containsPackageName(throwable.getMessage())) {
                return "Unexpected runtime exception.";
            }
        }
        return throwable.getCause() != null ? throwable.getCause().getMessage() : throwable.getMessage();
    }

    private String getMappedMessageKey(Throwable throwable) {
        if (throwable instanceof MethodArgumentNotValidException) {
            return ErrorConstants.ERR_VALIDATION;
        } else if (throwable instanceof ConcurrencyFailureException || throwable.getCause() instanceof ConcurrencyFailureException) {
            return ErrorConstants.ERR_CONCURRENCY_FAILURE;
        }
        return null;
    }

    private URI getPathValue(NativeWebRequest request) {
        if (request == null) {
            return ErrorConstants.BLANK_TYPE;
        }
        return URI.create(extractURI(request));
    }

    private String extractURI(NativeWebRequest request) {
        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);
        return servletRequest != null ? servletRequest.getRequestURI() : StringUtils.EMPTY;
    }

    private List<FieldErrorVM> getFieldErrors(MethodArgumentNotValidException exception) {
        return exception
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .map(field -> new FieldErrorVM(
                field.getObjectName().replaceFirst("DTO$", StringUtils.EMPTY),
                field.getField(),
                StringUtils.defaultIfBlank(field.getDefaultMessage(), field.getCode())
            ))
            .toList();
    }

    private Optional<ProblemDetailWithCause> buildCause(final Throwable throwable, NativeWebRequest request) {
        if (throwable != null && isCasualChainEnabled()) {
            return Optional.of(customizeProblem(getProblemDetailWithCause(throwable), throwable, request));
        }
        return Optional.empty();
    }

    private boolean isCasualChainEnabled() {
        return CASUAL_CHAIN_ENABLED;
    }

    private boolean containsPackageName(String message) {
        return Strings.CI.containsAny(
            message,
            "org.",
            "java.",
            "net.",
            "jakarta.",
            "javax.",
            "com.",
            "io.",
            "de.",
            "co.gov.aerocivil."
        );
    }

}

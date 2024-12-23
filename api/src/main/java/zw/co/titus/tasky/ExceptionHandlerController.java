package zw.co.titus.tasky;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import zw.co.titus.tasky.exceptions.InvalidArgumentException;
import zw.co.titus.tasky.exceptions.RecordNotFoundException;

import java.util.stream.Collectors;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class ExceptionHandlerController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    Error recordNotFoundError(RecordNotFoundException e) {
        LOGGER.info("Record not found error: {}", e.getMessage());
        return Error.of(404, e.getMessage());
    }

    @ExceptionHandler(InvalidArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Error invalidArgumentError(InvalidArgumentException e) {
        LOGGER.info("Invalid Argument exception: {}", e.getMessage());
        return Error.of(400, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Error serviceCommunicationException(BadCredentialsException e) {
        LOGGER.info("Invalid Argument error: {}", e.getMessage());
        return Error.of(400, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Error handleConstraintViolationException(ConstraintViolationException e) {
        String validationErrors = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        LOGGER.error("Validation failed: {}", validationErrors);
        return Error.of(400, validationErrors);
    }
}
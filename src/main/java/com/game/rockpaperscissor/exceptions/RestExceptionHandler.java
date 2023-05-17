package com.game.rockpaperscissor.exceptions;

import com.game.rockpaperscissor.models.Error;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleUnknownException(Exception exception, WebRequest request) {
        Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went Wrong!!!", exception.getMessage());
        return buildResponse(error);
    }

    // No Controller Found
    @Override
    protected ResponseEntity <Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Error error = new Error(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getRequestURL());
        return buildResponse(error);
    }

    // Game Not found handler
    @ExceptionHandler(GameNotFoundException.class)
    protected ResponseEntity<Object> handleGameNotFoundException(GameNotFoundException ex) {
        Error error = new Error(HttpStatus.NOT_FOUND, "No Game Found", ex.getMessage());
        return buildResponse(error);
    }

    // Invalid parameter provided
    @ExceptionHandler(GameOverException.class)
    public ResponseEntity<Object> handleGameOverException(GameOverException ex) {
        List<String> errors = List.of(ex.getMessage(), "Link: /start", "Results: /{token}/results");
        Error error = new Error(HttpStatus.BAD_REQUEST, "Game Over", errors);
        return buildResponse(error);
    }

    private ResponseEntity<Object> buildResponse(Error error) {
        return new ResponseEntity<>(error, error.getStatus());
    }

}

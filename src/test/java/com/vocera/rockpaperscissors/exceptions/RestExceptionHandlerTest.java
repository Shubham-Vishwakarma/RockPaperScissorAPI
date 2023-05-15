package com.vocera.rockpaperscissors.exceptions;

import com.vocera.rockpaperscissors.models.Error;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestExceptionHandlerTest {

    RestExceptionHandler handler;

    @BeforeAll
    void setUp() {
        handler = new RestExceptionHandler();
    }

    @Test
    void handleUnknownExceptionTest() {
        WebRequest request = Mockito.mock(WebRequest.class);
        ResponseEntity<Object> responseEntity = handler.handleUnknownException(new RuntimeException("Test Exception"), request);
        Error body = (Error) responseEntity.getBody();
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, body.getStatus());
        Assertions.assertEquals("Something Went Wrong!!!", body.getMessage());
        Assertions.assertEquals("Test Exception", body.getErrors().get(0));
    }

    @Test
    void handleNoHandlerFoundExceptionTest() {
        WebRequest request = Mockito.mock(WebRequest.class);
        NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/invalidUrl", new HttpHeaders());
        ResponseEntity<Object> responseEntity = handler.handleNoHandlerFoundException(ex, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

        Error body = (Error) responseEntity.getBody();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, body.getStatus());
        Assertions.assertEquals("No endpoint GET /invalidUrl.", body.getMessage());
        Assertions.assertEquals("/invalidUrl", body.getErrors().get(0));
    }

    @Test
    void handleGameNotFoundExceptionTest() throws Exception {
        ResponseEntity<Object> responseEntity = handler.handleGameNotFoundException(new GameNotFoundException("Cannot find game with given token"));

        Error body = (Error) responseEntity.getBody();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, body.getStatus());
        Assertions.assertEquals("No Game Found", body.getMessage());
        Assertions.assertEquals("Cannot find game with given token", body.getErrors().get(0));
    }

    @Test
    void handleGameOverExceptionTest() throws Exception {
        ResponseEntity<Object> responseEntity = handler.handleGameOverException(new GameOverException("Game Over"));

        Error body = (Error) responseEntity.getBody();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, body.getStatus());
        Assertions.assertEquals("Game Over", body.getMessage());
        Assertions.assertEquals("Game Over", body.getErrors().get(0));
    }

    @AfterAll
    void tearDown(){
        handler = null;
    }
}
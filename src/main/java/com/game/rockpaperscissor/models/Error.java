package com.game.rockpaperscissor.models;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class Error {

    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public Error(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public Error(HttpStatus status, String message, String errors) {
        this.status = status;
        this.message = message;
        this.errors = List.of(errors);
    }

}

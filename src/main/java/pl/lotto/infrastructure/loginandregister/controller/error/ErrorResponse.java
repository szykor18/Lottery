package pl.lotto.infrastructure.loginandregister.controller.error;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus status) {
}

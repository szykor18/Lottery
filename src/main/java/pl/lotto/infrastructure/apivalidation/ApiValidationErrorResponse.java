package pl.lotto.infrastructure.apivalidation;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ApiValidationErrorResponse(List<String> validationMessages, HttpStatus status) {
}

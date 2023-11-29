package pl.lotto.domain.loginandregister.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(
        @NotBlank(message = "{username.not.blank}")
        String username,
        @NotBlank(message = "{password.not.blank}")
        String password) {
}

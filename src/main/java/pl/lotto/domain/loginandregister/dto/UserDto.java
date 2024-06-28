package pl.lotto.domain.loginandregister.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserDto(String id, String username, String password, List<String> roles) {
}

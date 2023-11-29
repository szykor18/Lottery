package pl.lotto.infrastructure.loginandregister.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.domain.loginandregister.LoginAndRegisterFacade;
import pl.lotto.domain.loginandregister.dto.RegisterRequestDto;
import pl.lotto.domain.loginandregister.dto.RegisterResultDto;

@RestController
@AllArgsConstructor
public class RegisterRestController {
    private final LoginAndRegisterFacade loginAndRegisterFacade;

    @PostMapping
    public ResponseEntity<RegisterResultDto> registerUser(RegisterRequestDto registerRequest) {

        RegisterResultDto registerResult = loginAndRegisterFacade.registerUser(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResult);
    }
}

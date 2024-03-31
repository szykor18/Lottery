package pl.lotto.domain.loginandregister;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import pl.lotto.domain.loginandregister.dto.RegisterRequestDto;
import pl.lotto.domain.loginandregister.dto.RegisterResultDto;
import pl.lotto.domain.loginandregister.dto.UserDto;

@AllArgsConstructor
public class LoginAndRegisterFacade {
    private final UserRepository userRepository;

    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::mapFromUserToUserDto)
                .orElseThrow(() -> new BadCredentialsException(username));
    }

    public RegisterResultDto registerUser(RegisterRequestDto registerRequestDto) {
        boolean isUserWithSameUsername = userRepository.findByUsername(registerRequestDto.username()).isPresent();
        if (isUserWithSameUsername) {
            throw new RuntimeException("User with username: '" + registerRequestDto.username() + "' already exists.");
        }
        User userToSave = UserMapper.mapFromRegisterRequestToUser(registerRequestDto);
        User savedUser = userRepository.save(userToSave);
        return RegisterResultDto.builder()
                .id(savedUser.id())
                .username(savedUser.username())
                .isCreated(true)
                .build();
    }
}

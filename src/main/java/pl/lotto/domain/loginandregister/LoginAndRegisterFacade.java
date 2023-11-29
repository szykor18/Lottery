package pl.lotto.domain.loginandregister;

import lombok.AllArgsConstructor;
import pl.lotto.domain.loginandregister.dto.RegisterRequestDto;
import pl.lotto.domain.loginandregister.dto.RegisterResultDto;
import pl.lotto.domain.loginandregister.dto.UserDto;

@AllArgsConstructor
public class LoginAndRegisterFacade {
    private final UserRepository userRepository;

    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::mapFromUserToUserDto)
                .orElseThrow(() -> new RuntimeException("username not found"));
    }

    public RegisterResultDto registerUser(RegisterRequestDto registerRequestDto) {
        User userToSave = UserMapper.mapFromRegisterRequestToUser(registerRequestDto);
        User savedUser = userRepository.save(userToSave);
        return RegisterResultDto.builder()
                .id(savedUser.id())
                .username(savedUser.username())
                .isCreated(true)
                .build();
    }
}

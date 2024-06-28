package pl.lotto.domain.loginandregister;

import pl.lotto.domain.loginandregister.dto.RegisterRequestDto;
import pl.lotto.domain.loginandregister.dto.UserDto;
import pl.lotto.persistence.model.User;

class UserMapper {
    static UserDto mapFromUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.id())
                .username(user.username())
                .password(user.password())
                .build();
    }
    static User mapFromRegisterRequestToUser(RegisterRequestDto registerRequestDto) {
        return User.builder().username(registerRequestDto.username()).password(registerRequestDto.password()).build();
    }
}

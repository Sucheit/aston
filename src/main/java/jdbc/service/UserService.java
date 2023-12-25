package jdbc.service;

import jdbc.dto.UserRequestDto;
import jdbc.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getUsers();

    UserResponseDto getUserById(Integer userId);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(Integer userId, UserRequestDto userRequestDto);

    boolean deleteUser(Integer userId);
}

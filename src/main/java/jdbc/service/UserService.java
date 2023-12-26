package jdbc.service;

import jdbc.dto.GroupResponseDto;
import jdbc.dto.UserRequestDto;
import jdbc.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getUsers();

    UserResponseDto getUserById(Integer userId);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(Integer userId, UserRequestDto userRequestDto);

    boolean deleteUser(Integer userId);

    List<GroupResponseDto> getUserGroups(Integer userId);

    boolean addUserToGroup(Integer userId, Integer groupId);

    boolean deleteUserFromGroup(Integer userId, Integer groupId);
}

package spring.service;

import spring.dto.GroupResponseDto;
import spring.dto.UserRequestDto;
import spring.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getUsers();

    UserResponseDto getUserById(Integer userId);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(Integer userId, UserRequestDto userRequestDto);

    void deleteUser(Integer userId);

    List<GroupResponseDto> getUserGroups(Integer userId);

    List<GroupResponseDto> addUserToGroup(Integer userId, Integer groupId);

    List<GroupResponseDto> deleteUserFromGroup(Integer userId, Integer groupId);
}

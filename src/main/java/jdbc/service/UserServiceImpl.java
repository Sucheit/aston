package jdbc.service;

import jdbc.dao.UserDao;
import jdbc.dto.UserRequestDto;
import jdbc.dto.UserResponseDto;
import jdbc.exceptions.NotFoundException;
import jdbc.mappers.UserMapper;
import jdbc.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    UserDao userDao;

    UserMapper userMapper;

    @Override
    public List<UserResponseDto> getUsers() {
        return userMapper.userListToUserResponseDtoList(userDao.getUsers());
    }

    @Override
    public UserResponseDto getUserById(Integer userId) {
        User user = userDao.getUserById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userDao.createUser(userRequestDto)
                .orElseThrow(() -> new NotFoundException("User was not created."));
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public UserResponseDto updateUser(Integer userId, UserRequestDto userRequestDto) {
        userDao.getUserById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        User user = userDao.updateUser(userId, userRequestDto)
                .orElseThrow(() -> new NotFoundException("User was not updated."));
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public boolean deleteUser(Integer userId) {
        userDao.getUserById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        return userDao.deleteUser(userId);
    }
}

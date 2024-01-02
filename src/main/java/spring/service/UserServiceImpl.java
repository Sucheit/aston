package spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.GroupResponseDto;
import spring.dto.UserRequestDto;
import spring.dto.UserResponseDto;
import spring.error.NotFoundException;
import spring.mappers.GroupMapper;
import spring.mappers.UserMapper;
import spring.model.Group;
import spring.model.User;
import spring.repository.GroupRepository;
import spring.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final GroupMapper groupMapper;

    private final GroupRepository groupRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, GroupMapper groupMapper, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.groupMapper = groupMapper;
        this.groupRepository = groupRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> getUsers() {
        return userMapper.userListToUserResponseDtoList(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDto getUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        return userMapper.userToUserResponseDto(user);
    }

    @Transactional
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setFirstName(userRequestDto.firstName());
        user.setLastName(userRequestDto.lastName());
        user = userRepository.save(user);
        return userMapper.userToUserResponseDto(user);
    }

    @Transactional
    @Override
    public UserResponseDto updateUser(Integer userId, UserRequestDto userRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        user.setFirstName(userRequestDto.firstName());
        user.setLastName(userRequestDto.lastName());
        userRepository.save(user);
        return userMapper.userToUserResponseDto(user);
    }

    @Transactional
    @Override
    public void deleteUser(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupResponseDto> getUserGroups(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        Set<Group> groups = user.getGroups();
        return groupMapper.groupSetToGroupResponseDtoList(groups);
    }

    @Transactional
    @Override
    public List<GroupResponseDto> addUserToGroup(Integer userId, Integer groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        Set<Group> groups = user.getGroups();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        groups.add(group);
        userRepository.save(user);
        return getUserGroups(userId);
    }

    @Transactional
    @Override
    public List<GroupResponseDto> deleteUserFromGroup(Integer userId, Integer groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        Set<Group> groups = user.getGroups();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        groups.remove(group);
        userRepository.save(user);
        return getUserGroups(userId);
    }
}

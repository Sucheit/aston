package spring.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.GroupRequestDto;
import spring.dto.GroupResponseDto;
import spring.dto.UserResponseDto;
import spring.error.NotFoundException;
import spring.mappers.GroupMapper;
import spring.mappers.UserMapper;
import spring.model.Group;
import spring.model.User;
import spring.repository.GroupRepository;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;

    private final UserMapper userMapper;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper, UserMapper userMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupResponseDto> getGroups() {
        return groupMapper.groupListToGroupResponseDtoList(groupRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public GroupResponseDto getGroupById(Integer groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        return groupMapper.groupToGroupResponseDto(group);
    }

    @Transactional
    @Override
    public GroupResponseDto createGroup(GroupRequestDto groupRequestDto) {
        Group group = new Group();
        group.setName(groupRequestDto.name());
        group.setDescription(groupRequestDto.description());
        group = groupRepository.save(group);
        return groupMapper.groupToGroupResponseDto(group);
    }

    @Transactional
    @Override
    public GroupResponseDto updateGroup(Integer groupId, GroupRequestDto groupRequestDto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        group.setName(groupRequestDto.name());
        group.setDescription(groupRequestDto.description());
        groupRepository.save(group);
        return groupMapper.groupToGroupResponseDto(group);
    }

    @Transactional
    @Override
    public void deleteGroup(Integer groupId) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        groupRepository.deleteById(groupId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> getGroupUsers(Integer groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        Set<User> users = group.getUsers();
        return userMapper.userSetToUserResponseDtoList(users);
    }
}

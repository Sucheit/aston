package jdbc.service;

import jdbc.dao.GroupDao;
import jdbc.dto.GroupRequestDto;
import jdbc.dto.GroupResponseDto;
import jdbc.dto.UserResponseDto;
import jdbc.exceptions.NotFoundException;
import jdbc.mappers.GroupMapper;
import jdbc.mappers.UserMapper;
import jdbc.model.Group;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GroupServiceImpl implements GroupService {

    GroupDao groupDao;

    GroupMapper groupMapper;

    UserMapper userMapper;

    @Override
    public List<GroupResponseDto> getGroups() {
        return groupMapper.groupListToGroupResponseDtoList(groupDao.getGroups());
    }

    @Override
    public GroupResponseDto getGroupById(Integer groupId) {
        Group group = groupDao.getGroupById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        return groupMapper.groupToGroupResponseDto(group);
    }

    @Override
    public GroupResponseDto createGroup(GroupRequestDto groupRequestDto) {
        Group group = groupDao.createGroup(groupRequestDto)
                .orElseThrow(() -> new NotFoundException("Group was not created."));
        return groupMapper.groupToGroupResponseDto(group);
    }

    @Override
    public GroupResponseDto updateGroup(Integer groupId, GroupRequestDto groupRequestDto) {
        groupDao.getGroupById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        Group group = groupDao.updateGroup(groupId, groupRequestDto)
                .orElseThrow(() -> new NotFoundException("Group was not updated."));
        return groupMapper.groupToGroupResponseDto(group);
    }

    @Override
    public boolean deleteGroup(Integer groupId) {
        groupDao.getGroupById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        return groupDao.deleteGroup(groupId);
    }

    @Override
    public List<UserResponseDto> getGroupUsers(Integer groupId) {
        groupDao.getGroupById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        return userMapper.userListToUserResponseDtoList(groupDao.getGroupUsers(groupId));
    }
}

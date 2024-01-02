package spring.service;

import spring.dto.GroupRequestDto;
import spring.dto.GroupResponseDto;
import spring.dto.UserResponseDto;

import java.util.List;

public interface GroupService {

    List<GroupResponseDto> getGroups();

    GroupResponseDto getGroupById(Integer groupId);

    GroupResponseDto createGroup(GroupRequestDto groupRequestDto);

    GroupResponseDto updateGroup(Integer groupId, GroupRequestDto groupRequestDto);

    void deleteGroup(Integer groupId);

    List<UserResponseDto> getGroupUsers(Integer groupId);
}

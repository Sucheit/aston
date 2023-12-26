package jdbc.service;

import jdbc.dto.GroupRequestDto;
import jdbc.dto.GroupResponseDto;

import java.util.List;

public interface GroupService {

    List<GroupResponseDto> getGroups();

    GroupResponseDto getGroupById(Integer groupId);

    GroupResponseDto createGroup(GroupRequestDto groupRequestDto);

    GroupResponseDto updateGroup(Integer groupId, GroupRequestDto groupRequestDto);

    boolean deleteGroup(Integer groupId);
}

package spring.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.dto.GroupResponseDto;
import spring.model.Group;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(target = "groupId", source = "group.id")
    GroupResponseDto groupToGroupResponseDto(Group group);

    List<GroupResponseDto> groupSetToGroupResponseDtoList(Set<Group> groups);

    List<GroupResponseDto> groupListToGroupResponseDtoList(List<Group> groups);
}

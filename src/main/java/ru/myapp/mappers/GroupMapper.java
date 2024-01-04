package ru.myapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.myapp.dto.GroupResponseDto;
import ru.myapp.dto.GroupResponseDtoShort;
import ru.myapp.model.Group;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface GroupMapper {

    @Mapping(target = "groupId", source = "group.id")
    GroupResponseDto groupToGroupResponseDto(Group group);

    @Mapping(target = "groupId", source = "group.id")
    GroupResponseDtoShort groupToGroupResponseDtoShort(Group group);

    List<GroupResponseDtoShort> groupListToGroupResponseDtoShortList(List<Group> groups);

}

package ru.myapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.myapp.dto.GroupResponseDto;
import ru.myapp.dto.GroupResponseDtoShort;
import ru.myapp.dto.PaidGroupResponseDtoShort;
import ru.myapp.model.Group;
import ru.myapp.model.PaidGroup;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface GroupMapper {

    @Mapping(target = "groupId", source = "group.id")
    GroupResponseDto groupToGroupResponseDto(Group group);

    @Mapping(target = "groupId", source = "group.id")
    GroupResponseDtoShort groupToGroupResponseDtoShort(Group group);

    @Mapping(target = "groupId", source = "id")
    PaidGroupResponseDtoShort paidGroupToPaidGroupResponseDtoShort(PaidGroup paidGroup);

    List<GroupResponseDtoShort> groupListToGroupResponseDtoShortList(List<Group> groups);

    List<PaidGroupResponseDtoShort> paidGroupsListToPaidGroupResponseDtoList(List<PaidGroup> allPaidGroups);
}

package jdbc.mappers;

import jdbc.dto.GroupResponseDto;
import jdbc.model.Group;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {

    GroupResponseDto groupToGroupResponseDto(Group group);

    List<GroupResponseDto> groupListToGroupResponseDtoList(List<Group> groups);
}

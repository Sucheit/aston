package jdbc.dao;

import jdbc.dto.GroupRequestDto;
import jdbc.model.Group;
import jdbc.model.User;

import java.util.List;
import java.util.Optional;

public interface GroupDao {

    List<Group> getGroups();

    Optional<Group> getGroupById(Integer groupId);

    Optional<Group> createGroup(GroupRequestDto groupRequestDto);

    Optional<Group> updateGroup(Integer groupId, GroupRequestDto groupRequestDto);

    boolean deleteGroup(Integer groupId);

    List<User> getGroupUsers(Integer groupId);
}

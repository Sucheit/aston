package jdbc.dao;

import jdbc.dto.UserRequestDto;
import jdbc.model.Group;
import jdbc.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> getUsers();

    Optional<User> getUserById(Integer userId);

    Optional<User> createUser(UserRequestDto userRequestDto);

    Optional<User> updateUser(Integer userId, UserRequestDto userRequestDto);

    boolean deleteUser(Integer userId);

    List<Group> getUserGroups(Integer userId);

    boolean addUserToGroup(Integer userId, Integer groupId);

    boolean deleteUserFromGroup(Integer userId, Integer groupId);
}

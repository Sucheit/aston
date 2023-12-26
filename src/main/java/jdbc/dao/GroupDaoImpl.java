package jdbc.dao;

import jdbc.dto.GroupRequestDto;
import jdbc.model.Group;
import jdbc.model.User;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static jdbc.config.DataSource.getConnection;

public class GroupDaoImpl implements GroupDao {

    public GroupDaoImpl() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Group> getGroups() {
        List<Group> groups = new LinkedList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select * from groups order by group_id asc;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int userId = resultSet.getInt("group_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                groups.add(Group.builder()
                        .groupId(userId)
                        .name(name)
                        .description(description).build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return groups;
    }

    @Override
    public Optional<Group> getGroupById(Integer groupId) {
        Group group = null;
        try (Connection connection = getConnection()) {
            String sql = "select * from groups where group_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                group = Group.builder()
                        .groupId(groupId)
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return group != null ? Optional.of(group) : Optional.empty();
    }

    @Override
    public Optional<Group> createGroup(GroupRequestDto groupRequestDto) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO groups (name, description) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, groupRequestDto.name());
            preparedStatement.setString(2, groupRequestDto.description());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                return getGroupById(id);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Group> updateGroup(Integer groupId, GroupRequestDto groupRequestDto) {
        try (Connection connection = getConnection()) {
            String sql = "update groups set name = ?, description = ? where group_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, groupRequestDto.name());
            preparedStatement.setString(2, groupRequestDto.description());
            preparedStatement.setInt(3, groupId);
            preparedStatement.executeUpdate();
            return getGroupById(groupId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteGroup(Integer groupId) {
        try (Connection connection = getConnection()) {
            String sql = "delete from groups where group_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getGroupUsers(Integer groupId) {
        List<User> users = new LinkedList<>();
        try (Connection connection = getConnection()) {
            String sql = """
                    select u.user_id, u.first_name, u.second_name from users u\s
                    left join user_groups ug on u.user_id = ug.user_id
                    where ug.group_id = ?;
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(User.builder()
                        .userId(resultSet.getInt("user_id"))
                        .firstName(resultSet.getString("first_name"))
                        .secondName(resultSet.getString("second_name"))
                        .build());
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

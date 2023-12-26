package jdbc.dao;

import jdbc.dto.UserRequestDto;
import jdbc.model.Group;
import jdbc.model.User;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static jdbc.config.DataSource.getConnection;

public class UserDaoImpl implements UserDao {

    public UserDaoImpl() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new LinkedList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select * from users order by user_id asc;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String firstName = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                users.add(User.builder()
                        .userId(userId)
                        .firstName(firstName)
                        .secondName(secondName).build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return users;
    }

    @Override
    public Optional<User> getUserById(Integer userId) {
        User user = null;
        try (Connection connection = getConnection()) {
            String sql = "select * from users where user_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = User.builder()
                        .userId(userId)
                        .firstName(resultSet.getString("first_name"))
                        .secondName(resultSet.getString("second_name"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> createUser(UserRequestDto userRequestDto) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO users (first_name, second_name) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userRequestDto.firstName());
            preparedStatement.setString(2, userRequestDto.secondName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                return getUserById(id);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> updateUser(Integer userId, UserRequestDto userRequestDto) {
        try (Connection connection = getConnection()) {
            String sql = "update users set first_name = ?, second_name = ? where user_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userRequestDto.firstName());
            preparedStatement.setString(2, userRequestDto.secondName());
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
            return getUserById(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteUser(Integer userId) {
        try (Connection connection = getConnection()) {
            String sql = "delete from users where user_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Group> getUserGroups(Integer userId) {
        List<Group> groups = new LinkedList<>();
        try (Connection connection = getConnection()) {
            String sql = """
                    select g.group_id, g.name, g.description  from groups g
                    left join user_groups ug on g.group_id  = ug.group_id
                    where ug.user_id = ?;
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groups.add(Group.builder()
                        .groupId(resultSet.getInt("group_id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .build());
            }
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public boolean addUserToGroup(Integer userId, Integer groupId) {
        try (Connection connection = getConnection()) {
            String sql = "insert into user_groups (user_id, group_id) values (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, groupId);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUserFromGroup(Integer userId, Integer groupId) {
        try (Connection connection = getConnection()) {
            String sql = "delete from user_groups where user_id = ? and group_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, groupId);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

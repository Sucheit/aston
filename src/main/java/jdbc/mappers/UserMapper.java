package jdbc.mappers;

import jdbc.dto.UserResponseDto;
import jdbc.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UserMapper {

    UserResponseDto userToUserResponseDto(User user);

    List<UserResponseDto> userListToUserResponseDtoList(List<User> userList);
}

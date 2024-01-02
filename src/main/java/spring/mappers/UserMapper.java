package spring.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.dto.UserResponseDto;
import spring.model.User;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", source = "user.id")
    UserResponseDto userToUserResponseDto(User user);

    List<UserResponseDto> userListToUserResponseDtoList(List<User> userList);

    List<UserResponseDto> userSetToUserResponseDtoList(Set<User> userList);
}

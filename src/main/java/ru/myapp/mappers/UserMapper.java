package ru.myapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.myapp.dto.UserResponseDto;
import ru.myapp.dto.UserResponseDtoShort;
import ru.myapp.model.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = GroupMapper.class)
public interface UserMapper {

    @Mapping(target = "userId", source = "user.id")
    UserResponseDto userToUserResponseDto(User user);

    @Mapping(target = "userId", source = "user.id")
    UserResponseDtoShort userToUserResponseDtoShort(User user);

    List<UserResponseDtoShort> userListToUserResponseDtoShortList(List<User> userList);
}

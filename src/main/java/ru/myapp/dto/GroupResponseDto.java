package ru.myapp.dto;

import java.util.Set;

public record GroupResponseDto(Integer groupId,
                               String name,
                               String description,
                               Set<UserResponseDtoShort> users) {
}

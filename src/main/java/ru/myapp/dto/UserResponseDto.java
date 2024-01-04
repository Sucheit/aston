package ru.myapp.dto;

import java.util.Set;

public record UserResponseDto(String userId,
                              String firstName,
                              String lastName,
                              Set<GroupResponseDtoShort> groups) {
}

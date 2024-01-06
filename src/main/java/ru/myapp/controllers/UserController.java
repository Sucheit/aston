package ru.myapp.controllers;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myapp.aop.Loggable;
import ru.myapp.dto.UserRequestDto;
import ru.myapp.dto.UserResponseDto;
import ru.myapp.dto.UserResponseDtoShort;
import ru.myapp.service.UserService;

import java.util.List;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@RestController
@ResponseBody
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Loggable
    @GetMapping
    public List<UserResponseDtoShort> getUsers() {
        return userService.getUsers();
    }

    @Loggable
    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    @Loggable
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @Loggable
    @PutMapping("/{userId}")
    public UserResponseDto updateUser(@RequestBody UserRequestDto userRequestDto,
                                      @PathVariable Integer userId) {
        return userService.updateUser(userId, userRequestDto);
    }

    @Loggable
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Loggable
    @PostMapping("/{userId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto addUserToGroup(@PathVariable Integer userId,
                                          @PathVariable Integer groupId) {
        return userService.addUserToGroup(userId, groupId);
    }

    @Loggable
    @DeleteMapping("/{userId}/groups/{groupId}")
    public UserResponseDto deleteUserToGroup(@PathVariable Integer userId,
                                             @PathVariable Integer groupId) {
        return userService.deleteUserFromGroup(userId, groupId);
    }
}

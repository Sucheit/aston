package ru.myapp.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myapp.dto.UserRequestDto;
import ru.myapp.dto.UserResponseDto;
import ru.myapp.dto.UserResponseDtoShort;
import ru.myapp.service.UserService;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDtoShort> getUsers() {
        logger.info("GET /users request");
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable Integer userId) {
        logger.info("GET /users/{} request", userId);
        return userService.getUserById(userId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        logger.info("POST /users request: {}", userRequestDto);
        return userService.createUser(userRequestDto);
    }

    @PutMapping("/{userId}")
    public UserResponseDto updateUser(@RequestBody UserRequestDto userRequestDto,
                                      @PathVariable Integer userId) {
        logger.info("PUT /users/{} request: {}", userId, userRequestDto);
        return userService.updateUser(userId, userRequestDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        logger.info("DELETE /users/{} request", userId);
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto addUserToGroup(@PathVariable Integer userId,
                                          @PathVariable Integer groupId) {
        logger.info("POST /users/{}/groups/{} request", userId, groupId);
        return userService.addUserToGroup(userId, groupId);
    }

    @DeleteMapping("/{userId}/groups/{groupId}")
    public UserResponseDto deleteUserToGroup(@PathVariable Integer userId,
                                             @PathVariable Integer groupId) {
        logger.info("DELETE /users/{}/groups/{} request", userId, groupId);
        return userService.deleteUserFromGroup(userId, groupId);
    }
}

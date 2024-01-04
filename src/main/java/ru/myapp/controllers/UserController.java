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

//@Api(tags = "Пользователи")
@RestController
@ResponseBody
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //    @ApiOperation(value = "Получение списка пользователей")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Список пользователей получен"),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
//    })
    @GetMapping
    public List<UserResponseDtoShort> getUsers() {
        logger.info("GET /users request");
        return userService.getUsers();
    }

    //    @ApiOperation(value = "Получение пользователя по id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Пользователь получен"),
//            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
//    })
    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable Integer userId) {
        logger.info("GET /users/{} request", userId);
        return userService.getUserById(userId);
    }

    //    @ApiOperation(value = "Создание пользователя")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Пользователь создан"),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
//    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        logger.info("POST /users request: {}", userRequestDto);
        return userService.createUser(userRequestDto);
    }

    //    @ApiOperation(value = "Обновление пользователя")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Пользователь обновлен"),
//            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
//    })
    @PutMapping("/{userId}")
    public UserResponseDto updateUser(@RequestBody UserRequestDto userRequestDto,
                                      @PathVariable Integer userId) {
        logger.info("PUT /users/{} request: {}", userId, userRequestDto);
        return userService.updateUser(userId, userRequestDto);
    }

    //    @ApiOperation(value = "Удаление пользователя")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "Пользователь удален"),
//            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
//    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        logger.info("DELETE /users/{} request", userId);
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    //    @ApiOperation(value = "Добавление пользователя в группу")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Пользователь добавлен в группу"),
//            @ApiResponse(responseCode = "404", description = "Пользователь или группа не найдены"),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
//    })
    @PostMapping("/{userId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto addUserToGroup(@PathVariable Integer userId,
                                                 @PathVariable Integer groupId) {
        logger.info("POST /users/{}/groups/{} request", userId, groupId);
        return userService.addUserToGroup(userId, groupId);
    }

    //    @ApiOperation(value = "Удаление пользователя из группу")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Пользователь удален из группы"),
//            @ApiResponse(responseCode = "404", description = "Пользователь или группа не найдены"),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
//    })
    @DeleteMapping("/{userId}/groups/{groupId}")
    public UserResponseDto deleteUserToGroup(@PathVariable Integer userId,
                                                    @PathVariable Integer groupId) {
        logger.info("DELETE /users/{}/groups/{} request", userId, groupId);
        return userService.deleteUserFromGroup(userId, groupId);
    }
}

package ru.myapp.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myapp.dto.GroupRequestDto;
import ru.myapp.dto.GroupResponseDto;
import ru.myapp.dto.GroupResponseDtoShort;
import ru.myapp.service.GroupService;

import java.util.List;

//@Api(tags = "Группы")
@RestController
@ResponseBody
@RequestMapping("/groups")
public class GroupController {

    private static final Logger logger = LogManager.getLogger(GroupController.class);

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    //    @ApiOperation(value = "Получение списка групп")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Список групп получен"),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
//    })
    @GetMapping
    public List<GroupResponseDtoShort> getGroups() {
        logger.info("GET /groups request");
        return groupService.getGroups();
    }

    //    @ApiOperation(value = "Получение группы по id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Группа получена"),
//            @ApiResponse(responseCode = "404", description = "Группа не найдена"),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
//    })
    @GetMapping("/{groupId}")
    public GroupResponseDto getGroupById(@PathVariable Integer groupId) {
        logger.info("GET /groups/{} request", groupId);
        return groupService.getGroupById(groupId);
    }

    //    @ApiOperation(value = "Создание группы")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Группа создана"),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
//    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponseDto creatGroup(@RequestBody GroupRequestDto groupRequestDto) {
        logger.info("POST /groups request: {}", groupRequestDto);
        return groupService.createGroup(groupRequestDto);
    }

    //    @ApiOperation(value = "Обновление группы")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Группа обновлена"),
//            @ApiResponse(responseCode = "404", description = "Группа не найдена"),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
//    })
    @PutMapping("/{groupId}")
    public GroupResponseDto updateGroup(@PathVariable Integer groupId,
                                        @RequestBody GroupRequestDto groupRequestDto) {
        logger.info("PUT /groups/{} request: {}", groupId, groupRequestDto);
        return groupService.updateGroup(groupId, groupRequestDto);
    }

    //    @ApiOperation(value = "Удаление группы")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Группа удалена"),
//            @ApiResponse(responseCode = "404", description = "Группа не найдена"),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
//    })
    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroupById(@PathVariable Integer groupId) {
        logger.info("DELETE /groups/{} request", groupId);
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }
}

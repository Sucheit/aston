package ru.myapp.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myapp.dto.GroupRequestDto;
import ru.myapp.dto.GroupResponseDto;
import ru.myapp.dto.GroupResponseDtoShort;
import ru.myapp.dto.PaidGroupResponseDtoShort;
import ru.myapp.service.GroupService;

import java.util.List;


@RestController
@ResponseBody
@RequestMapping("/groups")
public class GroupController {

    private static final Logger logger = LogManager.getLogger(GroupController.class);

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupResponseDtoShort> getGroups() {
        logger.info("GET /groups request");
        return groupService.getGroups();
    }

    @GetMapping("/paid")
    public List<PaidGroupResponseDtoShort> getPaidGroups() {
        logger.info("GET /groups/paid request");
        return groupService.getPaidGroups();
    }


    @GetMapping("/{groupId}")
    public GroupResponseDto getGroupById(@PathVariable Integer groupId) {
        logger.info("GET /groups/{} request", groupId);
        return groupService.getGroupById(groupId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponseDto creatGroup(@RequestBody GroupRequestDto groupRequestDto) {
        logger.info("POST /groups request: {}", groupRequestDto);
        return groupService.createGroup(groupRequestDto);
    }


    @PutMapping("/{groupId}")
    public GroupResponseDto updateGroup(@PathVariable Integer groupId,
                                        @RequestBody GroupRequestDto groupRequestDto) {
        logger.info("PUT /groups/{} request: {}", groupId, groupRequestDto);
        return groupService.updateGroup(groupId, groupRequestDto);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroupById(@PathVariable Integer groupId) {
        logger.info("DELETE /groups/{} request", groupId);
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }
}

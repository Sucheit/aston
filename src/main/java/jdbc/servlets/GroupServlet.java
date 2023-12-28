package jdbc.servlets;

import com.google.gson.Gson;
import jdbc.dto.GroupRequestDto;
import jdbc.dto.GroupResponseDto;
import jdbc.exceptions.NotFoundException;
import jdbc.service.GroupService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static jdbc.Main.getGroupDao;
import static jdbc.Main.getGroupService;

@WebServlet(urlPatterns = {"/groups", "/groups/*"})
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupServlet extends HttpServlet {

    GroupService groupService;

    Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        groupService = getGroupService(getGroupDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        var mapping = req.getHttpServletMapping();
        String gson = switch (mapping.getPattern()) {
            case "/groups/*" -> {
                Integer groupId = Integer.parseInt(mapping.getMatchValue());
                GroupResponseDto groupResponseDto;
                try {
                    groupResponseDto = groupService.getGroupById(groupId);
                } catch (NotFoundException e) {
                    resp.sendError(404, e.getMessage());
                    yield e.getMessage();
                }
                yield this.gson.toJson(groupResponseDto);
            }
            case "/groups" -> {
                List<GroupResponseDto> groups = groupService.getGroups();
                yield this.gson.toJson(groups);
            }
            default -> "Invalid URL";
        };
        resp.getWriter().write(gson);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        var mapping = req.getHttpServletMapping();
        String gsonString = "Invalid URL";
        if (mapping.getPattern().equals("/groups/*")) {
            Integer groupId = Integer.parseInt(mapping.getMatchValue());
            try {
                groupService.deleteGroup(groupId);
                gsonString = "Group deleted";
            } catch (NotFoundException e) {
                resp.sendError(404, e.getMessage());
            }
        }
        resp.getWriter().write(gsonString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String body = req.getReader()
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
        GroupRequestDto groupRequestDto = gson.fromJson(body, GroupRequestDto.class);
        GroupResponseDto groupResponseDto = groupService.createGroup(groupRequestDto);
        String gsonString = gson.toJson(groupResponseDto);
        resp.getWriter().write(gsonString);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        var mapping = req.getHttpServletMapping();
        String gsonString = "Invalid URL";
        if (mapping.getPattern().equals("/groups/*")) {
            Integer groupId = Integer.parseInt(mapping.getMatchValue());
            String body = req.getReader()
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            GroupRequestDto groupRequestDto = gson.fromJson(body, GroupRequestDto.class);
            try {
                GroupResponseDto groupResponseDto = groupService.updateGroup(groupId, groupRequestDto);
                gsonString = gson.toJson(groupResponseDto);
            } catch (NotFoundException e) {
                resp.sendError(404, e.getMessage());
            }
        }
        resp.getWriter().write(gsonString);
    }
}

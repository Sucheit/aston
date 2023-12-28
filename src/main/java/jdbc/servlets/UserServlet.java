package jdbc.servlets;

import com.google.gson.Gson;
import jdbc.dto.GroupResponseDto;
import jdbc.dto.UserRequestDto;
import jdbc.dto.UserResponseDto;
import jdbc.exceptions.NotFoundException;
import jdbc.service.UserService;
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

import static jdbc.Main.getUserDao;
import static jdbc.Main.getUserService;

@WebServlet(urlPatterns = {"/users", "/users/*"})
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServlet extends HttpServlet {

    UserService userService;

    Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        userService = getUserService(getUserDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        var mapping = req.getHttpServletMapping();
        String gsonString = "Invalid URL";
        String pattern = mapping.getPattern();
        if (pattern.equals("/users/*")) {
            String[] strings = mapping.getMatchValue().split("/");
            Integer userId = Integer.parseInt(strings[0]);
            if (strings.length == 1) {
                UserResponseDto userResponseDto = null;
                try {
                    userResponseDto = userService.getUserById(userId);
                } catch (NotFoundException e) {
                    resp.sendError(404, e.getMessage());
                }
                gsonString = gson.toJson(userResponseDto);
            }
            if (strings.length == 2) {
                List<GroupResponseDto> groupResponseDtos = null;
                try {
                    groupResponseDtos = userService.getUserGroups(userId);
                } catch (NotFoundException e) {
                    resp.sendError(404, e.getMessage());
                }
                gsonString = gson.toJson(groupResponseDtos);
            }
        } else if (pattern.equals("/users")) {
            List<UserResponseDto> users = userService.getUsers();
            gsonString = gson.toJson(users);
        }

        resp.getWriter().write(gsonString);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        var mapping = req.getHttpServletMapping();
        String gsonString = "Invalid URL";
        String groupIdStr = req.getParameter("groupId");
        if (mapping.getPattern().equals("/users/*")) {
            Integer userId = Integer.parseInt(mapping.getMatchValue());
            if (groupIdStr == null) {
                try {
                    userService.deleteUser(userId);
                    gsonString = "User deleted";
                } catch (NotFoundException e) {
                    resp.sendError(404, e.getMessage());
                }
            } else {
                Integer groupId = Integer.parseInt(groupIdStr);
                userService.deleteUserFromGroup(userId, groupId);
                gsonString = String.format("User id=%s deleted from group id=%s", userId, groupId);
            }
        }
        resp.getWriter().write(gsonString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String gsonString;
        var mapping = req.getHttpServletMapping();
        String pattern = mapping.getPattern();
        if (pattern.equals("/users")) {
            String body = req.getReader()
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            UserRequestDto userRequestDto = gson.fromJson(body, UserRequestDto.class);
            UserResponseDto userResponseDto = userService.createUser(userRequestDto);
            gsonString = gson.toJson(userResponseDto);
        } else if (pattern.equals("/users/*")) {
            Integer groupId = Integer.parseInt(req.getParameter("groupId"));
            Integer userId = Integer.parseInt(mapping.getMatchValue());
            userService.addUserToGroup(userId, groupId);
            gsonString = String.format("User id=%s added to group id=%s", userId, groupId);
        } else {
            gsonString = "Invalid URL";
        }
        resp.getWriter().write(gsonString);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        var mapping = req.getHttpServletMapping();
        String gsonString = "Invalid URL";
        if (mapping.getPattern().equals("/users/*")) {
            Integer userId = Integer.parseInt(mapping.getMatchValue());
            String body = req.getReader()
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            UserRequestDto userRequestDto = gson.fromJson(body, UserRequestDto.class);
            try {
                UserResponseDto userResponseDto = userService.updateUser(userId, userRequestDto);
                gsonString = gson.toJson(userResponseDto);
            } catch (NotFoundException e) {
                resp.sendError(404, e.getMessage());
            }
        }
        resp.getWriter().write(gsonString);
    }
}

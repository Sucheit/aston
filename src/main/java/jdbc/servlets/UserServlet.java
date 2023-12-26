package jdbc.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdbc.Main;
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
        userService = Main.getUserService(Main.getUserDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        var mapping = req.getHttpServletMapping();
        String gson = switch (mapping.getPattern()) {
            case "/users/*" -> {
                Integer userId = Integer.parseInt(mapping.getMatchValue());
                UserResponseDto userResponseDto;
                try {
                    userResponseDto = userService.getUserById(userId);
                } catch (NotFoundException e) {
                    resp.sendError(404, e.getMessage());
                    yield e.getMessage();
                }
                yield this.gson.toJson(userResponseDto);
            }
            case "/users" -> {
                List<UserResponseDto> users = userService.getUsers();
                yield this.gson.toJson(users);
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
        if (mapping.getPattern().equals("/users/*")) {
            Integer userId = Integer.parseInt(mapping.getMatchValue());
            try {
                userService.deleteUser(userId);
                gsonString = "User deleted";
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
        UserRequestDto userRequestDto = gson.fromJson(body, UserRequestDto.class);
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        String gsonString = gson.toJson(userResponseDto);
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

package jdbc;

import jdbc.dao.UserDao;
import jdbc.dao.UserDaoImpl;
import jdbc.mappers.UserMapper;
import jdbc.service.UserService;
import jdbc.service.UserServiceImpl;
import org.mapstruct.factory.Mappers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    public static final String POSTRESQL_URL = "jdbc:postgresql://localhost:5432/aston";
    public static final String USER = "user";
    public static final String PASSWORD = "password";

    public static void main(String[] args) {
    }

    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }

    public static UserService getUserService(UserDao userDao) {
        return new UserServiceImpl(userDao, Mappers.getMapper(UserMapper.class));
    }

}

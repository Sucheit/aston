package jdbc;

import jdbc.dao.GroupDao;
import jdbc.dao.GroupDaoImpl;
import jdbc.dao.UserDao;
import jdbc.dao.UserDaoImpl;
import jdbc.mappers.GroupMapper;
import jdbc.mappers.UserMapper;
import jdbc.service.GroupService;
import jdbc.service.GroupServiceImpl;
import jdbc.service.UserService;
import jdbc.service.UserServiceImpl;
import org.mapstruct.factory.Mappers;

public class Main {

    public static void main(String[] args) {
    }

    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }

    public static UserService getUserService(UserDao userDao) {
        return new UserServiceImpl(userDao, Mappers.getMapper(UserMapper.class), Mappers.getMapper(GroupMapper.class));
    }

    public static GroupDao getGroupDao() {
        return new GroupDaoImpl();
    }

    public static GroupService getGroupService(GroupDao groupDao) {
        return new GroupServiceImpl(groupDao, Mappers.getMapper(GroupMapper.class), Mappers.getMapper(UserMapper.class));
    }
}

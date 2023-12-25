import jdbc.dao.UserDao;
import jdbc.dao.UserDaoImpl;
import jdbc.mappers.UserMapper;
import jdbc.service.UserService;
import jdbc.service.UserServiceImpl;
import org.mapstruct.factory.Mappers;

public class ServletsJdbcMain {

    public static void main(String[] args) {
    }

    UserDao getUserDao() {
        return new UserDaoImpl();
    }

    UserService getUserService(UserDao userDao) {
        return new UserServiceImpl(userDao, Mappers.getMapper(UserMapper.class));
    }
}

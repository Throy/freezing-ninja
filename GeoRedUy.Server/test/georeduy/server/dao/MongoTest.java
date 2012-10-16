package georeduy.server.dao;

import georeduy.server.data.User;
import georeduy.server.data.UserData;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MongoTest {

    private UserDao userDao;

    @Before
    public void before() {
        userDao = new UserDaoImpl();
    }

    private User createIfNotExistsUser() {

        User user = userDao.findByUserName("micalb");
        if (user == null) {
            user = new User();
            user.setPassword("aaa");
            user.setUserName("micalb");
            UserData userData = new UserData();
            userData.setEmail("micalb@gmail.com");
            user.setUserData(userData);
            userDao.saveUser(user);
        }

        return user;
    }

    @Test
    public void testPersistence() {
        // Insert
        User user = createIfNotExistsUser();

        // Update
//        UserData userData = new UserData();
//        userData.setEmail("micalb@gmail.com");
//        user.setUserData(userData);
//        userDao.saveUser(user);
        user = userDao.findByUserName("micalb");
        assertTrue(user.getUserData() != null);
    }

    @Test
    public void query() {

        UserData userDataMica = new UserData();
        userDataMica.setEmail("micalb@gmail.com");

        User user = userDao.findByUserName("micalb");

        assertTrue("Should have obtained a single user for the userDataMica",
                user.getUserName().equals("micalb"));

        UserData userDataAgus = new UserData();
        userDataAgus.setEmail("agustin@gmail.com");

        List<User> users = userDao.findUsersByUserData(userDataAgus);
        assertTrue("Should have obtained NONE user for the userDataAgus",
                users.isEmpty());

    }
    
    @Test
    public void testLogin(){
        User user = userDao.findByUsernameAndPassword("micalb", "dragon");
        assertNull(user);
        user = userDao.findByUsernameAndPassword("micalb", "aaa");
        assertNotNull(user);
    }
}

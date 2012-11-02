package georeduy.server.dao;

import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.UserData;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class InitDatabase {

    private static IUserDao userDao;
    private static ISiteDao siteDao;

    @Before
    public void before() {
        userDao = new UserDaoImpl();
        siteDao = new SiteDaoImpl();
    }

    private User createUsers() {

        User user = userDao.findByUserName("Admin");
        if (user == null) {
            user = new User();
            user.setPassword("1234");
            user.setUserName("Admin");
            UserData userData = new UserData();
            userData.setEmail("admin@geored.uy");
            user.setUserData(userData);
            List<String> roles = new ArrayList<String>();
            roles.add(Roles.REG_USER);
            roles.add(Roles.ADMIN);
            user.setRoles(roles);
            userDao.saveUser(user);
        }
        
        user = userDao.findByUserName("Agustin");
        if (user == null) {
            user = new User();
            user.setPassword("1234");
            user.setUserName("Agustin");
            UserData userData = new UserData();
            userData.setEmail("agustin@geored.uy");
            user.setUserData(userData);
            List<String> roles = new ArrayList<String>();
            roles.add(Roles.REG_USER);
            user.setRoles(roles);
            userDao.saveUser(user);
        }
        
        Site site = new Site();
        site.setName ("Sitio A");
        site.setDescription ("Es un sitio admirable");
    	Double[] coordinates = {-34.89, -56.13};
    	site.setCoordinates(coordinates);
        site.setAddress ("Calle A");
        site.setImageUrl ("site_a.jpg");
        site.setTags (null);
        siteDao.saveSite(site);

        return user;
    }

    @Test
    public void LoadUsers() {
    	createUsers();
    }

}

package georeduy.server.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.UserData;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoTest {

    private IUserDao userDao;

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
    
    @Test
    public void testReference(){
    	Mongo m;
        try {
	        m = new Mongo( "localhost" , 27017 );
	        DB db = m.getDB( "geouy" );
	    	DBCollection coll = db.getCollection("retailers");
	    	DBObject retailer = coll.findOne();
	    	DBRef userRef = (DBRef) retailer.get("user");
	    	userRef = new DBRef(db, "db.geouy.users", userRef.getId().toString());
	    	DBObject user = userRef.fetch();
	    	String name = (String) retailer.get("name");
	    	String name2 = (String) retailer.get("name");
        } catch (UnknownHostException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (MongoException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
    }
}

package georeduy.server.dao;

import com.google.code.morphia.dao.BasicDAO;
import georeduy.server.data.User;
import georeduy.server.data.UserData;
import georeduy.server.persistence.MongoConnectionManager;
import java.util.List;
import org.bson.types.ObjectId;

public class UserDaoImpl extends BasicDAO<User, ObjectId> implements UserDao {

    public UserDaoImpl() {
        super(User.class, MongoConnectionManager.instance().getDb());
    }

    @Override
    public void saveUser(User user) {
        this.save(user);
    }

    @Override
    public User find(ObjectId userId) {
        return get(userId);
    }

    @Override
    public List<User> findUsersByUserData(UserData userData) {
        return createQuery().field("userData").equal(userData).asList();
    }

    @Override
    public User findByUserName(String userName) {
    	List<User> users = createQuery().field("userName").equal(userName).asList();
    	if (users.size() == 1)
    		return users.get(0);
    	else
    		return null;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User ret = null;

        List<User> users = createQuery().field("userName").equal(username)
                .field("password").equal(password).asList();
        if (users.size() == 1) {
            ret = users.get(0);
        } else if (users.size() > 1) {
            throw new RuntimeException(
                    "username is duplicated in database. run guys!");
        }

        return ret;
    }
}

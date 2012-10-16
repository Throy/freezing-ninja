package georeduy.server.dao;

import georeduy.server.data.User;
import georeduy.server.data.UserData;
import java.util.List;
import org.bson.types.ObjectId;

public interface UserDao {

    public void saveUser(User user);

    public User find(ObjectId userId);

    public User findByUserName(String user);

    public List<User> findUsersByUserData(UserData userData);

    public User findByUsernameAndPassword(String username, String password);
}
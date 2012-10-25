package georeduy.server.dao;

import georeduy.server.logic.model.User;
import georeduy.server.logic.model.UserData;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.query.UpdateOperations;

public interface IUserDao {

    public void saveUser(User user);
    
    public UpdateOperations<User> getUpdateOperations();
    
    public void update(User user, UpdateOperations<User> uo);

    public User find(ObjectId userId);

    public User findByUserName(String user);

    public List<User> findUsersByUserData(UserData userData);

    public User findByUsernameAndPassword(String username, String password);
}

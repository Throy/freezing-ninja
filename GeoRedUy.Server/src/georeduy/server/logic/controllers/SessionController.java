package georeduy.server.logic.controllers;

import georeduy.server.dao.IUserDao;
import georeduy.server.dao.UserDaoImpl;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.User;
import georeduy.server.util.FacebookUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SessionController {

    private static SessionController s_instance = null;
    private Map<String, User> m_onlineUsers;
    private IUserDao userDao;

    public SessionController() {
    	m_onlineUsers = new HashMap<String, User>();
        userDao = new UserDaoImpl();
    }

    public static SessionController getInstance() {
        if (s_instance == null) {
            s_instance = new SessionController();
        }

        return s_instance;
    }

    public String LogIn(String userName, String password) throws Exception {
        
        User user = userDao.findByUsernameAndPassword(userName, password);
        
        if (user != null) {
            String token;
            do {
                token = GenerateToken();
            } while (m_onlineUsers.containsKey(token));
            m_onlineUsers.put(token, user);

            return token;
        }

        throw new Exception(GeoRedConstants.LOGIN_BAD_USERNAME_PASSWORD);
    }
    
    public String LogInExternal(String tokenType, String accessToken) throws Exception {
        
    	FacebookUtils fb = new FacebookUtils();
        if (fb.isTokenValid(accessToken)) {
        	User facebookUser = fb.fetchUser();
        	User user = userDao.findByExternalId(facebookUser.getExternalId());
        	if (user == null) {
        		Register(facebookUser);
        		user = facebookUser;
        	}
        	
        	if (user != null) {
                String token;
                do {
                    token = GenerateToken();
                } while (m_onlineUsers.containsKey(token));
                m_onlineUsers.put(token, user);

                return token;
            }
        }

        throw new Exception(GeoRedConstants.LOGIN_INVALID_TOKEN);
    }

    public void LogOut(String token) {
    	m_onlineUsers.remove(token);
    }

    // registrar un usuario cliente.
    public void Register(User user) throws Exception {
        if (userDao.findByUserName(user.getUserName()) == null) {
        	List<String> roles = new ArrayList<String>();
        	roles.add(Roles.REG_USER);
        	user.setRoles(roles);
        	
            userDao.saveUser(user);
        }
        else
        {
        	throw new Exception(GeoRedConstants.REGISTER_USERNAME_EXISTS);
        }        
    }

    public boolean isTokenValid(String token) {
        return m_onlineUsers.containsKey(token);
    }

    public User GetClient(String token) {
        return m_onlineUsers.get(token);
    }

    private String GenerateToken() {
        SecureRandom secureRandom = null;
        MessageDigest digest = null;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
        }
        secureRandom.setSeed(secureRandom.generateSeed(128));
        return digest.digest((secureRandom.nextLong() + "").getBytes()).toString();
    }
}

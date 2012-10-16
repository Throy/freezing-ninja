package georeduy.server.logic.controllers;

import georeduy.server.dao.UserDao;
import georeduy.server.dao.UserDaoImpl;
import georeduy.server.data.User;
import georeduy.server.data.UserData;
import georeduy.server.logic.model.Client;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SessionController {

    private static SessionController s_instance = null;
    private Map<String, Client> m_onlineClients;
    private UserDao userDao;

    public SessionController() {
        m_onlineClients = new HashMap<String, Client>();
        userDao = new UserDaoImpl();
    }

    public static SessionController getInstance() {
        if (s_instance == null) {
            s_instance = new SessionController();
        }

        return s_instance;
    }

    public String LogIn(String userName, String password) {
        
        User user = userDao.findByUsernameAndPassword(userName, password);
        
        if(user!=null){
            Client client = new Client(user.getId(), user.getUserName(), 
                    user.getUserData().getName(), 
                    user.getUserData().getLastName(), 
                    user.getUserData().getEmail(), user.getRoles());
            String token;
            do {
                token = GenerateToken();
            } while (m_onlineClients.containsKey(token));
            m_onlineClients.put(token, client);

            return token;
        }

        return null; // Hay que hacer algo para el manejo de errores
    }

    public void LogOut(String token) {
        m_onlineClients.remove(token);
    }

    public boolean Register(User user) {
        if (userDao.findByUserName(user.getUserName()) == null) {
            userDao.saveUser(user);
            
            return true;
        }
        else
        {
        	return false;
        }
        
    }

    public boolean isTokenValid(String token) {
        return m_onlineClients.containsKey(token);
    }

    public Client GetClient(String token) {
        return m_onlineClients.get(token);
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

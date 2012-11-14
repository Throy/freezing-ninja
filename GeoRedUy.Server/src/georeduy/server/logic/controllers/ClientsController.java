package georeduy.server.logic.controllers;

import georeduy.server.dao.ContactDaoImpl;
import georeduy.server.dao.IContactDao;
import georeduy.server.dao.IUserDao;
import georeduy.server.dao.UserDaoImpl;
import georeduy.server.logic.model.Contact;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Tag;
import georeduy.server.logic.model.User;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.bson.types.ObjectId;

public class ClientsController {
	// instancia del singleton
	private static ClientsController s_instance = null;
	
	// propiedades de correo
	private Properties mailConfig;
	
	// constantes de correo
    private String SMTP_HOST = "smtp.gmail.com";  
    private String GEORED_MAIL_ADDRESS = "tsi2.georeduy@gmail.com";  
    private String GEORED_MAIL_PASSWORD = "sacamos12";  
    private String GEORED_MAIL_NAME = "GeoRed.Uy"; 
	
	// daos
	private IUserDao userDao =  new UserDaoImpl();
	private IContactDao contactDao =  new ContactDaoImpl();
	
	// constructores
	
	public ClientsController() {
		mailConfig = new Properties ();
		mailConfig.put("mail.smtp.host", SMTP_HOST);  
		mailConfig.put("mail.smtp.auth", "true");  
        mailConfig.put("mail.debug", "false");  
        mailConfig.put("mail.smtp.ssl.enable", "true");  
	}

	public static ClientsController getInstance() {
		if (s_instance == null) {
			s_instance = new ClientsController();
		}

		return s_instance;
	}
	
	// métodos del controlador
	
	public void AddContact(Contact contact) throws Exception {
        if (userDao.find(new ObjectId(contact.getContactUserId())) != null) {
        	if (contactDao.findByContactUserId(contact.getContactUserId()) == null) { 
	        	contact.setUserId(User.Current().getId());
	        	contactDao.saveContact(contact);
        	} else {
        		throw new Exception(GeoRedConstants.CONTACT_ALREADY_EXISTS);
        	}
        } else {
        	throw new Exception(GeoRedConstants.USER_DOES_NOT_EXIST);
        }
	}
	
	public void RemoveContact(Contact contact) {
		contactDao.deleteContact(contact);
	}
	
	public List<Contact> Get(int from, int count) {
		return contactDao.findByUser(User.Current().getId(), from, count);
	}
	
	public List<User> SearchUsersByName(String name, int from, int count) {
		return userDao.searchByUsersName(name, from, count);
	}

	// envía una invitación a un usuario
	public void sendInvitation (String userEmail, String userName, String message)
			throws AddressException, MessagingException, UnsupportedEncodingException {

		Session session = Session.getInstance(mailConfig, new SocialAuth());  
		MimeMessage mimiMessage = new MimeMessage (session);
		mimiMessage.setFrom (new InternetAddress (GEORED_MAIL_ADDRESS, GEORED_MAIL_NAME));
		
		mimiMessage.addRecipient (Message.RecipientType.TO, new InternetAddress (userEmail, userName));
		mimiMessage.setSubject (User.Current ().getUserData ().getName () + " " + User.Current ().getUserData ().getLastName () +" te invita a GeoRed.uy");
		mimiMessage.setContent ("<font face=\"Verdana, Tahoma\" size=\"2\">¡Hola, " + userName + "! \n"
				+ User.Current ().getUserData ().getName () + " " + User.Current ().getUserData ().getLastName ()
				+ " te invita a unirte a <b>GeoRed.uy</b>, la red social móvil del Uruguay, con el siguente mensaje:<br/><br/>"
				+ "<i>" + message + "</i><br/><br/>"
				+ "Por más información, visitá <a href=\"http://www.geored.com.uy\">www.geored.com.uy</a>.</font>",
				"text/html");
		
		Transport.send (mimiMessage);
	}
	
    private class SocialAuth extends Authenticator {  
    	  
        @Override  
        protected PasswordAuthentication getPasswordAuthentication() {  
  
            return new PasswordAuthentication (GEORED_MAIL_ADDRESS, GEORED_MAIL_PASSWORD);  
  
        }  
    }  
}

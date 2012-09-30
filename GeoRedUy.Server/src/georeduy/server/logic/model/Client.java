package georeduy.server.logic.model;

import java.util.List;

public class Client {
	private int    m_id;
	private String m_userName;
	private String m_firstName;
	private String m_lastName;
	private String m_email;
	private List<String>   m_roles = null;
	
	public Client(int id, String userName, String firstName, String lastName, String email, List<String> roles) {
		m_id = id;
		m_userName = userName;
		m_firstName = firstName;
		m_lastName = lastName;
		m_roles = roles;
	}
	
	public int getId() {
		return m_id;
	}
	
	public String getUserName() {
		return m_userName;
	}

	public String getFirstName() {
		return m_firstName;
	}

	public String getLastName() {
		return m_lastName;
	}
	
	public String getEmail() {
		return m_email;
	}
	
	public List<String> getRoles() {
	    return m_roles;
    }	
}

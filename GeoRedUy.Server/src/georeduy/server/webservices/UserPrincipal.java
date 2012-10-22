package georeduy.server.webservices;


import georeduy.server.data.User;

import java.security.Principal;


public class UserPrincipal implements Principal {
	
	private User m_user;
	private String m_token;
	
	public UserPrincipal(User user, String token) {
		m_user = user;
		m_token = token;
	}
	
	@Override
    public String getName() {
	    return m_user.getUserName();
    }
	
	public User getUser() {
	    return m_user;
    }
	
	public String getToken() {
	    return m_token;
    }

}

package georeduy.server.webservices;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ClientRequestWrapper extends HttpServletRequestWrapper {

	UserPrincipal m_principal;
    List<String> m_roles = null;
	HttpServletRequest m_realRequest;
  
	public ClientRequestWrapper(UserPrincipal principal, List<String> roles, HttpServletRequest request) {
	    super(request);
	    m_principal = principal;
	    m_roles = roles;
	    m_realRequest = request;
	    
	    if (m_roles == null) {
	    	m_roles = new ArrayList<String>();
	    }
	}

	@Override
	public boolean isUserInRole(String role) {
	    if (m_roles == null) {
	        return m_realRequest.isUserInRole(role);
	    }
	    
	    return m_roles.contains(role);
	}

    @Override
    public Principal getUserPrincipal() {
    	if (m_principal == null) {
    		return m_realRequest.getUserPrincipal();
    	}
    	
    	return m_principal;
    }
}

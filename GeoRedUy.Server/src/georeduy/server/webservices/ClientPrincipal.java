package georeduy.server.webservices;

import georeduy.server.logic.model.Client;

import java.security.Principal;

public class ClientPrincipal implements Principal {
	
	private Client m_client;
	private String m_token;
	
	public ClientPrincipal(Client client, String token) {
		m_client = client;
		m_token = token;
	}
	
	@Override
    public String getName() {
	    return m_client.getUserName();
    }
	
	public Client getClient() {
	    return m_client;
    }
	
	public String getToken() {
	    return m_token;
    }

}

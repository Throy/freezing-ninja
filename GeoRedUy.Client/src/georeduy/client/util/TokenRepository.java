package georeduy.client.util;

public class TokenRepository {
	private static TokenRepository s_instance = null;
	
	private String m_token = null;
	
    public static TokenRepository getInstance(){
        if (s_instance == null)
        {
        	s_instance = new TokenRepository();
        }
        
        return s_instance;
    }

	public String getToken() {
	    return m_token;
    }

	public void setToken(String token) {
	    m_token = token;
    }
}

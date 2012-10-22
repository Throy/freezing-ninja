package georeduy.backend.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

public class GeoRedClient {

	public static String Get(String uri, Map<String, String> params, String token)
	        throws Exception {
		
		ClientResponse<String> response = GeoRedClient.GetRequest(Config.SERVER_URL + uri, params, token);
		
		int status = response.getStatus();
		if (status != 200) {
			throw new Exception(response.getEntity(String.class));
		}
		
		return response.getEntity(String.class);
	}

	private static ClientResponse<String> GetRequest(String endpoint, Map<String, String> params, String token) throws Exception {
		StringBuilder query = new StringBuilder();
		
		if (params != null && params.size() > 0) {
			Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> param = iterator.next();
				query.append(param.getKey()).append('=').append(param.getValue());
				
				if (iterator.hasNext()) {
					query.append('&');
				}
			}
			endpoint = endpoint + "?" + query;
		}
		
		ClientRequest request = new ClientRequest(endpoint);

		request.accept(MediaType.TEXT_PLAIN_TYPE);

		if (token != null)
			request.header("Token", token);
 
		return request.get();
	}
	
	public static String Post(String uri, Map<String, String> params, String token)
	        throws Exception {
		
		ClientResponse<String> response = GeoRedClient.PostRequest(Config.SERVER_URL + uri, params, token);
		
		int status = response.getStatus();
		if (status != 200) {
			throw new Exception(response.getEntity(String.class));
		}
		
		return response.getEntity(String.class);
	}
	
	private static ClientResponse<String> PostRequest(String endpoint, Map<String, String> params, String token) throws Exception {
		StringBuilder query = new StringBuilder();
		
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			query.append(param.getKey()).append('=').append(param.getValue());
			
			if (iterator.hasNext()) {
				query.append('&');
			}
		}
		
		ClientRequest request = new ClientRequest(endpoint);

		request.accept("text/plain").body( MediaType.TEXT_PLAIN, query);

		if (token != null)
			request.header("Token", token);
 
		return request.post();
	}
}

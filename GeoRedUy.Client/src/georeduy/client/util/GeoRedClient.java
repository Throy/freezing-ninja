package georeduy.client.util;

import static com.google.resting.component.EncodingTypes.UTF8;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.google.resting.Resting;
import com.google.resting.component.RequestParams;
import com.google.resting.component.impl.ServiceResponse;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public class GeoRedClient {
	
	private static List<Header> getHeaders() {
		List<Header> headers = new ArrayList<Header>();
		String token = TokenRepository.getInstance().getToken();
		
		if (token != null)
			headers.add(new BasicHeader("Token", TokenRepository.getInstance().getToken()));
		
		return headers;
	}
	
	public static String get(String uri, RequestParams requestParams) {
		ServiceResponse response = Resting.get(Config.SERVER_URL + uri, Config.SERVER_PORT, requestParams, UTF8, getHeaders()); 
		
		return response.getResponseString();
	}
	
	public static void getAsync(final String uri, final RequestParams requestParams, final OnCompletedCallback callback) {
		(new AsyncTask<String, String, String>(){

			@Override
            protected String doInBackground(String... params) {
				ServiceResponse response = Resting.get(Config.SERVER_URL + uri, Config.SERVER_PORT, requestParams, UTF8, getHeaders());
	            
				return response.getResponseString();
            }
			
			protected void onPostExecute(String response) {
				callback.onCompleted(response);
		        super.onPostExecute(response);
		    }
			
		}).execute();
	}
}

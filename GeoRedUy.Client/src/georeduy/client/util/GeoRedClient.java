package georeduy.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.os.AsyncTask;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

public class GeoRedClient {

	public static String Get(String uri, Map<String, String> params)
	        throws Exception {
		
		HttpResponse response = GeoRedClient.GetRequest(Config.SERVER_URL + uri, params);
		
		int status = response.getStatusLine().getStatusCode();
		if (status != 200) {
			throw new Exception(EntityUtils.toString(response.getEntity()));
		}
		
		return EntityUtils.toString(response.getEntity());
	}

	public static void GetAsync(final String uri,
	        final Map<String, String> requestParams,
	        final OnCompletedCallback callback) {
		
		(new AsyncTask<String, String, HttpResponse>() {

			@Override
			protected HttpResponse doInBackground(String... params) {
				String errorMessage = "";
				try {
	                return GeoRedClient.GetRequest(Config.SERVER_URL + uri, requestParams);
                } catch (ClientProtocolException e) {
                	errorMessage = e.getMessage();
                } catch (IOException e) {
                	errorMessage = e.getMessage();
                } catch (Exception e) {
                	errorMessage = e.getMessage();
				} catch (Throwable t) {
					errorMessage = t.getMessage();
				}
				
				BasicHttpResponse response = new BasicHttpResponse(new ProtocolVersion("HTTP", 1, 1),
			            HttpStatus.SC_INTERNAL_SERVER_ERROR, errorMessage);
				try {
	                response.setEntity(new StringEntity(errorMessage));
                } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
                }
				
				return response;
			}

			protected void onPostExecute(HttpResponse response) {
				String res = null;
				String error = null;
                try {
                	if (response.getEntity() != null)
                		res = EntityUtils.toString(response.getEntity());
                	else
                		error = "Null entity.";
                } catch (ParseException e) {
                	error = e.getMessage();
                } catch (IOException e) {
                	error = e.getMessage();
                } catch (Exception e) {
                	error = e.getMessage();
                }
				
				if (response.getStatusLine().getStatusCode() != 200)
					error = res;
				
				if (callback != null)
					callback.onCompleted(res, error);

				super.onPostExecute(response);
			}

		}).execute();
	}
	
	private static HttpResponse GetRequest(String endpoint, Map<String, String> params) throws ClientProtocolException, IOException {
		StringBuilder query = new StringBuilder();
		
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			query.append(param.getKey()).append('=').append(param.getValue());
			
			if (iterator.hasNext()) {
				query.append('&');
			}
		}
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(endpoint + "?" + query.toString());
		
		String token = TokenRepository.getInstance().getToken();
		if (token != null)
			getRequest.addHeader("Token", token);
 
		return httpClient.execute(getRequest);
	}
	
	public static String Post(String uri, Map<String, String> params)
	        throws Exception {
		
		HttpResponse response = GeoRedClient.PostRequest(Config.SERVER_URL + uri, params);
		
		int status = response.getStatusLine().getStatusCode();
		if (status != 200) {
			throw new Exception(EntityUtils.toString(response.getEntity()));
		}
		
		return EntityUtils.toString(response.getEntity());
	}
	
	public static void PostAsync(final String uri,
	        final Map<String, String> requestParams,
	        final OnCompletedCallback callback) {
		
		(new AsyncTask<String, String, HttpResponse>() {

			@Override
			protected HttpResponse doInBackground(String... params) {
				String errorMessage = "";
				try {
	                return GeoRedClient.PostRequest(Config.SERVER_URL + uri, requestParams);
                } catch (ClientProtocolException e) {
                	errorMessage = e.getMessage();
                } catch (IOException e) {
                	errorMessage = e.getMessage();
                } catch (Exception e) {
                	errorMessage = e.getMessage();
				} catch (Throwable t) {
					errorMessage = t.getMessage();
				}
				
				return new BasicHttpResponse(new ProtocolVersion("HTTP", 1, 1),
			            HttpStatus.SC_INTERNAL_SERVER_ERROR, errorMessage);
			}

			protected void onPostExecute(HttpResponse response) {
				String res = null;
				String error = null;
                try {
	                res = EntityUtils.toString(response.getEntity());
                } catch (ParseException e) {
                	error = e.getMessage();
                } catch (IOException e) {
                	error = e.getMessage();
                }
				
				if (response.getStatusLine().getStatusCode() != 200)
					error = res;
				if (callback != null)
					callback.onCompleted(res, error);

				super.onPostExecute(response);
			}

		}).execute();
	}
	
	private static HttpResponse PostRequest(String endpoint, Map<String, String> params) throws ClientProtocolException, IOException {
		StringBuilder query = new StringBuilder();
		
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			query.append(param.getKey()).append('=').append(param.getValue());
			
			if (iterator.hasNext()) {
				query.append('&');
			}
		}
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(endpoint);
		
		postRequest.setEntity(new StringEntity(query.toString(), "UTF-8"));
		
		String token = TokenRepository.getInstance().getToken();
		if (token != null)
			postRequest.addHeader("Token", token);
 
		HttpResponse response = httpClient.execute(postRequest);
			
		return response;
	}
}

package georeduy.server.logic.controllers;

import georeduy.server.logic.model.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class NotificationsController {
	private static NotificationsController s_instance = null;

	private Map<String, String> m_onlineDevices;

	private Sender m_sender;

	private static final int MULTICAST_SIZE = 1000;

	private static final Executor m_threadPool = Executors
	        .newFixedThreadPool(5);

	public NotificationsController() {
		m_onlineDevices = new HashMap<String, String>();
		// TODO: Sacar el api key para un archivo de config o algo.
		m_sender = new Sender("AIzaSyDIDxeUih1uReYXML7WRjhTU42GzISOfO8");
	}

	public static NotificationsController getInstance() {
		if (s_instance == null) {
			s_instance = new NotificationsController();
		}

		return s_instance;
	}

	public void RegisterDevice(String id) {
		m_onlineDevices.put(Client.Current().getId(), id);
	}

	public void UnregisterDevice() {
		m_onlineDevices.remove(Client.Current().getId());
	}
	
	public void UnregisterDevice(String id) {
		for (Map.Entry<String, String> entry : m_onlineDevices.entrySet()) {
	        if (id.equals(entry.getValue())) {
	        	m_onlineDevices.remove(entry.getKey());
	        }
	    }
	}
	
	public void UpdateDevice(String oldId, String newId) {
		for (Map.Entry<String, String> entry : m_onlineDevices.entrySet()) {
	        if (oldId.equals(entry.getValue())) {
	        	m_onlineDevices.put(entry.getKey(), newId);
	        }
	    }
	}

	public void SendToClient(int clientId) throws IOException {
		Message message = new Message.Builder().build();
		m_sender.send(message, m_onlineDevices.get(clientId), 5);
	}

	public void BroadCast() {
		int total = m_onlineDevices.size();
        
        if (total == 1) {
            // send a single message using plain post
            String registrationId = m_onlineDevices.get(0);
            Message message = new Message.Builder().build();
            try {
	            m_sender.send(message, registrationId, 5);
            } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
        } else {
            List<String> partialDevices = new ArrayList<String>(total);
            int counter = 0;
            
            for (String device : m_onlineDevices.values()) {
                counter++;
                partialDevices.add(device);
                
                int partialSize = partialDevices.size();
                if (partialSize == MULTICAST_SIZE || counter == total) {
                    asyncSend(partialDevices);
                    partialDevices.clear();
                }
            }
        }
	}

	private void asyncSend(List<String> partialDevices) {
		// make a copy
		final List<String> devices = new ArrayList<String>(partialDevices);
		
		m_threadPool.execute(new Runnable() {

			public void run() {
				Message message = new Message.Builder().build();
				MulticastResult multicastResult;
				try {
					multicastResult = m_sender.send(message, devices, 5);
				} catch (IOException e) {
					return;
				}
				
				List<Result> results = multicastResult.getResults();
				// analyze the results
				for (int i = 0; i < devices.size(); i++) {
					String regId = devices.get(i);
					Result result = results.get(i);
					
					String messageId = result.getMessageId();
					if (messageId != null) {
						String canonicalRegId = result
						        .getCanonicalRegistrationId();
						if (canonicalRegId != null) {
							// same device has more than on registration id:
							// update it
							UpdateDevice(regId, canonicalRegId);
						}
					} else {
						String error = result.getErrorCodeName();
						if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
							// application has been removed from device -
							// unregister it
							UnregisterDevice(regId);
						}
					}
				}
			}
		});
	}

}

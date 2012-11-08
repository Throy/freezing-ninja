package georeduy.server.logic.controllers;

import georeduy.server.dao.IUserDao;
import georeduy.server.dao.UserDaoImpl;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.UserNotificationsTypes;
import georeduy.server.util.Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.bson.types.ObjectId;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;

public class NotificationsController {
	private static NotificationsController s_instance = null;

	private Map<String, String> m_onlineDevices;

	private Sender m_sender;

	private static final int MULTICAST_SIZE = 1000;

	private static final Executor m_threadPool = Executors
	        .newFixedThreadPool(5);
	
	// daos
	
	private IUserDao userDao = new UserDaoImpl();
	
	// constructores

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
		m_onlineDevices.put(User.Current().getId(), id);
	}

	public void UnregisterDevice() {
		m_onlineDevices.remove(User.Current().getId());
	}
	
	public void UnregisterDevice(String id) {
		for (Map.Entry<String, String> entry : m_onlineDevices.entrySet()) {
	        if (id.equals(entry.getValue())) {
	        	m_onlineDevices.remove(entry.getKey());
	        }
	    }
	}
	
	public void UpdateDevice(String oldId, String newId) {
		if (m_onlineDevices.containsValue(newId)) {
			for (Map.Entry<String, String> entry : m_onlineDevices.entrySet()) {
		        if (oldId.equals(entry.getValue())) {
		        	m_onlineDevices.remove(entry.getKey());
		        }
		    }
		}
		else {
			for (Map.Entry<String, String> entry : m_onlineDevices.entrySet()) {
		        if (oldId.equals(entry.getValue())) {
		        	m_onlineDevices.put(entry.getKey(), newId);
		        }
		    }
		}
	}

	public void SendToClient(String userId, Object payload) throws IOException {
		if (!m_onlineDevices.containsKey(userId))
			return;
		
		Gson gson = new Gson();
        
        Message message = new Message.Builder().addData("className", payload.getClass().getSimpleName()).addData("jsonPayload", gson.toJson(payload)).build();
        
		m_sender.send(message, m_onlineDevices.get(userId), 5);
	}

	public void BroadCast(Object payload) {
		int total = m_onlineDevices.size();
        
        List<String> partialDevices = new ArrayList<String>(total);
        int counter = 0;
        
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        Message message = new Message.Builder().addData("className", payload.getClass().getSimpleName()).addData("jsonPayload", json).build();
        
        for (String device : m_onlineDevices.values()) {
            counter++;
            partialDevices.add(device);
            
            int partialSize = partialDevices.size();
            if (partialSize == MULTICAST_SIZE || counter == total) {
                asyncSend(partialDevices, message);
                partialDevices.clear();
            }
        }
	}
	
	public void BroadCast(Object payload, Filter filter) {
		int total = m_onlineDevices.size();
        
        List<String> partialDevices = new ArrayList<String>(total);
        int counter = 0;
        
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        Message message = new Message.Builder().addData("className", payload.getClass().getSimpleName()).addData("jsonPayload", json).build();
        
        for (Entry<String, String> entry : m_onlineDevices.entrySet()) {
            counter++;
            if (!filter.filter(entry.getKey()))
            	partialDevices.add(entry.getValue());
            
            int partialSize = partialDevices.size();
            if (partialSize > 0 && (partialSize == MULTICAST_SIZE || counter == total)) {
                asyncSend(partialDevices, message);
                partialDevices.clear();
            }
        }
	}

	private void asyncSend(List<String> partialDevices, final Message message) {
		// make a copy
		final List<String> devices = new ArrayList<String>(partialDevices);
		
		m_threadPool.execute(new Runnable() {

			public void run() {
				
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

    // devuelve la configuración de tipos de notificaciones del usuario.
    public UserNotificationsTypes getUserNotificationsTypes (String userId) {
    	return userDao.getNotificationsTypes (userId);
    }

    // modifica la configuración de tipos de notificaciones del usuario.
    public void setUserNotificationsTypes (String userId, UserNotificationsTypes notitypes) {
    	userDao.setNotificationsTypes (userId, notitypes);
    }
}

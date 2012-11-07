// NotificacionesController

// controlador para los casos de uso de notificaciones:
// configurar, listar, etc.

package georeduy.client.controllers;

// imports

import georeduy.client.model.ChatMessage;
import georeduy.client.model.ChatRoom;
import georeduy.client.model.Site;
import georeduy.client.model.Tag;
import georeduy.client.model.UserNotificationTag;
import georeduy.client.model.UserNotificationsTypes;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.OnCompletedCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

public class NotificationsController
{
	// *******************
	// instancia singleton
	// *******************
	
	private static NotificationsController _instance = null;
	
	private Map<String, ChatRoom> _chatRooms = new HashMap<String, ChatRoom>();
	
	// *************
	// constructores
	// *************
	
	private NotificationsController () {
		
	}
	
	public static NotificationsController getInstance() {
		if (_instance == null) {
			_instance = new NotificationsController ();
		}
		
		return _instance;
	}

	// *******
	// métodos
	// *******
	
	// obtener datos.
	
	public void getSomething () {
	}
	
	public void handleNotification(Context context, Site site) {
		String siteName = site.getName();
	}
	
	public void handleNotification(Context context, ChatMessage message) {
		ChatRoom chatRoom;
		if (_chatRooms.containsKey(message.getFromUserId())) {
			chatRoom = _chatRooms.get(message.getFromUserId());
		} else {
			chatRoom = new ChatRoom(message.getFromUserId());
			_chatRooms.put(message.getFromUserId(), chatRoom);
		}
		
		chatRoom.addMessage(message);
		
		Intent intent = new Intent(CommonUtilities.DISPLAY_CHAT_MESSAGE_ACTION);
        intent.putExtra(CommonUtilities.EXTRA_CHAT_MESSAGE, message.getMessage());
        intent.putExtra(CommonUtilities.EXTRA_CHAT_USER_ID, message.getFromUserId());
        context.sendBroadcast(intent);
        
		//TODO: Notificar al usuario de que llego un nuevo mensaje
	}
	
	public void sendMessage(ChatMessage message, OnCompletedCallback callback) {
		ChatRoom chatRoom;
		if (_chatRooms.containsKey(message.getToUserId())) {
			chatRoom = _chatRooms.get(message.getToUserId());
		} else {
			chatRoom = new ChatRoom(message.getToUserId());
			_chatRooms.put(message.getToUserId(), chatRoom);
		}
		
		chatRoom.addMessage(message);
		
		Map <String, String> params = new HashMap <String, String>();
        Gson gson = new Gson();
        
		params.put ("messageInfo", gson.toJson(message));
		
    	GeoRedClient.PostAsync("/Notifications/SendMessage", params, callback);	
	}

	public ChatRoom getChatRoom(String partnerUserId) {
		ChatRoom chatRoom;
		if (_chatRooms.containsKey(partnerUserId)) {
			chatRoom = _chatRooms.get(partnerUserId);
		} else {
			chatRoom = new ChatRoom(partnerUserId);
			_chatRooms.put(partnerUserId, chatRoom);
		}
		
		return chatRoom;
    }
	
	// obtiene la configuración de tipos de notificaciones del usuario.
	
	public void getNotificationsTypesConfiguration (OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
    	GeoRedClient.GetAsync("/Notifications/UserConfig/GetTypes", params, callback);	
	}
	
	// obtiene la configuración de etiquetas de notificaciones del usuario.
	
	public void getNotificationsTagsConfiguration (OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
    	GeoRedClient.GetAsync("/Notifications/UserConfig/GetTags", params, callback);	
	}
	
	// obtiene la configuración de tipos de notificaciones del usuario.
	
	public void setNotificationsTypesConfiguration (UserNotificationsTypes notitypes, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
		// agregar parámetros
        Gson gson = new Gson();
		params.put ("notitypesInfo", gson.toJson (notitypes));
		
    	GeoRedClient.PostAsync("/Notifications/UserConfig/SetTypes", params, callback);	
	}
	
	// obtiene la configuración de etiquetas de notificaciones del usuario.
	
	public void setNotificationsTagsConfiguration (List <Tag> tags, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
		// agregar parámetros
        Gson gson = new Gson();
		params.put ("tagsInfo", gson.toJson (tags));
		
    	GeoRedClient.PostAsync("/Notifications/UserConfig/SetTags", params, callback);	
	}
}

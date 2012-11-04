// Visit

// clase de visitas de la capa lógica del appserver.

package georeduy.client.model;

import java.util.ArrayList;
import java.util.List;


public class ChatRoom {

    private String partnerUserId;
    
    private String partnerUserName;

    private List<ChatMessage> messages = new ArrayList<ChatMessage>();

    public ChatRoom(String partnerUserId) {
    	this.partnerUserId = partnerUserId;
    }

	public String getPartnerUserId() {
		return partnerUserId;
	}


	public void setPartnerUserId(String partnerUserId) {
		this.partnerUserId = partnerUserId;
	}


	public String getPartnerUserName() {
		return partnerUserName;
	}


	public void setPartnerUserName(String partnerUserName) {
		this.partnerUserName = partnerUserName;
	}


	public List<ChatMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ChatMessage> messages) {
		this.messages = messages;
	}
   
	public void addMessage(ChatMessage message) {
		this.messages.add(message);
	}
}

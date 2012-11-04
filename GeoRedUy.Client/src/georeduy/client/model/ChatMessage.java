// Visit

// clase de visitas de la capa lógica del appserver.

package georeduy.client.model;


public class ChatMessage {

    private String toUserId;
    
    private String fromUserId;
    
    private String fromUserName;

    private String message;
    
    private boolean left;
    
    public ChatMessage() {
    	left = false;
    }
    
	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
}

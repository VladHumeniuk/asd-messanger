package lnu.asd.messenger.web.entity.socket.message;

public class MessageResponse {

    private String type;

    private String messageObject;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageObject() {
        return messageObject;
    }

    public void setMessageObject(String messageObject) {
        this.messageObject = messageObject;
    }
}

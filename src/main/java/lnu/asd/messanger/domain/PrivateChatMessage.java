package lnu.asd.messanger.domain;

import lnu.asd.messanger.domain.dbentity.User;

public class PrivateChatMessage extends ChatMessage {
    
    private User recipient;

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
}

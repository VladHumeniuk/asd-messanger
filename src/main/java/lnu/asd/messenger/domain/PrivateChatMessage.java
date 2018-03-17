package lnu.asd.messenger.domain;

import lnu.asd.messenger.domain.dbentity.User;

public class PrivateChatMessage extends ChatMessage {
    
    private User recipient;

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
}

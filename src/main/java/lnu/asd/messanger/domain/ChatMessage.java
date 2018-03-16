package lnu.asd.messanger.domain;

import lnu.asd.messanger.domain.dbentity.User;

public class ChatMessage {

    private User sender;

    private String text;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

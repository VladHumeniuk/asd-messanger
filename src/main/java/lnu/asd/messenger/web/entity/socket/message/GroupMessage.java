package lnu.asd.messenger.web.entity.socket.message;

import lnu.asd.messenger.domain.dbentity.Group;

public class GroupMessage {

    private User sender;

    private Group group;

    private String message;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package lnu.asd.messanger.domain;

import lnu.asd.messanger.domain.dbentity.Group;

public class GroupChatMessage extends ChatMessage {

    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}

package lnu.asd.messanger.web.mapper;

import lnu.asd.messanger.domain.PrivateChatMessage;
import lnu.asd.messanger.web.entity.socket.message.PrivateMessage;
import lnu.asd.messanger.web.entity.socket.message.User;
import org.springframework.stereotype.Component;

@Component
public class PrivateMessageMapper implements BaseMapper<PrivateChatMessage, PrivateMessage> {

    @Override
    public PrivateMessage map(PrivateChatMessage entity) {
        User sender = new User();
        sender.setId(entity.getSender().getId());
        sender.setName(entity.getSender().getUserName());
        User recipient = new User();
        recipient.setId(entity.getRecipient().getId());
        recipient.setName(entity.getRecipient().getUserName());

        PrivateMessage message = new PrivateMessage();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setMessage(entity.getText());

        return message;
    }
}

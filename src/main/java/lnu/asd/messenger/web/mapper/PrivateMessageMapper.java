package lnu.asd.messenger.web.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lnu.asd.messenger.domain.PrivateChatMessage;
import lnu.asd.messenger.web.entity.socket.message.MessageResponse;
import lnu.asd.messenger.web.entity.socket.message.MessageType;
import lnu.asd.messenger.web.entity.socket.message.PrivateMessage;
import lnu.asd.messenger.web.entity.socket.message.User;
import org.springframework.stereotype.Component;

@Component
public class PrivateMessageMapper implements BaseMapper<PrivateChatMessage, MessageResponse> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public MessageResponse map(PrivateChatMessage entity) {
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

        MessageResponse response = new MessageResponse();
        response.setType(MessageType.PRIVATE);
        try {
            response.setMessageObject(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }
}

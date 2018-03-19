package lnu.asd.messenger.web.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lnu.asd.messenger.domain.GroupChatMessage;
import lnu.asd.messenger.web.entity.socket.message.GroupMessage;
import lnu.asd.messenger.web.entity.socket.message.MessageResponse;
import lnu.asd.messenger.web.entity.socket.message.MessageType;
import lnu.asd.messenger.web.entity.socket.message.User;
import org.springframework.stereotype.Component;

@Component
public class GroupMessageMapper implements BaseMapper<GroupChatMessage, MessageResponse> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public MessageResponse map(GroupChatMessage entity) {
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setGroup(entity.getGroup());
        User sender = new User();
        sender.setId(entity.getSender().getId());
        sender.setName(entity.getSender().getUserName());
        groupMessage.setSender(sender);
        groupMessage.setMessage(entity.getText());

        MessageResponse response = new MessageResponse();
        response.setType(MessageType.GROUP);
        try {
            response.setMessageObject(objectMapper.writeValueAsString(groupMessage));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }
}

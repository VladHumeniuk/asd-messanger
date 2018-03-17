package lnu.asd.messenger.web.controllers;

import lnu.asd.messenger.domain.PrivateChatMessage;
import lnu.asd.messenger.domain.dbentity.User;
import lnu.asd.messenger.repository.UserRepository;
import lnu.asd.messenger.web.entity.message.request.SendMessageRequest;
import lnu.asd.messenger.web.exceptions.UserNotFoundException;
import lnu.asd.messenger.web.socket.SocketServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private SocketServer socketServer;

    private UserRepository userRepository;

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Inject
    public void setSocketServer(SocketServer socketServer) {
        this.socketServer = socketServer;
    }

    @RequestMapping(value = "/private/{userId}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sendPrivateMessage(@RequestHeader(name = "senderId") Long senderId,
                                             @RequestBody SendMessageRequest request,
                                             @PathVariable(name = "userId") Long userId) throws UserNotFoundException {
        User sender = userRepository.findOne(senderId);
        User recipient = userRepository.findOne(userId);

        if (sender == null) {
            throw new UserNotFoundException(senderId);
        }
        if (recipient == null) {
            throw new UserNotFoundException(userId);
        }

        PrivateChatMessage message = new PrivateChatMessage();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setText(request.getMessage());
        try {
            socketServer.sendMessage(message);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new InternalError(e.getMessage());
        }
    }

    @RequestMapping(value = "/group/{groupId}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sendGroupMessage(@RequestHeader(value = "senderId") Long senderId,
                                           @RequestBody SendMessageRequest request,
                                           @PathVariable(name = "groupId") Long groupId) throws UserNotFoundException {

        User sender = userRepository.findOne(senderId);

        if (sender == null) {
            throw new UserNotFoundException(senderId);
        }
        //TODO
        return new ResponseEntity(HttpStatus.OK);
    }
}

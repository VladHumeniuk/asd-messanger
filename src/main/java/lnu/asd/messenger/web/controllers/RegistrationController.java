package lnu.asd.messenger.web.controllers;

import lnu.asd.messenger.domain.dbentity.User;
import lnu.asd.messenger.repository.UserRepository;
import lnu.asd.messenger.web.entity.register.request.Data;
import lnu.asd.messenger.web.entity.register.request.RegisterRequest;
import lnu.asd.messenger.web.entity.register.response.UserInfoResponse;
import lnu.asd.messenger.web.exceptions.AlreadyRegisteredException;
import lnu.asd.messenger.web.mapper.UserInfoResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private UserRepository userRepository;

    private UserInfoResponseMapper mapper;

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Inject
    public void setMapper(UserInfoResponseMapper mapper) {
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoResponse> register(@RequestBody RegisterRequest request)
            throws AlreadyRegisteredException {

        Data registrationData = request.getData();
        User user = userRepository.findUserByUserName(registrationData.getName());

        if (user != null) {
            throw new AlreadyRegisteredException(registrationData.getName());
        }

        user = new User();
        user.setPassword(registrationData.getPassword());
        user.setUserName(registrationData.getName());
        user.setEncryptionKey(registrationData.getKey());
        user = userRepository.save(user);

        //TODO add groups info to response
        return new ResponseEntity<>(mapper.map(user), HttpStatus.CREATED);
    }
}
